package be.vdab.frida.controllers;

import be.vdab.frida.sessions.ZoekDeFriet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("zoekdefriet")
public class ZoekDeFrietController {
    private final ZoekDeFriet zoekDeFriet;

    public ZoekDeFrietController(ZoekDeFriet zoekDeFriet) {
        this.zoekDeFriet = zoekDeFriet;
    }

    @GetMapping
    public ModelAndView zoekDeFriet(){
        return new ModelAndView("zoekdefriet").addObject(zoekDeFriet);
    }
}
