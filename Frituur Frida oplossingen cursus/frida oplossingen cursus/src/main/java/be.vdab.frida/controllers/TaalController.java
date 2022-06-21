package be.vdab.frida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// @Controller zorgt ervoor dat Spring een bean van de class maakt.
// een bean is een java object waar Spring handelingen op kan uitvoeren.
@Controller
// @RequestMapping geeft aan naar welke URL er bij @GetMappping wordt verwezen.
// alle @GetMapping behoren tot de pagina taal.
@RequestMapping("talen")
public class TaalController {
    // Geeft aan dat de method hieronder een Get request verwerkt naar de pagina taal bij @RequestMapping.
    // Get request vraagt enkel data.
    @GetMapping
    // @RequestHeader bevat informatie van de browser. We willen bij deze request header de accept-language weten
    // dit is de taal waarin de browser staat.

    // Locatie van request headers in de browser:
    //  1.  Druk F12.
    //  2.  Surf naar http://localhost:8080
    //  3.  Kies in het tabblad Network de GET request naar /.
    //      Scrol rechts naar beneden tot Request Headers.
    //      Een header heeft een naam en een waarde. Voorbeelden:
    //      De header Accept-Language header bevat de voorkeur taal en land van de gebruiker.
    //      De header User-Agent bevat het type browser en het besturingssysteem.
    public ModelAndView nederlands(@RequestHeader("Accept-Language") String language) {
        // De enige taal die we laten terug vinden is nederlands daarom zoeken we naar "nl".
        return new ModelAndView("talen", "nederlands", language.startsWith("nl"));
    }
}
