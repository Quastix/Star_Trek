package be.vdab.startrek.services;

import be.vdab.startrek.domain.Werknemer;
import be.vdab.startrek.repositories.WerknemerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WerknemerService {

    private final WerknemerRepository werknemerRepository;

    public WerknemerService(WerknemerRepository werknemerRepository) {
        this.werknemerRepository = werknemerRepository;
    }

    public List<Werknemer> findAll(){
        return werknemerRepository.findAll();
    }
    public Optional<Werknemer> findWerknemerById(long id){
        return werknemerRepository.findById(id);
    }
    @Transactional
    public void update(Werknemer werknemer){
        werknemerRepository.update(werknemer);
    }
}
