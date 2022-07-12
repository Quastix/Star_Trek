package be.vdab.startrek.repositories;

import be.vdab.startrek.domain.Bestelling;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BestellingRepository.class)
@Sql("/insertBestellingen.sql")
public class BestellingRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String BESTELLINGEN = "bestellingen";
    private final BestellingRepository repository;

    public BestellingRepositoryTest(BestellingRepository repository) {
        this.repository = repository;
    }

    private long werknemerIdVanTestBestelling1(){
        return jdbcTemplate.queryForObject(
                "select werknemerId from bestellingen where omschrijving = 'test1'", Long.class);
    }

    private long werknemerIdVanTestBestelling2(){
        return jdbcTemplate.queryForObject(
                "select werknemerId from bestellingen where omschrijving = 'test2'", Long.class);
    }
    private long werknemerIdVanTestBestelling3(){
        return jdbcTemplate.queryForObject(
                "select werknemerId from bestellingen where omschrijving = 'test3'", Long.class);
    }

    @Test
    void findBestellingWerknemerIdMetId1(){
        long id1= werknemerIdVanTestBestelling1();
        assertThat(repository.findBestellingByWerknemerId(id1))
                .hasSize(countRowsInTableWhere(BESTELLINGEN, "werknemerId = " + id1));

    }

    @Test
    void createBestelling(){
        var id = repository.create(new Bestelling(0,2,"test2", BigDecimal.TEN));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(BESTELLINGEN, "id = " + id)).isOne();
    }

}
