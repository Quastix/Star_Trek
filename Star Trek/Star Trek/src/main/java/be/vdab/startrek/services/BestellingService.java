package be.vdab.startrek.services;

import be.vdab.startrek.domain.Bestelling;
import be.vdab.startrek.domain.Werknemer;
import be.vdab.startrek.repositories.BestellingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BestellingService {

    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }
    @Transactional
    public long create(Bestelling bestelling){
        return bestellingRepository.create(bestelling);
    }
    public List<Bestelling> findBestellingByWerknemerId(long werknemerId){
        return bestellingRepository.findBestellingByWerknemerId(werknemerId);
    }


}
