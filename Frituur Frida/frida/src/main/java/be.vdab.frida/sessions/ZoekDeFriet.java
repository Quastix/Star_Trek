package be.vdab.frida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Random;

@Component
// Spring maakt een bean van de class zodat, die er later bewerkingen mee kan uitvoeren.
// Omdat de class geen service, controller of repository is gebruiken we @Component
@SessionScope
public class ZoekDeFriet implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int deur = new Random().nextInt(7);

    public int getDeur() {
        return deur;
    }

}
