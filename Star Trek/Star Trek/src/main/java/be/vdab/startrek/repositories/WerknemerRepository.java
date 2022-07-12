package be.vdab.startrek.repositories;

import be.vdab.startrek.domain.Werknemer;
import be.vdab.startrek.exception.WerknemerNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WerknemerRepository {

    private final JdbcTemplate template;

    private final SimpleJdbcInsert insert;

    public WerknemerRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("werknemers")
                .usingGeneratedKeyColumns("id");
    }
    private final RowMapper<Werknemer> werknemerMapper =
            (result, rowNum) ->
                    new Werknemer(result.getLong("id"),
                            result.getString("voornaam"),
                            result.getString("familienaam"),
                            result.getBigDecimal("budget"));
    public List<Werknemer> findAll() {
        var sql = """
                select id, voornaam, familienaam, budget
                from werknemers
                """;
        return template.query(sql, werknemerMapper);
    }
    public Optional<Werknemer> findById(long id) {
        try {
            var sql = """
                    select id, voornaam, familienaam, budget
                    from werknemers
                    where id = ?
                    """;
            return Optional.of(template.queryForObject(sql, werknemerMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
    public void update(Werknemer werknemer) {
        var sql = """
                update werknemers
                set voornaam = ?, familienaam = ?, budget = ?
                where id = ?
                """;
        if (template.update(sql,
                werknemer.getVoornaam(),
                werknemer.getFamilienaam(),
                werknemer.getBudget(),
                werknemer.getId()) == 0) {
            throw new WerknemerNietGevondenException();
        }
    }
}
