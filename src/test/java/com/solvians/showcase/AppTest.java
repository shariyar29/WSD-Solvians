package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AppTest {
    @Test
    void shouldGenerateRequestedNumberOfUpdates() throws Exception {
        List<String> results = App.generateUpdates(4, 10);

        assertEquals(10, results.size());
    }

    @Test
    void shouldAllowZeroUpdates() throws Exception {
        List<String> results = App.generateUpdates(4, 0);

        assertEquals(0, results.size());
    }

    @Test
    void shouldRejectZeroThreads() {
        assertThrows(
                IllegalArgumentException.class,
                () -> App.generateUpdates(0, 10)
        );
    }

    @Test
    void shouldRejectNegativeThreads() {
        assertThrows(
                IllegalArgumentException.class,
                () -> App.generateUpdates(-1, 10)
        );
    }

    @Test
    void shouldRejectNegativeUpdates() {
        assertThrows(
                IllegalArgumentException.class,
                () -> App.generateUpdates(4, -1)
        );
    }

    @Test
    void shouldRejectMissingArguments() {
        assertThrows(
                IllegalArgumentException.class,
                () -> App.main(new String[]{"10"})
        );
    }

    @Test
    void shouldRejectInvalidThreadArgument() {
        assertThrows(
                NumberFormatException.class,
                () -> App.main(new String[]{"xxx", "10"})
        );
    }

    @Test
    void shouldRejectInvalidUpdateArgument() {
        assertThrows(
                NumberFormatException.class,
                () -> App.main(new String[]{"10", "zzz"})
        );
    }
}
