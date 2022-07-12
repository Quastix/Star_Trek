package be.vdab.startrek.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class WerknemerTest {

    private Werknemer werknemer;

    @BeforeEach
    void beforeEach() {
     werknemer = new Werknemer(1, "Sven", "Van Gastel", new BigDecimal(1000));
    }

    @Test
    void BudgetMin700Is300(){
        werknemer.uitgave(new BigDecimal(700));
        assertThat(werknemer.getBudget()).isEqualByComparingTo("300");
    }

    @Test
    void BudgetVerminderenMetNegatiefGetalGaatNiet(){
        assertThat(werknemer.uitgave(new BigDecimal(-20))).isFalse();
    }

    @Test
    void BudgetVerminderenMetGetalHogerDanHetBudgetGaatNiet(){
        assertThat(werknemer.uitgave(new BigDecimal(2000))).isFalse();
    }
}
