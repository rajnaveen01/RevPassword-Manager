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
            System.out.println("\n================ AUTHENTICATION MENU =================");
            System.out.println("   1. Register New Account");
            System.out.println("   2. Login to Vault");
            System.out.println("   3. Recover Account (Forgot Password)");
            System.out.println("   0. Exit Application");
            System.out.println("======================================================");
            
            int choice = InputUtil.nextInt(">> Select Option: ");

            switch (choice) {
                case 1: register(); break;
                case 2: login(); break;
                case 3: forgotPassword(); break;
                case 0: 
                    System.out.println("\n>> [INFO] Exiting Application. Stay Secure!");
                    System.exit(0);
                    break;
                default: System.out.println(">> [ERROR] Invalid selection. Please try again.");
            }
        }
    }

    private void register() {
        System.out.println("\n------------------ REGISTRATION ------------------");
        String name = InputUtil.nextLine(">> Enter Full Name: ");
        
        String email;
        while (true) {
            email = InputUtil.nextLine(">> Enter Email Address: ");
            if (com.revpm.util.ValidationUtil.isValidEmail(email)) break;
            System.out.println(">> [ERROR] Invalid email format. Example: user@domain.com");
        }

        String password;
        while (true) {
            password = InputUtil.nextLine(">> Create Master Password: ");
            if (com.revpm.util.ValidationUtil.isStrongPassword(password)) break;
            System.out.println(">> [ERROR] Weak Password! Must contain 8+ chars, Upper, Lower, Digit, & Symbol.");
        }

        boolean success = userService.registerUser(name, email, password);
        if (success) {
            System.out.println(">> [SUCCESS] Account registered successfully! You may now login.");
        } else {
            System.out.println(">> [ERROR] Registration failed. Email may already serve an account.");
        }
    }

    private void login() {
        System.out.println("\n--------------------- LOGIN ----------------------");
        String email = InputUtil.nextLine(">> Enter Email: ");

        if (!userService.isEmailRegistered(email)) {
            System.out.println(">> [ERROR] No account found with this email (Please Register / Verify email)");
            return;
        }

        String password = InputUtil.nextLine(">> Enter Master Password: ");
        int userId = userService.login(email, password);

        if (userId > 0) {
            System.out.println(">> [SUCCESS] Identity Verified. Opening Vault...");
            new UserMenu(userId, email).start();
        } else {
            System.out.println(">> [ERROR] Authentication Failed. Invalid Credentials.");
        }
    }

    private void forgotPassword() {
        System.out.println("\n---------------- ACCOUNT RECOVERY ----------------");
        String email = InputUtil.nextLine(">> Enter Registered Email: ");
        Long userId = userService.getUserIdByEmail(email);

        if (userId == null) {
            System.out.println(">> [ERROR] User not found in our database.");
            return;
        }

        List<SecurityQuestion> questions = securityService.getQuestions(userId);
        if (questions.isEmpty()) {
            System.out.println(">> [ERROR] Recovery not configured. Please contact support.");
            return;
        }

        List<String> answers = new ArrayList<>();
        System.out.println("\n>> Please answer the following security questions:");
        for (SecurityQuestion q : questions) {
            answers.add(InputUtil.nextLine("   [Q] " + q.getQuestion() + "\n   [A] "));
        }

        if (securityService.verifySecurityAnswers(userId, answers)) {
            System.out.println(">> [SUCCESS] Identity Verified.");
            String newPass;
            while(true) {
                newPass = InputUtil.nextLine(">> Set New Master Password: ");
                if (com.revpm.util.ValidationUtil.isStrongPassword(newPass)) break;
                System.out.println(">> [ERROR] Password too weak. Try again.");
            }
            boolean success = userService.resetMasterPassword(userId, newPass);
            System.out.println(success ? ">> [SUCCESS] Password reset completed!" : ">> [ERROR] System error during reset.");
        } else {
            System.out.println(">> [ERROR] Security answers did not match.");
        }
    }
}