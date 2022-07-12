package be.vdab.startrek.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Bestelling {

    private final long id;
    private final long werknemerId;
    @NotBlank
    private final String omschrijving;
    @Positive @NumberFormat(pattern = "#,##0.00")
    private final BigDecimal bedrag;


    public Bestelling(long id, long werknemerId, String omschrijving, BigDecimal bedrag) {
        this.id = id;
        this.werknemerId = werknemerId;
        this.omschrijving = omschrijving;
        this.bedrag = bedrag;
    }

    public long getId() {
        return id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
    public BigDecimal getBedrag() {
        return bedrag;
    }

    public long getWerknemerId() {
        return werknemerId;
    }
}
