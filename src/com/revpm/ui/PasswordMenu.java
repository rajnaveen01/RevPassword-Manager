package com.revpm.ui;

import com.revpm.model.PasswordEntry;
import com.revpm.service.PasswordService;
import com.revpm.service.UserService;
import com.revpm.util.InputUtil;
import java.util.List;

public class PasswordMenu {

    private PasswordService passwordService = new PasswordService();
    private UserService userService = new UserService();
    private long userId;
    private String userEmail;

    public PasswordMenu(long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void start() {
        while (true) {
            System.out.println("\n================== VAULT OPERATIONS ==================");
            System.out.println("   1. Add New Password");
            System.out.println("   2. List All Passwords");
            System.out.println("   3. View Password (Decrypt)");
            System.out.println("   4. Update Password");
            System.out.println("   5. Delete Password");
            System.out.println("   6. Search Vault");
            System.out.println("   7. Generate Strong Password");
            System.out.println("   0. Return to Dashboard");
            System.out.println("======================================================");

            int choice = InputUtil.nextInt(">> Select Option: ");

            switch (choice) {
                case 1: addPassword(); break;
                case 2: listPasswords(); break;
                case 3: viewPassword(); break;
                case 4: updatePassword(); break;
                case 5: deletePassword(); break;
                case 6: searchPassword(); break;
                case 7: generatePassword(); break;
                case 0: return;
                default: System.out.println(">> [ERROR] Invalid choice.");
            }
        }
    }

    private void addPassword() {
        System.out.println("\n-- Add New Entry --");
        String accountName = InputUtil.nextLine(">> Account Name (e.g., Google): ");
        String username = InputUtil.nextLine(">> Username/Login ID: ");
        
        String password;
        while(true) {
            password = InputUtil.nextLine(">> Password: ");
            if (com.revpm.util.ValidationUtil.isStrongPassword(password)) break;
            System.out.println(">> [WARNING] Password is weak. We recommend stronger credentials.");
            String confirm = InputUtil.nextLine(">> Use anyway? (Y/N): ");
            if(confirm.equalsIgnoreCase("Y")) break;
        }
        
        if(passwordService.addPassword(userId, accountName, username, password)) {
            System.out.println(">> [SUCCESS] Entry encrypted and saved to Vault.");
        } else {
            System.out.println(">> [ERROR] Failed to save. Account name might exist.");
        }
    }

    private void listPasswords() {
        List<PasswordEntry> list = passwordService.listPasswords(userId);
        System.out.println("\n-------------------- STORED ACCOUNTS -----------------");
        System.out.printf("| %-6s | %-20s | %-20s |%n", "ID", "ACCOUNT", "USERNAME");
        System.out.println("+--------+----------------------+----------------------+");
        for (PasswordEntry p : list) {
            System.out.printf("| %-6d | %-20s | %-20s |%n", p.getEntryId(), p.getAccountName(), p.getAccountUsername());
        }
        System.out.println("------------------------------------------------------");
    }

    private void viewPassword() {
        System.out.println("\n-- Decrypt Password --");
        String accountName = InputUtil.nextLine(">> Enter Account Name: ");
        
        // Security Re-check
        String masterPass = InputUtil.nextLine(">> Verify Master Password: ");
        if (userService.login(userEmail, masterPass) == -1) {
            System.out.println(">> [SECURITY ALERT] Authentication Failed. Access Logged.");
            return;
        }

        String password = passwordService.viewPassword(userId, accountName);
        if (password != null) {
            System.out.println("\n   +------------------------------------------+");
            System.out.println("   | DECRYPTED PASSWORD: " + password);
            System.out.println("   +------------------------------------------+");
        } else {
            System.out.println(">> [ERROR] Account not found.");
        }
    }

    private void updatePassword() {
        long entryId = InputUtil.nextLong(">> Enter Entry ID to Update: ");
        String newPassword = InputUtil.nextLine(">> Enter New Password: ");
        
        if (passwordService.updatePassword(entryId, newPassword)) {
            System.out.println(">> [SUCCESS] Vault entry updated.");
        } else {
            System.out.println(">> [ERROR] Update failed. ID may be invalid.");
        }
    }

    private void deletePassword() {
        long entryId = InputUtil.nextLong(">> Enter Entry ID to Delete: ");
        if (passwordService.deletePassword(entryId)) {
            System.out.println(">> [SUCCESS] Entry moved to Trash (Soft Deleted).");
        } else {
            System.out.println(">> [ERROR] Delete failed. ID not found.");
        }
    }

    private void searchPassword() {
        String keyword = InputUtil.nextLine(">> Enter Search Keyword: ");
        List<PasswordEntry> list = passwordService.searchPasswords(userId, keyword);
        System.out.println("\n-- Search Results --");
        for (PasswordEntry p : list) {
            System.out.println(">> Found: [ID: " + p.getEntryId() + "] " + p.getAccountName());
        }
    }

    private void generatePassword() {
        System.out.println("\n-- Password Generator --");
        int length = InputUtil.nextInt(">> Length (min 8): ");
        if(length < 8) length = 8;

        boolean useUpper = InputUtil.nextLine(">> Include Uppercase? (Y/N): ").equalsIgnoreCase("Y");
        boolean useLower = InputUtil.nextLine(">> Include Lowercase? (Y/N): ").equalsIgnoreCase("Y");
        boolean useDigits = InputUtil.nextLine(">> Include Numbers? (Y/N): ").equalsIgnoreCase("Y");
        boolean useSpecial = InputUtil.nextLine(">> Include Symbols? (Y/N): ").equalsIgnoreCase("Y");

        if (!useUpper && !useLower && !useDigits && !useSpecial) {
            System.out.println(">> [ERROR] Select at least one character type.");
            return;
        }

        String pwd = passwordService.generateCustomPassword(length, useUpper, useLower, useDigits, useSpecial);
        System.out.println("\n   GENERATED:  " + pwd);
    }
}