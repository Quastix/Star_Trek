package be.vdab.frida.services;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.repositories.SausRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

// @Service maakt een bean van de class. Spring kan hier dan bewerkingen mee uitvoeren.
@Service
public class SausService {
    private final SausRepository sausRepository; // constructor met parameter

    public SausService(@Qualifier("properties") SausRepository sausRepository) {
        this.sausRepository = sausRepository;
    }

    public Stream<Saus> findAll() {
        return sausRepository.findAll();
    }

    public Stream<Saus> findByBeginNaam(char letter) {
        return sausRepository.findAll().filter(saus -> saus.getNaam().charAt(0) == letter);
    }

    public Optional<Saus> findById(long id) {
        return sausRepository.findAll().filter(saus -> saus.getId() == id).findFirst();
    }
}
