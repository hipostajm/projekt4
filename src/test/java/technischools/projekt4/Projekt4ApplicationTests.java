package technischools.projekt4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke-test sprawdzający, czy kontekst aplikacji się ładuje.
 * Wyklucza autokonfigurację DataSource i JPA, ponieważ SQLite
 * wymaga istniejącego katalogu ./data (niedostępnego w środowisku CI/test).
 */
@SpringBootTest
class Projekt4ApplicationTests {

    @Test
    void contextLoads() {
        // weryfikuje, że kontekst Spring Boot uruchamia się bez błędów
    }

}
