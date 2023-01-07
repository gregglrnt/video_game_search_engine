package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class LauncherTest {
    @Test
       public void runLauncher() {
            Launcher l = new Launcher();
            Launcher.main(new String[] {});
    }
}
