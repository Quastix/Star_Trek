package be.vdab.frida.sessions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoekDeFrietTest {
    private ZoekDeFriet zoekDeFriet;

    @BeforeEach
    void beforeEach() {
        zoekDeFriet = new ZoekDeFriet();
    }

    @Test
    void eenNieuweZoekDeFrietHeeftEenGetalTussen0En6(){
        assertThat(zoekDeFriet.getDeur()).isBetween(0,6);
    }
    @Test
    void eenNieuweZoekDeFrietHeeftEenGetalLagerDan7(){
        assertThat(zoekDeFriet.getDeur()).isLessThan(7);
    }
    @Test
    void eenNieuweZoekDeFrietHeeftEenGetalHogerOfGelijkAan0(){
        assertThat(zoekDeFriet.getDeur()).isGreaterThanOrEqualTo(0);
    }
}
