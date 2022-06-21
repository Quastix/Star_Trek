package be.vdab.frida.services;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import be.vdab.frida.repositories.SausRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// @Service maakt een bean van de class. Spring kan hier dan bewerkingen mee uitvoeren.
@Service
// Een service class method roept methodes op van de repository classes. De service class method
// zorgt ervoor dat de bijhorende database bewerkingen tot 1 transactie behoren.
public class SausService {
    // Je maakt een SausRepository reference variabele, maar je initialiseert ze nog niet.
    private final SausRepository[] sauzen;

    // Je maakt een constructor. Die krijgt een SausRepository object als parameter binnen.
    public SausService(SausRepository[] sauzen) {
        // Je onthoudt dit object in de variabele sauzen
        this.sauzen = sauzen;
    }

    // List aanmaken met alle sauzen erin.
    public List<Saus> findAll() {
        Exception laatste = null;
        // Itereren over de verschillende dependencies (Repositories)
        for (var sausjes : sauzen) {
            try {
                // Je probeert findAll() op te roepen. Als dit lukt, gebruik je de gelezen waarde.
                // Gezien je hier een return doet, stopt ook de iteratie bij de eerste client die we
                // probeerde lezen.
                return sausjes.findAll();
            } catch (SausRepositoryException ex) {
                laatste = ex;
            }
        }
        // Alle dependencies hebben een fout geworpen. Je werpt zelf een fout. Je geeft de exception,
        // die de laatst geprobeerde dependency gaf, mee als parameter.
        throw new SausRepositoryException("Kan de sauzen in geen enkel bestand lezen", laatste);
    }

    // List aanmaken van de class saus waarbij de beginletter overeenkomt met de
    // letter die we opgeven
    public List<Saus> findByBeginNaam(char letter){
        // De functie wou niet werken omdat ik de variabele fout had gedeclareerd!
        //Saus[] sausLijst = (Saus[]) sauzen.findAll().toArray();
        //return Arrays.stream(sausLijst).filter(saus -> saus.nummer() == id).findFirst();
        Exception laatste = null;
        for (var sausjes : sauzen) {
            try {
                List<Saus> sausLijst = sausjes.findAll();
                return sausLijst.stream().filter(saus -> saus.naam().charAt(0) == letter).toList();
            } catch (SausRepositoryException ex) {
                laatste = ex;
            }
        }
        throw new SausRepositoryException("Kan de sauzen in geen enkel bestand lezen", laatste);
    }

    // De method findByNummerHelper return een Optional van het object Saus.
    // De List van sauzen wordt gefilterd op de saus die overeen komt met het meegegeven nummer
    // van de method. De saus die overeenkomt met dat nummer is de return waarde.
    public Optional<Saus> findById(long id){
        // De functie werkte wel maar in de functie findByBeginNaam wou de functie niet werken
        // omdat ik de variabele fout had gedeclareerd!
        //Saus[] sausLijst = (Saus[]) sauzen.findAll().toArray();
        //return Arrays.stream(sausLijst).filter(saus -> saus.nummer() == id).findFirst();
        Exception laatste = null;
        for (var sausjes : sauzen) {
            try {
                List<Saus> sausLijst = sausjes.findAll();
                return sausLijst.stream().filter(saus -> saus.nummer() == id).findFirst();
            } catch (SausRepositoryException ex) {
                laatste = ex;
            }
        }
        throw new SausRepositoryException("Kan de sauzen in geen enkel bestand lezen", laatste);
    }

}
