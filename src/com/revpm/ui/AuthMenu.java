package com.revpm.ui;

import com.revpm.service.SecurityService;
import com.revpm.service.UserService;
import com.revpm.util.InputUtil;
import com.revpm.model.SecurityQuestion;
import java.util.ArrayList;
import java.util.List;

public class AuthMenu {

    private UserService userService = new UserService();
    private SecurityService securityService = new SecurityService();

    public void start() {
        while (true) {
            System.out.println("\n--- AUTH MENU ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("0. Exit");
            
            int choice = InputUtil.nextInt("Choose option: "); // FIXED: Safe input

            switch (choice) {
                case 1: register(); break;
                case 2: login(); break;
                case 3: forgotPassword(); break;
                case 0: 
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                    break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void register() {
        System.out.println("\n--- REGISTER ---");
        
        String name = InputUtil.nextLine("Name: ");
        
        String email;
        while (true) {
            email = InputUtil.nextLine("Email: ");
            // VALIDATION: Check for valid email format
            if (com.revpm.util.ValidationUtil.isValidEmail(email)) {
                break;
            }
            System.out.println("Error: Invalid email format (e.g., user@example.com). Try again.");
        }

        String password;
        while (true) {
            password = InputUtil.nextLine("Master Password: ");
            // VALIDATION: Check for strong password
            if (com.revpm.util.ValidationUtil.isStrongPassword(password)) {
                break;
            }
            System.out.println("Error: Password must be 8+ chars, include Uppercase, Lowercase, Digit, and Special Char.");
        }

        boolean success = userService.registerUser(name, email, password);
        System.out.println(success ? "Registration successful!" : "Registration failed (Email might already exist).");
    }

    private void login() {
        String email = InputUtil.nextLine("Email: ");
        String password = InputUtil.nextLine("Master Password: ");

        int userId = userService.login(email, password);

        if (userId > 0) {
            System.out.println("Login successful!");
            // UPDATE: Pass both ID and Email
            new UserMenu(userId, email).start(); 
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private void forgotPassword() {
        System.out.println("\n--- RECOVERY ---");
        String email = InputUtil.nextLine("Enter Email: ");
        Long userId = userService.getUserIdByEmail(email);

        if (userId == null) {
            System.out.println("User not found.");
            return;
        }

        List<SecurityQuestion> questions = securityService.getQuestions(userId);
        if (questions.isEmpty()) {
            System.out.println("Account recovery not set up. Contact Admin.");
            return;
        }

        List<String> answers = new ArrayList<>();
        for (SecurityQuestion q : questions) {
            System.out.println("Q: " + q.getQuestion());
            answers.add(InputUtil.nextLine("Answer: "));
        }

        if (securityService.verifySecurityAnswers(userId, answers)) {
            // FIXED: Enforce Strong Password for New Password
            String newPass;
            while(true) {
                newPass = InputUtil.nextLine("Enter New Password: ");
                if (com.revpm.util.ValidationUtil.isStrongPassword(newPass)) break;
                System.out.println("Error: Password is too weak (needs 8+ chars, Upper, Lower, Digit, Special).");
            }
            
            boolean success = userService.resetMasterPassword(userId, newPass);
            System.out.println(success ? "Password reset successful!" : "Error resetting password.");
        } else {
            System.out.println("Incorrect security answers.");
        }
    }
}