package tracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MainTests {
    @Test
    @DisplayName("email should be checked")
    void testIsValidEmail() {
        assertTrue(Main.isValidEmail("some@gmail.com"));
        assertTrue(Main.isValidEmail("SoMe123@gmail.com"));
        assertFalse(Main.isValidEmail("somegmail.com"));
    }

    @ParameterizedTest
    @CsvSource({
            "some@gmail.com, true",
            "SoMe123@gmail.com, true",
            "somegmail.com, false"
    })
    void testIsValidEmailParameterized(String email, boolean expected) {
        assertEquals(expected, Main.isValidEmail(email));
    }
}
