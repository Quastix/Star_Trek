package be.vdab.frida.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record Snack (long id, @NotBlank String naam,@NotNull @PositiveOrZero BigDecimal prijs){
}
