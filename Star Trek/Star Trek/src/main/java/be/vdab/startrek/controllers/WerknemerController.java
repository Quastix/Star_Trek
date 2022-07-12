package be.vdab.startrek.controllers;

import be.vdab.startrek.domain.Bestelling;
import be.vdab.startrek.exception.WerknemerNietGevondenException;
import be.vdab.startrek.services.BestellingService;
import be.vdab.startrek.services.WerknemerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("werknemer")
public class WerknemerController {

    private final WerknemerService werknemerService;
    private final BestellingService bestellingService;

    public WerknemerController(WerknemerService werknemerService, BestellingService bestellingService) {
        this.werknemerService = werknemerService;
        this.bestellingService = bestellingService;
    }

    @GetMapping("{id}")
    public ModelAndView findByWerknemerId(@PathVariable long id) {
        var modelAndView = new ModelAndView("werknemer");
        werknemerService.findWerknemerById(id).ifPresent(gevondenWerknemer ->
                modelAndView.addObject("werknemer", gevondenWerknemer));
        return modelAndView;
    }

    @GetMapping("/bestelling/{werknemerId}")
    public ModelAndView findBestellingByWerknemerId(@PathVariable long werknemerId) {
        var modelAndView = new ModelAndView("bestelling", "bestellingen"
                , bestellingService.findBestellingByWerknemerId(werknemerId));
        werknemerService.findWerknemerById(werknemerId).ifPresent(gevondenWerknemer ->
                modelAndView.addObject("werknemer", gevondenWerknemer));
        return modelAndView;
    }

    @GetMapping("nieuwebestelling/form/{werknemerId}")
    public ModelAndView nieuweBestellingForm(@PathVariable long werknemerId) {
        var modelAndView = new ModelAndView("nieuwebestelling")
                .addObject(new Bestelling(0, 0, "", null));
        werknemerService.findWerknemerById(werknemerId).ifPresent(gevondenWerknemer ->
                modelAndView.addObject("werknemer", gevondenWerknemer));
        return modelAndView;
    }

    @PostMapping("/bestelling/{werknemerId}")
    public String toevoegen(@Valid Bestelling bestelling,
                            @PathVariable long werknemerId, Errors errors, RedirectAttributes redirect) {
        var werknemer = werknemerService.findWerknemerById(werknemerId).get();
        if (errors.hasErrors()) {
            return "nieuwebestelling";
        }
        if (!werknemer.uitgave(bestelling.getBedrag())) {
            redirect.addAttribute("budgetOverschreden", werknemer.getBudget());
            return "redirect:/werknemer/" + werknemerId;
        }
        try {
            werknemerService.update(werknemer);
            bestellingService.create(bestelling);
            return "redirect:/werknemer/bestelling/" + werknemerId;
        } catch (WerknemerNietGevondenException ex) {
            return "redirect:/werknemer/bestelling/" + werknemerId;
        }
    }
}
