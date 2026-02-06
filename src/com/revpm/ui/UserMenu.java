package com.revpm.ui;

import com.revpm.service.UserService;
import com.revpm.service.VerificationService;
import com.revpm.util.InputUtil;

public class UserMenu {

    private int userId;
    private String userEmail; // Store email for session
    private UserService userService = new UserService();
    private VerificationService verificationService = new VerificationService(); // Add Service

    public UserMenu(int userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- USER MENU (" + userEmail + ") ---");
            System.out.println("1. Manage Passwords");
            System.out.println("2. Update Profile");
            System.out.println("3. Security Questions");
            System.out.println("4. Change Master Password");
            System.out.println("0. Logout");
            
            int choice = InputUtil.nextInt("Choose option: ");

            switch (choice) {
            case 1:
                new PasswordMenu(userId, userEmail).start(); // Pass email
                break;
            case 2:
                updateProfile();
                break;
            case 3:
                new SecurityMenu(userId).start();
                break;
            case 4:
                changeMasterPassword();
                break;
            case 0:
                System.out.println("Logged out!");
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }

    private void updateProfile() {
        System.out.println("\n--- SENSITIVE OPERATION: UPDATE PROFILE ---");
        
        // 1. Verify Identity
        String code = verificationService.generateCode(userId);
        System.out.println("[SYSTEM MOCK] Verification Code sent to email: " + code);

        String inputCode = InputUtil.nextLine("Enter Verification Code: ");
        if (!verificationService.validateCode(userId, inputCode)) {
            System.out.println("Verification Failed!");
            return;
        }

        // 2. Get Data
        String name = InputUtil.nextLine("New Name: ");
        
        // VALIDATION: Check email format
        String email;
        while (true) {
            email = InputUtil.nextLine("New Email: ");
            if (com.revpm.util.ValidationUtil.isValidEmail(email)) break;
            System.out.println("Invalid email format.");
        }
        
        boolean success = userService.updateProfile(userId, name, email);
        if (success) {
            System.out.println("Profile updated! Please re-login.");
            this.userEmail = email; 
        } else {
            System.out.println("Update failed!");
        }
    }

    private void changeMasterPassword() {
        String oldPass = InputUtil.nextLine("Old Password: ");
        
        // VALIDATION: Check password strength
        String newPass;
        while (true) {
            newPass = InputUtil.nextLine("New Password: ");
            if (com.revpm.util.ValidationUtil.isStrongPassword(newPass)) break;
            System.out.println("Error: Password is too weak (needs 8+ chars, Upper, Lower, Digit, Special).");
        }

        boolean success = userService.changePassword(userId, oldPass, newPass);
        System.out.println(success ? "Password changed!" : "Failed (Old password incorrect).");
    }
}