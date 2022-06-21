package be.vdab.frida.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @Extendwith laat toe om extensies toe te voegen in JUnit. We voegen Spring toe
// als extensie. Spring maakt een lege IOC container aan ten dienste van de test.
// Een IOC container is een object van een opgegeven class en injecteert daar alle
// dependencies in adhv een constructor of een method.
@ExtendWith(SpringExtension.class)
// @PropertySource is een annotation van Spring.
// Het vraagt welke properties bestand we gaan inlezen.
// In application.properties vinden we de websites terug die we met fixer of ecb aanspreken.
@PropertySource("application.properties")
// @Import is een annotation van Spring.
// Het vraagt welke class we gaan importeren. We importeren de class PropertiesSausRepository
// Spring maakt van de class een bean in de IOC container.
@Import(PropertiesSausRepository.class)
public class PropertiesSausRepositoryTest {

    private final Path pad;
    private final PropertiesSausRepository repository;

    public PropertiesSausRepositoryTest(@Value("${propertiesSauzenPad}")Path pad, PropertiesSausRepository repository) {
        this.pad = pad;
        this.repository = repository;
    }
    /*@Test
    void erZijnEvenveelSauzenAlsErRegelsZijnInHetCSVBestand() throws IOException {
        assertThat(repository.findAll()).hasSameSizeAs(Files.readAllLines(PAD));
    }*/

    @Test
    void deEersteSausBevatDeDataVanDeEersteRegelInHetCSVBestand() throws IOException {
        var eersteRegel = Files.lines(pad).findFirst().get();
        var eersteSaus = repository.findAll().findFirst().get();
        assertThat(eersteSaus.getId() + ":" + eersteSaus.getNaam() + "," +
                Arrays.stream(eersteSaus.getIngredienten())
                        .collect(Collectors.joining(",")))
                .isEqualTo(eersteRegel);
    }
}
