package be.vdab.startrek.repositories;

import be.vdab.startrek.domain.Werknemer;
import be.vdab.startrek.exception.WerknemerNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(WerknemerRepository.class)
@Sql("/insertWerknemers.sql")
public class WerknemerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    public static final String WERKNEMERS = "werknemers";
    private final WerknemerRepository repository;

    public WerknemerRepositoryTest(WerknemerRepository repository) {
        this.repository = repository;
    }

    private long idVanTestWerknemer() {
        // Je base class bevat een variabele jdbcTemplate, van het type JdbcTemplate.
        // Je voert daarmee SQL statements uit in je test.
        // Je zoekt hier de id van de eerste pizza die je maakte in insertPizzas.sql.
        return jdbcTemplate.queryForObject(
                "select id from werknemers where voornaam = 'test1'", Long.class);
    }

    @Test
    void findAll(){
        assertThat(repository.findAll())
                .hasSize(countRowsInTable(WERKNEMERS));
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanTestWerknemer()))
                .hasValueSatisfying(werknemer -> assertThat(werknemer.getVoornaam())
                        .isEqualTo("test1"));
    }

    @Test
    void update(){
        var id = idVanTestWerknemer();
        var werknemer = new Werknemer(id, "test", "testen"
                , BigDecimal.TEN);
        repository.update(werknemer);
        assertThat(countRowsInTableWhere(WERKNEMERS, "budget =10 and id ="+id))
                .isOne();
    }

    @Test
    void updateOnbestaandeWerkneerGeeftEenFout() {
        assertThatExceptionOfType(WerknemerNietGevondenException.class).isThrownBy(
                () -> repository.update(new Werknemer(-1, "test", "testen"
                        , BigDecimal.TEN)));
    }
}
