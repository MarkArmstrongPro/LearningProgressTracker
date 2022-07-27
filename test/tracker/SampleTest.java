package tracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tracker.Main.patternMatches;

public class SampleTest {

    @Test
    void test() {
        assertEquals(5, 2 + 3);
    }

    @Test
    public void testValidEmail() {
        String emailAddress = "username@domain.com";
        String regexPattern = "^(.+)@(\\S+)$";
        assertTrue(patternMatches(emailAddress, regexPattern));
    }
}
