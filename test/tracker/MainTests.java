package tracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StudentTests {
    @Test
    @DisplayName("email should be checked")
    void testIsValidEmail() {
        assertTrue(Student.isValidEmail("some@gmail.com"));
        assertTrue(Student.isValidEmail("SoMe123@gmail.com"));
        assertFalse(Student.isValidEmail("somegmail.com"));
    }

    @ParameterizedTest(name = "{index} : isValidEmail({1}) => {2}")
    @CsvSource({
            "some@gmail.com, true",
            "SoMe123@gmail.com, true",
            "somegmail.com, false"
    })
    void testIsValidEmailParameterized(String email, boolean expected) {
        assertEquals(expected, Student.isValidEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"some@mail.com", "another@email.org"})
    void testIsValidEmailParameterizedValues(String arg){
        assertTrue(Student.isValidEmail(arg));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void testIsValidEmailNullAndEmpty(String arg) {
        assertTrue(arg == null || arg.trim().isEmpty());
    }
}
