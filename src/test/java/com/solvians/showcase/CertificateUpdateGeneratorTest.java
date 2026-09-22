package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateGeneratorTest {

    private final CertificateUpdateGenerator generator =
            new CertificateUpdateGenerator(new IsinGenerator());

    @Test
    void shouldRejectNullIsinGenerator() {
        assertThrows(
                NullPointerException.class,
                () -> new CertificateUpdateGenerator(null)
        );
    }

    @Test
    void shouldGenerateCertificateUpdateWithValidFields() throws Exception {
        String result = generator.call();
        String[] fields = result.split(",");

        assertEquals(6, fields.length);

        long timestamp = Long.parseLong(fields[0]);
        String isin = fields[1];
        double bidPrice = Double.parseDouble(fields[2]);
        int bidSize = Integer.parseInt(fields[3]);
        double askPrice = Double.parseDouble(fields[4]);
        int askSize = Integer.parseInt(fields[5]);

        assertTrue(timestamp > 0);
        assertTrue(isin.matches("[A-Z]{2}[A-Z0-9]{9}[0-9]"));

        assertTrue(bidPrice >= 100.00 && bidPrice <= 200.00);
        assertTrue(bidSize >= 1000 && bidSize <= 5000);

        assertTrue(askPrice >= 100.00 && askPrice <= 200.00);
        assertTrue(askSize >= 1000 && askSize <= 10000);
    }

    @Test
    void shouldFormatPricesWithTwoDecimalPlaces() throws Exception {
        String result = generator.call();
        String[] fields = result.split(",");

        assertTrue(fields[2].matches("\\d+\\.\\d{2}"));
        assertTrue(fields[4].matches("\\d+\\.\\d{2}"));
    }
}