package com.revpm.util;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    private InputUtil() {}

    public static String nextLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // FIXED: Added error handling for non-integer inputs
    public static int nextInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // FIXED: Added error handling for non-long inputs
    public static long nextLong(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ID.");
            }
        }
    }
}