package com.revpm.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    private static final SecureRandom random = new SecureRandom();

    public static String generate(int length, boolean useUpper, boolean useLower,
                                  boolean useDigits, boolean useSpecial) {

        StringBuilder charPool = new StringBuilder();

        if (useUpper) charPool.append(UPPER);
        if (useLower) charPool.append(LOWER);
        if (useDigits) charPool.append(DIGITS);
        if (useSpecial) charPool.append(SPECIAL);

        if (charPool.length() == 0) {
            throw new IllegalArgumentException("At least one character type must be selected!");
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            password.append(charPool.charAt(index));
        }

        return password.toString();
    }
}
