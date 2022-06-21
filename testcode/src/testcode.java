import java.util.stream.Stream;

public class testcode {
    public static void main(String[] args) {
        Stream.of("sla", "sla", "kool", "wortel", "biet", "sla")
                .distinct()
                .forEach(groente -> System.out.println(groente));
    }

}
