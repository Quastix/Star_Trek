package be.vdab.frida.services;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.dto.Dagverkopen;
import be.vdab.frida.repositories.SnackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Gezien je van deze service maar één implementatie hebt, maak je geen bijbehorende interface.
// @Service maakt van de class een bean. Spring kan bewerkingen uitvoeren hierop
@Service
public class SnackService {
    private final SnackRepository snackRepository;

    // Je injecteert de PizzaRepository bean.
    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public Optional<Snack> read(long id){
        return snackRepository.findById(id);
    }

    public  void update(Snack snack){
        snackRepository.update(snack);
    }

    public List<Snack> findByBeginNaam(String beginNaam){
        return snackRepository.findByBeginNaam(beginNaam);
    }

    // We geven de transactie readOnly mee aan de method van de class
    // de method leest enkel records (er wordt niets toegevoegd, gewijzigd of verwijdert)
    @Transactional(readOnly = true)
    public List<Dagverkopen> findDagVerkopen(){
        return snackRepository.findDagverkopen();
    }
}
