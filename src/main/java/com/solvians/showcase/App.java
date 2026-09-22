package com.solvians.showcase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException(
                    "Expected number of threads and number of certificate updates"
            );
        }

        int threads = Integer.parseInt(args[0]);
        int updates = Integer.parseInt(args[1]);

        List<String> results = generateUpdates(threads, updates);

        results.forEach(System.out::println);
    }

    static List<String> generateUpdates(int threads, int updates) throws Exception {
        if (threads <= 0) {
            throw new IllegalArgumentException(
                    "Number of threads must be greater than 0"
            );
        }

        if (updates < 0) {
            throw new IllegalArgumentException(
                    "Number of certificate updates must not be negative"
            );
        }

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        try {
            IsinGenerator isinGenerator = new IsinGenerator();
            List<Future<String>> futures = new ArrayList<>();

            for (int i = 0; i < updates; i++) {
                futures.add(
                        executor.submit(
                                new CertificateUpdateGenerator(isinGenerator)
                        )
                );
            }

            List<String> results = new ArrayList<>();

            for (Future<String> future : futures) {
                results.add(future.get());
            }

            return results;
        } finally {
            executor.shutdown();
        }
    }
}