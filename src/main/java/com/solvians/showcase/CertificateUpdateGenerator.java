package com.solvians.showcase;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class CertificateUpdateGenerator implements Callable<String> {
    private final IsinGenerator isinGenerator;

    public CertificateUpdateGenerator(IsinGenerator isinGenerator) {
        this.isinGenerator = Objects.requireNonNull(isinGenerator,"isinGenerator cannot be null");
    }

    @Override
    public String call() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long timestamp = System.currentTimeMillis();
        String isin = isinGenerator.generate();

        double bidPrice = random.nextInt(10000, 20001) / 100.0;
        int bidSize = random.nextInt(1000, 5001);

        double askPrice = random.nextInt(10000, 20001) / 100.0;
        int askSize = random.nextInt(1000, 10001);

        return String.format(
                Locale.ROOT,
                "%d,%s,%.2f,%d,%.2f,%d",
                timestamp,
                isin,
                bidPrice,
                bidSize,
                askPrice,
                askSize
        );
    }
}
