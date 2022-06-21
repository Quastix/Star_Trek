package be.vdab.frida.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

// @JdbcTest maakt een IOC-container met een DataSource bean ten dienste van de test.
@JdbcTest
class DataSourceTest {
    private final DataSource dataSource;
    // Injecteren van de DataSource bean.
    DataSourceTest(DataSource dataSource){
        this.dataSource=dataSource;
    }

    @Test
    void getConnection() throws SQLException {
        // De method getConnection geeft ons een variabele van de Interface
        // connection. Op de interface Connection kunnen we de method
        // getCatalog uitvoeren geeft de catalog naam van het huidige object terug.
        // In application.properties vindt men terug dat dit luigi is.
        try (var connection = dataSource.getConnection()){
            assertThat(connection.getCatalog()).isEqualTo("frida");
        }
    }
}