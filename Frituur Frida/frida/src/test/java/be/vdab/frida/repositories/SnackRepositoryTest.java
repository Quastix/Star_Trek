package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.SnackNietGevondenException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @JdbcTest maakt een IOC-container met een DataSource bean ten dienste van de test.
// Focust enkel op JDBC-gebaseerde componenten.
@JdbcTest
// Een of meerdere @component classen importeren.
// Spring maakt een PizzaRepository bean in de IOC container. Dit is de te testen bean.
@Import(SnackRepository.class)
/*
Je verwijst naar insertPizzas.sql. Spring voert de SQL statements daarin uit voor elke test.
Spring doet dit in dezelfde transactie waarin Spring ook de test zelf uitvoert.
Spring rollbackt die transactie na de test.
Spring doet dus ook het SQL statement in insertPizzas.sql ongedaan.
Je kan bij @Sql(...) verwijzen naar meerdere .sql bestanden: @Sql({"a.sql", "b.sql"}).
Spring voert voor elke test de SQL statements in al die bestanden uit.
 */
@Sql({"/insertSnacks.sql", "/insertDagverkopen.sql"})
public class SnackRepositoryTest
    /*
    Je test erft van AbstractTransactionalJUnit4SpringContextTests.
    Die zorg er voor dat elke test een transactie is die Spring na de test rollbackt
    De class ook bevat methods om gemakkelijk een repository te testen.
     */
        extends AbstractTransactionalJUnit4SpringContextTests {

    // Deze constante bevat de naam van de table (in de database) die de snacks bevat.
    private static final String SNACKS = "snacks";

    //Je maakt een reference variabele met als type de bean die je wil testen.
    private final SnackRepository repository;

    // Spring injecteert in de constructor de bean waarmee je de variabele bij SNACKS vult.
    public SnackRepositoryTest(SnackRepository repository) {
        this.repository = repository;
    }

    //Je gebruikt deze method verder in andere test methods.
    private long idVanTestSnack() {
        // Je base class bevat een variabele jdbcTemplate, van het type JdbcTemplate.
        // Je voert daarmee SQL statements uit in je test.
        // Je zoekt hier de id van de eerste snack die je maakte in insertSnacks.sql.
        return jdbcTemplate.queryForObject("select id from snacks where naam = 'test'", long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestSnack()))
                // Je controleert met hasValueSatisfying de waarde in een Optional.
                // De parameter pizza van de lambda bevat die waarde.
                // Je test met assertThat of die pizza aan een voorwaarde voldoet.
                .hasValueSatisfying(snack -> assertThat(snack.naam()).isEqualTo("test"));
    }

    @Test
    void findByOnbestaandeIdVindtGeenSnack() {
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void update() {
        var id = idVanTestSnack();
        var snack = new Snack(id, "test", BigDecimal.TEN);
        repository.update(snack);
        // Je wijzigde de prijs van de eerst toegevoegde pizza naar 10.
        // Het aantal records met de id van de toegevoegde pizza en de prijs 10 moet 1 zijn.
        assertThat(countRowsInTableWhere(SNACKS, "prijs=10 and id =" + id)).isOne();
    }

    @Test
    void updateOnbestaandeSnackGeeftEenFout() {
        assertThatExceptionOfType(SnackNietGevondenException.class).isThrownBy(
                () -> repository.update(new Snack(-1, "test", BigDecimal.TEN)));
    }

    /*@Test
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("t"))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 't%'"))
                .extracting(Snack::naam)
                .allSatisfy(naam -> assertThat(naam.toLowerCase())
                        .startsWith("t")).isSortedAccordingTo(String::compareToIgnoreCase);
    }*/

    /*@Test
    void findVerkochtAantalPerSnack() {
        var verkochteAantallenPerSnack = repository.findDagverkopen();
        assertThat(verkochteAantallenPerSnack).hasSize(countRowsInTable(SNACKS));
        var rij1 = verkochteAantallenPerSnack.get(0);
        assertThat(rij1.totaalAantal()).isEqualTo(jdbcTemplate.queryForObject("select sum(aantal) from dagVerkopen where snackId = " + rij1.id(), Integer.class));
    }*/


}
