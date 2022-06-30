package be.vdab.frida.controllers;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.SnackNietGevondenException;
import be.vdab.frida.forms.ZoekSnackForm;
import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

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
    public ModelAndView findDagverkopen() {
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        return new ModelAndView("dagverkopen", "dagverkopen", snackService.findDagVerkopen());
    }

    // Onderstaande method voert een get method uit.
    // Het vraagt data op.
    // url: /zoeksnack/form
    @GetMapping("zoeksnack/form")
    public ModelAndView zoekSnackForm() {
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        return new ModelAndView("zoeksnack")
                .addObject("zoekSnackForm", new ZoekSnackForm(null));
    }

    // Het vraagt data op.
    // url: /zoeksnack

    // Bean validation valideert bij de validatie van een ZoekSnackForm
    // en controleert of de waarden van en tot geen null (niets) bevatten en
    // meer dan alleen spaties bevat.
    @GetMapping("zoeksnack")
    public ModelAndView zoekSnack(@Valid ZoekSnackForm form, Errors errors) {
        // Er worden 3 parameters ingegeven in het return-object
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        var modelAndView = new ModelAndView("zoeksnack");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("snacks",
                snackService.findByBeginNaam(form.beginletters()));
    }

    @GetMapping("updatesnack/form/{id}")
    public ModelAndView updateSnackForm(@PathVariable long id) {
        var modelAndView = new ModelAndView("updatesnack");
        snackService.read(id).ifPresent(gevondenSnack ->
                modelAndView.addObject("snack", gevondenSnack));
        return modelAndView;
    }

    @PostMapping
    @Transactional
    public String updatesnack(@Valid Snack snack, Errors errors, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return "updatesnack";
        }
        try {
            var snackOud = snackService.read(snack.id()).get();
            redirect.addAttribute("snackOudId", snackOud.id())
                    .addAttribute("snackOudNaam", snackOud.naam())
                    .addAttribute("snackOudPrijs", snackOud.prijs());
            snackService.update(snack);
            snackService.read(snack.id());
            redirect.addAttribute("snackNieuwId", snack.id())
                    .addAttribute("snackNieuwNaam", snack.naam())
                    .addAttribute("snackNieuwPrijs", snack.prijs());
            return "redirect:/";
        } catch (SnackNietGevondenException ex) {
            redirect.addAttribute("snackNietGevonden", snack.id());
            return "redirect:/";
        }
    }
}
