package be.vdab.frida.controllers;

import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// @Controller zorgt ervoor dat Spring een bean van de class maakt.
// Een bean is een Java object waar Spring handelingen op kan uitvoeren.
@Controller
// @RequestMapping geeft aan naar welke URL er bij @GetMapping wordt verwezen.
// Alle @GetMapping behoren tot de pagina sauzen.
@RequestMapping("snacks")
public class SnackController {

    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    // we kunnen een string meegeven in de variabele alfabet en onderverdelen in characters met
    // de functie .toCharArray()
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    // Onderstaande method voert een get request uit.
    // Het vraagt data op
    // het alfabet wordt weergegeven op /alfabet
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        return new ModelAndView("snackAlfabet", "alfabet", alfabet);
    }

    // Geeft aan dat de method hieronder een GET request
    // verwerkt naar de pagina sauzen bij @RequestMapping en nummer bij @GetMapping.
    // Het URL-adres is /sauzen/nummer
    // Get request vraag enkel data.
    @GetMapping("{nummer}")
    // De method findByNummer geeft een ModelAndView als return waarde.
    // @Pathvariable zorgt ervoor dat Spring de parameter nummer met de waarde van de path variable
    // met dezelfde naam (nummer) invult.
    public ModelAndView findByNummer(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        snackService.read(nummer).ifPresent(gevondenSnack ->
                modelAndView.addObject("snack", gevondenSnack));
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        return modelAndView;
    }

    // Onderstaande method voert een get method uit.
    // Het vraagt data op.
    // url: /alfabet/{letter}
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginNaam(@PathVariable char letter) {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet)
                .addObject("snacks", snackService.findByBeginNaam(String.valueOf(letter)));
    }

    // Onderstaande method voert een get method uit.
    // Het vraagt data op.
    // url: /dagverkopen
    @GetMapping("dagverkopen")
    // Er worden 3 parameters ingegeven in het return-object
    // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
    // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
    //              in de Thymeleaf pagina index.
    // Parameter 3: De inhoud van de variabele zelf.
    public ModelAndView findDagverkopen(){
        return new ModelAndView("dagverkopen", "dagverkopen", snackService.findDagVerkopen());
    }

}
