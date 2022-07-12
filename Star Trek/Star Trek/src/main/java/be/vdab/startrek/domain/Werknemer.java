package be.vdab.startrek.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class Werknemer{

    private final long id;

    private final String voornaam;

    private final String familienaam;
    @PositiveOrZero @NumberFormat(pattern = "#,##0.00")
    private BigDecimal budget;

    public Werknemer(long id, String voornaam, String familienaam, BigDecimal budget) {
        this.id = id;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.budget = budget;
    }

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public boolean uitgave(BigDecimal bedrag){
        if(bedrag.compareTo(BigDecimal.ZERO)<=0 || bedrag.compareTo(budget) > 0){
            return false;
        }
        budget = budget.subtract(bedrag);
        return true;
    }
}
