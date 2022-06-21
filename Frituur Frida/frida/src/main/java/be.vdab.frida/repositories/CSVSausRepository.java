package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Met @Component maken we een bean van de class. Hierdoor kunnen we bewerkingen op de class
// uitvoeren met Spring.
@Component
// Public verwijdert in class zodat de class niet buiten hun package
// aangesproken kunnen worden.
class CSVSausRepository implements SausRepository {

    private final Path pad;

    CSVSausRepository(@Value("${CSVSauzenPad}") Path pad) {
        this.pad = pad;
    }

    // Overridden van de findAll() method van de interface SausRepository
    @Override
    public List<Saus> findAll() {
        List<Saus> sauzen = new ArrayList<>();
        // De method readAllLines van de class Files werpt een IOException
        try {
            // Je leest met een BufferedReader blok per blok uit een bestand. De class BufferedReader
            // bepaalt zelf de optimale grootte van zo’n blok.
            var listSauzenString =
                    // Je krijgt een BufferedReader van de static method newBufferedReader van de
                    // class Files. Je geeft als parameter een Path mee met de locatie en naam van
                    // het te lezen bestand.
                    Files.readAllLines(pad);
            // Je leest een eerste regel (de tekens tot juist voor een Enter) uit de gelezen blok.
            // Als het bestand leeg is, krijg je null terug. Anders krijg je de eerste regel terug
            // als een String.
            for (var listSaus : listSauzenString) {
                // We delen de string van de regel op met de methode split op de komma ","
                // We declareren dit als een array van Strings met alle woorden apart.
                String[] sausGegevens = listSaus.split(",");
                // we maken van die gegevens een object saus met de mehtod createSaus.
                var saus = SausRepository.createSaus(sausGegevens);
                // het object saus wordt daarna toegevoegd aan de lijst van sauzen.
                sauzen.add(saus);
            }
            return sauzen;
            // opvangen van de IOException die de method readAllLines van de class Files
            // kan werpen.
        } catch (IOException ex) {
            throw new SausRepositoryException("Fout bij lezen van CSV file", ex);
        }
    }

}