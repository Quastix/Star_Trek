package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;

import java.util.ArrayList;
import java.util.List;

public interface SausRepository {
    // We geven een sausArray mee aan de functie en de functie geeft een Saus als return waarde.
    // Static maakt de method eigen aan de interface
    static Saus createSaus(String[] sausArray) {
        // op positie 0 van de van de array wordt de string omgezet naar een long dit is het nummer
        // van de saus
        long nummer = Long.parseLong(sausArray[0]);
        // op positie 1 van de array vinden we de naam van de saus
        String naam = sausArray[1];
        // op de 2de positie en verder van de array vinden we de verschillende ingredienten van de saus
        // terug. We maken hiervoor eerst een nieuwe list van String voor aan.
        // Daarna itereren we over de sausArray van positie 2 tot de lengte van de array -1
        // om zo alle ingredienten in de array met ingredienten te plaatsen.
        List<String> ingredienten = new ArrayList<>();
        for (var i = 2; i < sausArray.length - 1; i++) {
            ingredienten.add(sausArray[i]);
        }
        return new Saus(nummer, naam, ingredienten);
    }

    List<Saus> findAll();
}
