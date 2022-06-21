package be.vdab.frida.controllers;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// @Controller zorgt ervoor dat Spring en bean van de class maakt.
// Een bean is een java object waar Spring handelingen op kan uitvoeren.
@Controller
// @RequestMapping geeft aan naar welke URL er bij @GetMapping wordt verwezen.
// Alle @GetMapping behoren tot de pagina sauzen.
@RequestMapping("sauzen")
class SauzenController {
    /* Vervangen door dependency injection
    //aanmaken van een array van sauzen
    private final Saus[] alleSauzen = {
            new Saus(1, "cocktail", List.of(new String[]{"mayonasie", "ketchup", "cognac"})),
            new Saus(2, "mayonaise", List.of(new String[]{"ei", "mosterd"})),
            new Saus(3, "mosterd", List.of(new String[]{"mosterd", "azijn", "witte wijn"})),
            new Saus(4, "tartare", List.of(new String[]{"mayonaise", "augurk", "tabasco"})),
            new Saus(5, "vinaigrette", List.of(new String[]{"olijfolie", "mosterd", "azijn"}))};
    */

    // Oplossing van de curus oef 1.16 alfabet
    // we kunnen een string meegeven in de variabele alfabet en onderverdelen in characters met
    // de functie .toCharArray()
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    // We declareren een variabele van de SausService class. Een service class method roept
    // methodes op van de repository classes. De service class method zorgt ervoor dat de
    // bijhorende database bewerkingen tot 1 transactie behoren.
    private final SausService sausService;

    // Bij de start van de website roept maakt Spring een bean van de class SauzenController.
    // Spring roept daarbij de constructor op. Spring geeft de SausService bean mee als
    // constructor parameter. IntelliJ toont dit met een symbool in de marge. Als je daarop klikt,
    // opent IntelliJ de class van deze bean: SausService.
    SauzenController(SausService sausService) {
        this.sausService = sausService;
    }

    // Geeft aan dat de method hieronder een GET request verwerkt naar de pagina sauzen bij @RequestMapping.
    // Get request vraag enkel data.
    @GetMapping
    // De ModelAndViewMethod:
    // De naam van de method mag vrij gekozen worden. (findAll)
    // De methode geeft data door aan de Thymeleaf pagina en heeft als return type ModelAndView
    // Model staat voor de data, View staat voor de Thymeleaf pagina.
    // Model = data die we gebruiken (java bestanden)
    // View = hoe we het eruit laten zien (HTML-bestanden).
    public ModelAndView findAll() {
        // De variabele van een object ModelAndView.
        // Model = data die we gebruiken (java bestanden). Het Model is een Map,
        // hierdoor kunnen er meerderen objecten gebruikt worden die een sleutel hebben bij naam
        // view = weergaven van die data (in een HTML-bestand)
        // Dit object van de class ModelAndView heeft 3 parameters. Er bestaand ook andere constructors.
        // viewName: De naam van de Thymeleaf pagina (sauzen.html)
        // modelName: de naam waaronder je data doorgeeft ana de Thymelead pagina.
        // modelObject:
        //              de data die je doorgeeft aan de Thymeleaf pagina: was de array alleSauzen
        //              dit is vervangen dankzij een dependency injection door sausService.findAll()
        //              van de class SausService.
        return new ModelAndView("sauzen", "alleSauzen", sausService.findAll().iterator());
    }

    /* Vervangen door dependency injection.

    // De method findByNummerHelper return een Optional van het object Saus.
    // De array van sauzen wordt gefilterd op de saus die overeen komt met het meegegeven nummer
    // van de method. De saus die overeenkomt met dat nummer is de return waarde.
    private Optional<Saus> findByNummerHelper(long nummer) {
        //return Arrays.stream(alleSauzen).filter(saus -> saus.nummer() == nummer).findFirst();
        return sausService.findById(nummer);
    }
*/
    // Geeft aan dat de method hieronder een GET request
    // verwerkt naar de pagina sauzen bij @RequestMapping en nummer bij @GetMapping.
    // Het URL-adres is /sauzen/nummer
    // Get request vraag enkel data.
    @GetMapping("{id}")
    // De method findByNummer geeft een ModelAndView als return waarde.
    // @Pathvariable zorgt ervoor dat Spring de parameter nummer met de waarde van de path variable
    // met dezelfde naam (nummer) invult.
    public ModelAndView findById(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        // Toestand voor dependency injection: Als het nummer voorkomt in de return waarde van
        // findByNummerHelper dan wordt de saus al object toegevoegd in het ModelAndView.
        //
        // Toestand na dependency injection: Met de variabele van de class SausService spreken we
        // de findById(long id) aan van die class. Je bekomt hetzelfde resultaat als bij de toestand
        // voor dependency injection.
        sausService.findById(id).ifPresent(gevondenSaus ->
                modelAndView.addObject("saus", gevondenSaus));
        return modelAndView;
    }

    // onderstaande method voert een get request uit.
    // het vraagt data op
    // het alfabet wordt weergegeven op /alfabet
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }
/* Vervangen door dependency injection
    // stream aanmaken van de class saus waarbij de beginletter overeenkomt met de
    // letter die we opgeven
   private Stream<Saus> findByBeginNaamHelper(char letter) {
        return Arrays.stream(alleSauzen).filter(saus -> saus.naam().charAt(0) == letter);
        //return (Stream<Saus>) sausService.findByBeginNaam(letter);
    }
*/

    // onderstanade method voert een get method uit.
    // het vraagt data op.
    // url: /alfabet/{letter}
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginNaam(@PathVariable char letter) {
        // if waarbij we controleren of letter een onderdeel is van het alfabet
        if (Character.isLetter(letter)) {
            // if = true
            // dan krijgen we een model and view terug
            // viewName = de naam van de html painga sausAlfabet.html
            // modelName = de naam die aanspreekbaar is in html met ${...}
            // attributeValue = data die we meegeven. we geven 3 verschillende data door.
            //      1. de array van character van het alfabet, zodat die blijft weergegeven worden
            //         op het scherm.
            //      2. voor dependency injection: een stream van sauzen waarbij de naam overeenkomt
            //         met de letter van de path variabele.
            //
            //         Na dependency injection: we roepen via de variabele sausService de method
            //         findByBeginNaam(char letter) op. Die geeft een lijst terug met alle sauzen
            //         die beginnen met de letter van de path variabele.
            //      3. een boolean die true geeft omdat de path variabele een letter van het alfabet is.
            return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                    .addObject("sauzen", sausService.findByBeginNaam(letter).iterator())
                    .addObject("booleanLetter", true);
        } else {
            // if = false
            // dan krijgen we een model and view terug
            // viewName = de naam van de html painga sausAlfabet.html
            // modelName = de naam die aanspreekbaar is in html met ${...}
            // attributeValue = data die we meegeven. we geven 3 verschillende data door.
            //      1. de array van character van het alfabet, zodat die blijft weergegeven worden
            //         op het scherm.
            //      3. een boolean die false geeft omdat de pathvariabele geen letter van het alfabet is.
            return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                    .addObject("booleanLetter", false);
        }
    }

    // We maken een nieuw model (data) and view (uiterlijk)
    // viewName is de naam van de html-pagina waar we mee samenwerken
    // modelName is de naam die aangesproken wordt via ${...}
    // findByAlfabetHelper(letter).iterator() en true is de data die we doorgeven.
    // Dit is een stream van de sauzen die dezelfde beginletter hebben als de letter van de url.
    // OPMERKING!   Na een stream moet een .iterator geplaatst worden.
    //              Als dat niet gebeurt dan zal de website een foute pagina weergeven.

    // als de letter geen letter van het alfabet is. geven we als model en view.
    // viewName is de naam van de html-pagina waar we mee samenwerken
    // modelName is de naam die aangesproken wordt via ${...}
    // false is de data die we doorgeven.


    /* Mijn oplossing oef 1.16 alfabet
    Als je op een letter in de lijst klikt krijg je een pagina met daarop een tabel
    met de naam of namen en ingredienten van de sauzen.

    onderstaande code is mijn antwoord op de code.
    Ik had een pagina ontworpen dat de naam en ingredienten van de saus van de geselecteerde
    letter weergaf in een tabel vorm


    //dit is een array dat alle letters van het alfabet in characters bevat.
    private final Character[] alfabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    // We voeren een get request uit op de /sauzen/alfabet, get request = data opvragen
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        // We maken een nieuw model (data) and view (uiterlijk)
        // viewName is de naam van de html-pagina waar we mee samenwerken
        // modelName is de naam die aangesproken wordt via ${...}
        // alfabet is de data die we doorgeven. Dit is een array van characters
        return new ModelAndView("sausPerLetter", "alfabet",
                alfabet);
    }

    private Stream<Saus> findByAlfabetHelper(char letter) {
        // We filteren de array op sausnaam en vergelijken de eerste letter van de saus
        // met de letter die we opgeven in de functie. Als die gelijk is blijft die in de
        // stream aanwezig.
        return Arrays.stream(alleSauzen).filter(saus -> saus.naam().charAt(0) == letter);
    }

    // we voeren een get request uit op /sauzen/alfabet/{letter}, get request = data opvragen
    @GetMapping("alfabet/{letter}")
    // @pathVariabele = {letter} wordt ingevuld door de parameter letter.
    public ModelAndView findByletter(@PathVariable char letter) {
        // Probleemstelling: Er kon eender welk character in de brouwser weergeven worden.
        // Oplossing: if functie onderaan. Die controleert of het character tot het alfabet
        // behoord.
        // We geven extra date mee met de modelAndView, die de waarde true of false bevat.
        // later maakt th:if hier van gebruik om te beslissen welke gegeven op het scherm
        // getoond worden.
        if (Character.isLetter(letter)) {
            // We maken een nieuw model (data) and view (uiterlijk)
            // viewName is de naam van de html-pagina waar we mee samenwerken
            // modelName is de naam die aangesproken wordt via ${...}
            // findByAlfabetHelper(letter).iterator() en true is de data die we doorgeven.
            // Dit is een stream van de sauzen die dezelfde beginletter hebben als de letter van de url.
            // OPMERKING!   Na een stream moet een .iterator geplaatst worden.
            //              Als dat niet gebeurt dan zal de website een foute pagina weergeven.
            return new ModelAndView("sausPerLetter", "sauzen",
                    findByAlfabetHelper(letter).iterator())
                    .addObject("booleanLetter", true);
            // als de letter geen letter van het alfabet is. geven we als model en view.
            // viewName is de naam van de html-pagina waar we mee samenwerken
            // modelName is de naam die aangesproken wordt via ${...}
            // false is de data die we doorgeven.
        } else {
            return new ModelAndView("sausPerLetter",
                    "booleanLetter", false);
        }
    }*/

}
