package com.revpm.ui;

import com.revpm.model.PasswordEntry;
import com.revpm.service.PasswordService;
import com.revpm.service.UserService;
import com.revpm.util.InputUtil;
import java.util.List;

public class PasswordMenu {

    private PasswordService passwordService = new PasswordService();
    private UserService userService = new UserService(); // Added to verify password
    private long userId;
    private String userEmail; // Needed for re-verification

    public PasswordMenu(long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- PASSWORD MENU ---");
            System.out.println("1. Add Password");
            System.out.println("2. List Passwords");
            System.out.println("3. View Password (Secure)");
            System.out.println("4. Update Password");
            System.out.println("5. Delete Password");
            System.out.println("6. Search Password");
            System.out.println("7. Generate Strong Password");
            System.out.println("0. Back");

            int choice = InputUtil.nextInt("Choose option: ");

            switch (choice) {
                case 1: addPassword(); break;
                case 2: listPasswords(); break;
                case 3: viewPassword(); break;
                case 4: updatePassword(); break;
                case 5: deletePassword(); break;
                case 6: searchPassword(); break;
                case 7: generatePassword(); break;
                case 0: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void addPassword() {
        String accountName = InputUtil.nextLine("Account Name: ");
        String username = InputUtil.nextLine("Username: ");
        
        // FIXED: Enforce Strong Password
        String password;
        while(true) {
            password = InputUtil.nextLine("Password: ");
            if (com.revpm.util.ValidationUtil.isStrongPassword(password)) break;
            System.out.println("Error: Password is too weak (needs 8+ chars, Upper, Lower, Digit, Special).");
        }
        
        boolean success = passwordService.addPassword(userId, accountName, username, password);
        // Error message is now handled nicely in DAO/Service if duplicate
        if(success) System.out.println("Password added!");
    }

    private void listPasswords() {
        List<PasswordEntry> list = passwordService.listPasswords(userId);
        System.out.printf("%-5s | %-20s | %-20s%n", "ID", "Account", "Username");
        System.out.println("------------------------------------------------");
        for (PasswordEntry p : list) {
            System.out.printf("%-5d | %-20s | %-20s%n", p.getEntryId(), p.getAccountName(), p.getAccountUsername());
        }
    }

    private void viewPassword() {
        String accountName = InputUtil.nextLine("Enter Account Name to View: ");
        
        // REQ: Master Password Re-entry
        String masterPass = InputUtil.nextLine("Verify Master Password to reveal: ");
        if (userService.login(userEmail, masterPass) == -1) {
            System.out.println("Authentication Failed! Access Denied.");
            return;
        }

        String password = passwordService.viewPassword(userId, accountName);
        if (password != null) {
            System.out.println("--------------------------------");
            System.out.println("Decrypted Password: " + password);
            System.out.println("--------------------------------");
        } else {
            System.out.println("Account not found.");
        }
    }

    private void updatePassword() {
        long entryId = InputUtil.nextLong("Entry ID: ");
        
        // FIXED: Enforce Strong Password
        String newPassword;
        while(true) {
            newPassword = InputUtil.nextLine("New Password: ");
            if (com.revpm.util.ValidationUtil.isStrongPassword(newPassword)) break;
            System.out.println("Error: Password is too weak (needs 8+ chars, Upper, Lower, Digit, Special).");
        }

        boolean success = passwordService.updatePassword(entryId, newPassword);
        System.out.println(success ? "Updated!" : "Failed!");
    }

    private void deletePassword() {
        long entryId = InputUtil.nextLong("Entry ID: ");
        boolean success = passwordService.deletePassword(entryId);
        System.out.println(success ? "Deleted!" : "Failed!");
    }

    private void searchPassword() {
        String keyword = InputUtil.nextLine("Keyword: ");
        List<PasswordEntry> list = passwordService.searchPasswords(userId, keyword);
        for (PasswordEntry p : list) {
            System.out.println(p.getEntryId() + " | " + p.getAccountName());
        }
    }

    private void generatePassword() {
        System.out.println("\n--- GENERATE PASSWORD ---");
        int length = InputUtil.nextInt("Length (min 8): ");
        if(length < 8) {
            System.out.println("Auto-correcting length to 8.");
            length = 8;
        }

        // REQ: Providing parameters like characters, numbers, etc.
        boolean useUpper = InputUtil.nextLine("Include Uppercase? (Y/N): ").equalsIgnoreCase("Y");
        boolean useLower = InputUtil.nextLine("Include Lowercase? (Y/N): ").equalsIgnoreCase("Y");
        boolean useDigits = InputUtil.nextLine("Include Numbers? (Y/N): ").equalsIgnoreCase("Y");
        boolean useSpecial = InputUtil.nextLine("Include Special Chars? (Y/N): ").equalsIgnoreCase("Y");

        if (!useUpper && !useLower && !useDigits && !useSpecial) {
            System.out.println("Invalid: You must select at least one option!");
            return;
        }

        String pwd = passwordService.generateCustomPassword(length, useUpper, useLower, useDigits, useSpecial);
        System.out.println("--------------------------------");
        System.out.println("Generated: " + pwd);
        System.out.println("--------------------------------");
    }
}