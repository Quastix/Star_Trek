package be.vdab.frida.forms;

import javax.validation.constraints.NotBlank;

// We valideren de variabelen van en tot met @NotBlank
// Met @NotBlank geven we aan dat de variabele niet null (niets) mag bevatten
// en meer moet bevatten dan enkel spaties.
public record ZoekSnackForm(@NotBlank String beginletters) {
}
