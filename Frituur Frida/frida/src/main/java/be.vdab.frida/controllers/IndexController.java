package be.vdab.frida.controllers;

import be.vdab.frida.domain.Adres;
import be.vdab.frida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.DayOfWeek.MONDAY;

// @Controller zorgt ervoor dat Spring een bean van de class maakt.
// Een bean is een Java object waar Spring handelingen op kan uitvoeren.
@Controller
class IndexController {
    // De variabele EEN_JAAR_IN_SECONDEN is het aantal seconden dat de cookie wordt bewaard
    // final = waarde is een constante. (in drukletters geschreven)
    // private = eigen aan deze class
    // static = variabele wordt opgeslagen in de class en niet in een object.
    private static final int EEN_JAAR_IN_SECONDEN = 31_536_000;

    // Geeft aan dat de method hieronder een GET request verwerkt naar de URL ("/").
    // Get request vraag enkel data.
    // ("/") Staat voor de welkom pagina
    @GetMapping("/")
    /* De ModelAndView method:
    De naam van de method mag vrij gekozen worden. (Index)
    De methode geeft data door aan de Thymeleaf pagina en heeft als return type ModelAndView
    Model staat voor de data, View staat voor de Thymeleaf pagina.
    Model = data die we gebruiken (Java bestanden)
    View = hoe we het eruit laten zien (HTML-bestanden) */
    // @CookieValue
    // We gaan een cookie aanmaken van een Optional<integer> van het aantal bezoeken op de website.
    public ModelAndView index(@CookieValue Optional<Integer> aantalBezoeken,
            //We sturen de cookie ineens naar de browser met onderstaande code
                              HttpServletResponse response) {
        // De variabele openOfGesloten is een String die de waarde open of gesloten heeft.
        // We controleren de dag van vandaag en controleren of dit gelijk is aan maandag.
        // Als de dag maandag is. Is de string gelijk aan gesloten, anders is de string gelijk aan open.
        var openOfGesloten = LocalDate.now().getDayOfWeek() == MONDAY ? "geloten" : "open";
        // De variabele nieuwAantalBezoeken is een integer waar de waarde gelijk is aan het aantal keer
        // de website bezocht is.
        // we gebruiken de variabele van de cookie
        // de method orElse returned de waarde die aanwezig is. En telt er 1 bij op.
        // Als er geen waarde aanwezig is returned orElse de waarde van other. En telt er 1 bij op.
        var nieuwAantalBezoeken = aantalBezoeken.orElse(0) + 1;
        // De variabele cookie is een variabele van de class Cookie
        // De naam is "aantalBezoeken", en de waarde in String formaat van de nieuwAantalBezoeken.
        var cookie = new Cookie("aantalBezoeken", String.valueOf(nieuwAantalBezoeken));
        // Met de method setMaxAge maak je de cookie permanent
        // De browser verwijdert de cookie na de seconden gelijk van de variabele EEN_JAAR_IN_SECONDEN.
        cookie.setMaxAge(EEN_JAAR_IN_SECONDEN);
        // De method setPath het begin van een URL in je website.
        // Enkel requests naar URL's die hiermee beginnen, kunnen de cookie lezen.
        // "/" zorgt ervoor dat alle request van je website de cookie kunnen lezen.
        // "/mandje" Enkel requests naar URL's die beginnen met mandje kunnen de cookie lezen.
        cookie.setPath("/");
        // Je voegt de cookie toe aan de variabele response van het type HttpServletResponse.
        response.addCookie(cookie);
        // De variabele van een object ModelAndView.
        // Model = data die we gebruiken (java bestanden). Het Model is een Map,
        // hierdoor kunnen er meerderen objecten gebruikt worden die een sleutel hebben bij naam
        // view = weergaven van die data (in een HTML-bestand)
        // Dit object van de class ModelAndView heeft 3 parameters. Er bestaan ook andere constructors.
        // Parameter 1: De naam van de Thymeleaf pagina terug. Je typt de extensie (.html) niet
        // Parameter 2: De naam waaronder je een stukje data doorgeeft, dit wordt ook gebruikt
        //              in de Thymeleaf pagina index.
        // Parameter 3: De inhoud van de variabele zelf.
        var modelAndView = new ModelAndView("index", "toegang", openOfGesloten);
        // De methode addObject voegen we een nieuw object toe aan de Map van Model.
        // De sleutel is "adres", de data is een object van de class Adres.
        modelAndView.addObject("adres", new Adres("Mamoutsauslaan",
                "25", new Gemeente("Kleintje-Specialsaus", 8520)));
        // De methode addObject voegen we een nieuw object toe aan de Map van Model.
        // De sleutel is "aantalBezoeken", de data is de variabele nieuwAantalBezoeken.
        // Dit is ook de waarde die de cookie onthoudt.
        modelAndView.addObject("aantalBezoeken", nieuwAantalBezoeken);
        // De return is de aangemaakte ModelAndView variabele. Die alle data bevat om weer te geven
        // op de html-pagina
        return modelAndView;
    }
}
