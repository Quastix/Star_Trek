package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    private final PropertiesSausRepository repository;

    public PropertiesSausRepositoryTest(PropertiesSausRepository repository) {
        this.repository = repository;
    }

    @Test
    void deEersteSausIsCocktailSaus() {
        assertThat(repository.findAll().contains(new Saus(1, "cocktail", List.of(new String[]{"mayonasie", "ketchup", "cognac"}))));
    }

    /*@Test
    void erZijnEvenveelSauzenAlsErRegelsZijnInHetCSVBestand() throws IOException {
        AssertionsForClassTypes.assertThat(repository.findAll()).hasSameSizeAs(Files.readAllLines(PAD));
    }*/


}

