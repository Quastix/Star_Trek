import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        final Pattern PATTERN =
                // De regular expression stelt de tekst "USD": voor, gevolgd door een getal (met de koers)
                // ^ het begin van een lijn
                // . eender welk character
                //
                Pattern.compile("^.*\"USD\": *(\\d+\\.?\\d*).*$");

        System.out.println(PATTERN);
    }


}
