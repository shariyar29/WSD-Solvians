package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IsinGeneratorTest {
    private final IsinGenerator generator = new IsinGenerator();

    @Test
    void shouldCalculateCheckDigitForKnownIsin() {
        int checkDigit = generator.calculateCheckDigit("DE123456789");

        assertEquals(6, checkDigit);
    }

    @Test
    void shouldThrowExceptionWhenIsinContainsInvalidCharacters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.calculateCheckDigit("DE12345@789")
        );
    }

    @Test
    void shouldThrowExceptionWhenIsinIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.calculateCheckDigit(null)
        );
    }

    @Test
    void shouldThrowExceptionWhenIsinHasInvalidLength() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.calculateCheckDigit("DE123")
        );
    }

    @Test
    void shouldThrowExceptionWhenIsinContainsLowercaseLetters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.calculateCheckDigit("DE12345678a")
        );
    }

    @Test
    void shouldGenerateValidIsinsRepeatedly() {
        for (int i = 0; i < 100; i++) {
            String isin = generator.generate();

            assertEquals(12, isin.length());
            assertTrue(isin.matches("[A-Z]{2}[A-Z0-9]{9}[0-9]"));

            String isinWithoutCheckDigit = isin.substring(0, 11);

            int expectedCheckDigit =
                    generator.calculateCheckDigit(isinWithoutCheckDigit);

            int actualCheckDigit =
                    Character.getNumericValue(isin.charAt(11));

            assertEquals(expectedCheckDigit, actualCheckDigit);
        }
    }

    @Test
    void shouldThrowExceptionWhenIsinDoesNotStartWithTwoLetters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.calculateCheckDigit("1E123456789")
        );
    }
}