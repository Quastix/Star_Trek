package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.dto.Dagverkopen;
import be.vdab.frida.exceptions.SnackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Repository class, methods van deze class spreken de database aan.
// Spring maakt van de class een bean.
// een bean is een java object waar Spring bewerkingen op kan uitvoeren.
@Repository
public class SnackRepository {

    // De class JdbcTemplate zorgt ervoor dat de herhaalde stappen van JDBC automatisch
    // uitgevoerd worden.

    // Stappen JDBC.
    // Connectie openen
    // een PreparedStatement maken
    // Eventueel parameters van dit PreparedStatement invullen
    // Het PreparedStatement uitvoeren
    // Eventueel itereren over de ResultSet (als je data leest).
    private final JdbcTemplate template;

    // Een object van de interface RowMapper kan een object maken van een rij van de ResultedSet
    // bij deze variabele maken we een object van Snack
    private final RowMapper<Snack> snackMapper =
            // We passen de method mapRow toe op de variabele.
            // Omdat RowMapper maar 1 method heeft doen we dit via een lambda.
            // Met de method geef je een ResultSet en een rijnummer (int) mee.
            // We geven 2 parameters mee
            // parameter 1: result is de resultSet
            // parameter 2: rowNum is het volgnummer (int) van de huidige rij in de ResultSet
            (result, rowNum)->
                    // De return waarde is de Snack die je maakt op basis van de huidige rij in de
                    // ResultSet
                    new Snack(result.getLong("id"), result.getString("naam"),
                            result.getBigDecimal("prijs"));

    public SnackRepository(JdbcTemplate template) {
        this.template = template;
    }

    // Method om 1 record te lezen als java object.
    public Optional<Snack> findById(long id) {
        // try block omdat de method queryForObject een IncorrectResultSizeDateAccesException
        // kan werpen
        try {
            var sql = """
                    select id, naam, prijs
                    from snacks
                    where id = ?
                    """;
            // De Optional vraag de waarde van de JdbcTemplate.
            // De method queryForObject geeft ons 1 rij terug.
            // Als er niet exact 1 rij wordt teruggegeven werpt de method een
            // IncorrectResultSizeDataAccesException.
            // We geven 3 parameters mee
            // parameter 1: select statement
            // parameter 2: De variabele van de class RowMapper waar de method MapRow op wordt
            // toegepast
            // parameter 3: het vraagteken in de sql statement
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex){
            // als er een IncorrectResultSizeDataAccesException wordt geworpen geven we een lege
            // Optional terug.
            return Optional.empty();
        }
    }

    // Method om de gegevens van een snack te wijzigen adhv id
    public void update(Snack snack) {
        var sql = """
                update snacks
                set naam = ?, prijs = ?
                where id = ?
                """;
        // De update method voert een update, insert of delete SQL statement uit.
        // En heeft als returnwaarde het aantal records dat werden aangepast.
        // Er worden 5 parameters meegegeven.
        // Parameter 1: is het SQL statement
        // Parameter 2: is de waarde voor naam = ? in het SQL statement.
        // Parameter 3: is de waarde voor prijs = ? in het SQL statement.
        // Parameter 4: is de waarde voor id = ? in het SQL statement.
        // Als Het aantal pizzas gelijk is aan 0 dan wordt er een nieuwe
        // PizzaNietGevondenException gethrowed.
        if (template.update(sql, snack.naam(), snack.prijs(), snack.id()) == 0) {
            throw new SnackNietGevondenException();
        }
    }

    // Method die de snacks met de beginwaarde van een String leest:
    // We geven een String met beginNaam mee
    public List<Snack> findByBeginNaam(String beginNaam) {
        // Als de String beginNaam leeg is
        if (beginNaam.isEmpty()){
            return List.of();
        }
        // Je kan dit SQL statement niet testen in IntelliJ omdat het bestaat uit
        // een concatenatie van strings
        var sql = """
                select id, naam, prijs
                from snacks
                where naam like 
                """
                // We voegen ' toe gevolgd door de string van de beginNaam
                // gevolgd door %'
                + "'" + beginNaam + "%'";
        // De method query geeft ons een List<Pizza> terug.
        // Het krijgt 3 parameters mee
            // parameter 1: select statement
            // parameter 2: De variabele van de class RowMapper waar de method MapRow op wordt
            // toegepast
        // De method voert volgende stappen uit.
            // een lege List<Snack> maken
            // Een Connection vragen daan de DataSource bean
            // Een Statement met het select statement maken en uitvoeren.
            // Itereren over de ResultSet van het resultaat van het Statement
            // Per rij je lambda uitvoeren
            // De Snack, die je lambda teruggeeft, toevoegen aan de List
            // De ResultSet, het Statement en de Connection sluiten.
            // De List<Snack> teruggeven.
        return template.query(sql, snackMapper);

    }

    public List<Dagverkopen> findDagverkopen(){
        var sql = """
                SELECT id, naam, sum(aantal) as totaalAantal
                FROM snacks left outer JOIN dagverkopen
                ON snacks.id = dagverkopen.snackId
                group by id
                order by id
                """;
        // Een object van de interface RowMapper kan een object maken van een rij van de ResultedSet
        // bij deze variabele maken we een object van Dagverkopen
        RowMapper<Dagverkopen> mapper = (result, rowNum) ->
                new Dagverkopen(
                        result.getLong("id"), result.getString("naam"), result.getInt("totaalAantal"));
        // De method query geeft ons een List<Dagverkopen> terug.
        // Het krijgt 3 parameters mee
        // parameter 1: select-statement
        // parameter 2: De variabele van de class RowMapper waar de method MapRow op wordt
        // toegepast
        // De method voert volgende stappen uit.
        // Een lege List<DagVerkopen> maken
        // Een Connection vragen daan de DataSource bean
        // Een statement met het select-statement maken en uitvoeren.
        // Itereren over de ResultSet van het resultaat van het statement
        // Per rij je lambda uitvoeren
        // De Pizza, die je lambda teruggeeft, toevoegen aan de List
        // De ResultSet, het statement en de Connection sluiten.
        // De List<Dagverkopen> teruggeven.
        return template.query(sql, mapper);
    }


}
