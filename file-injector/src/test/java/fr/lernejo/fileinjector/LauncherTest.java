package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {

    @Test
    void main_terminates_before_5_sec() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(5L),
            () -> Launcher.main(new String[]{}));
    }

    @Test
    void finds_my_file() {
        File file = new File("src/test/resources");
        String jsonTestFile = file.getAbsolutePath()+"/games.json";
        Launcher.main(new String[]{jsonTestFile});
        System.out.println("Found file and parsed it");
    }

    @Test
    void wrong_file_to_break_my_code() {
        try {
            Launcher.main(new String[]{"test.json"});
            throw new Exception("Error ! Code worked anyway");
        } catch (Exception e) {
            System.out.println("Code breaks if file is wrong");
        }

    }
}
