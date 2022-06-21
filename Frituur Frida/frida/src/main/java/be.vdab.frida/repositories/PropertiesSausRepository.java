package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Public verwijdert in class zodat de class niet buiten hun package
// aangesproken kunnen worden.
@Component
class PropertiesSausRepository implements SausRepository{

    private final Path pad;

    PropertiesSausRepository(@Value("${propertiesSauzenPad}") Path pad) {
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
                String[] sausGegevens = listSaus.split("[,\\s\\,:\\?]");
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
