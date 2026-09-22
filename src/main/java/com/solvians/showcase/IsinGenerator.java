package com.solvians.showcase;

import java.util.concurrent.ThreadLocalRandom;

public class IsinGenerator {
    private static final String ISIN_WITHOUT_CHECK_DIGIT_PATTERN = "[A-Z]{2}[A-Z0-9]{9}";
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder isin = new StringBuilder();

        // First 2 characters must be uppercase letters
        for (int i = 0; i < 2; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            isin.append(letter);
        }

        // Next 9 characters must be uppercase alphanumeric
        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            isin.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }

        int checkDigit = calculateCheckDigit(isin.toString());
        isin.append(checkDigit);

        return isin.toString();
    }

    public int calculateCheckDigit(String isinWithoutCheckDigit) {
        validate(isinWithoutCheckDigit);

        String numericValue = convertToNumericValue(isinWithoutCheckDigit);

        int sum = 0;
        boolean shouldDouble = true;

        for (int i = numericValue.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numericValue.charAt(i));

            if (shouldDouble) {
                digit *= 2;
            }

            // add individual digits to result (e.g. 14 -> 1 + 4)
            sum += digit / 10;
            sum += digit % 10;

            shouldDouble = !shouldDouble;
        }

        int remainder = sum % 10;

        if (remainder == 0) {
            return 0;
        }

        return 10 - remainder;
    }

    private void validate(String isinWithoutCheckDigit) {
        if (isinWithoutCheckDigit == null) {
            throw new IllegalArgumentException("ISIN must not be null");
        }

        if (!isinWithoutCheckDigit.matches(ISIN_WITHOUT_CHECK_DIGIT_PATTERN)) {
            throw new IllegalArgumentException(
                    "ISIN must contain 2 uppercase letters followed by 9 alphanumeric characters"
            );
        }
    }

    private String convertToNumericValue(String isinWithoutCheckDigit) {
        StringBuilder numericValue = new StringBuilder();

        for (char character : isinWithoutCheckDigit.toCharArray()) {
            if (Character.isLetter(character)) {
                numericValue.append(character - 'A' + 10);
            } else {
                numericValue.append(character);
            }
        }

        return numericValue.toString();
    }
}

