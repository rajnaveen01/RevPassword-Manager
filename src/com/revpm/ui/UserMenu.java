package com.revpm.ui;

import com.revpm.service.UserService;
import com.revpm.service.VerificationService;
import com.revpm.util.InputUtil;

public class UserMenu {

    private int userId;
    private String userEmail;
    private UserService userService = new UserService();
    private VerificationService verificationService = new VerificationService();

    public UserMenu(int userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void start() {
        while (true) {
            System.out.println("\n================= USER DASHBOARD =====================");
            System.out.println("   User: " + userEmail);
            System.out.println("------------------------------------------------------");
            System.out.println("   1. Manage Password Vault");
            System.out.println("   2. Update Profile Details");
            System.out.println("   3. Manage Security Questions");
            System.out.println("   4. Change Master Password");
            System.out.println("   0. Logout");
            System.out.println("======================================================");
            
            int choice = InputUtil.nextInt(">> Select Action: ");

            switch (choice) {
                case 1: new PasswordMenu(userId, userEmail).start(); break;
                case 2: updateProfile(); break;
                case 3: new SecurityMenu(userId).start(); break;
                case 4: changeMasterPassword(); break;
                case 0:
                    System.out.println("\n>> [INFO] Logging out securely...");
                    return;
                default: System.out.println(">> [ERROR] Invalid choice.");
            }
        }
    }

    private void updateProfile() {
        System.out.println("\n-------------- SENSITIVE OPERATION ---------------");
        System.out.println(">> Initiating Profile Update Protocol...");
        
        String code = verificationService.generateCode(userId);
        System.out.println(">> [MOCK EMAIL] Verification Code: " + code);

        String inputCode = InputUtil.nextLine(">> Enter 6-Digit Code: ");
        if (!verificationService.validateCode(userId, inputCode)) {
            System.out.println(">> [ERROR] Verification Failed. Access Denied.");
            return;
        }

        String name = InputUtil.nextLine(">> Enter New Name: ");
        String email;
        while (true) {
            email = InputUtil.nextLine(">> Enter New Email: ");
            if (com.revpm.util.ValidationUtil.isValidEmail(email)) break;
            System.out.println(">> [ERROR] Invalid email format.");
        }
        
        if (userService.updateProfile(userId, name, email)) {
            System.out.println(">> [SUCCESS] Profile updated!!");
            this.userEmail = email; 
        } else {
            System.out.println(">> [ERROR] Update failed. Email might differ.");
        }
    }

    private void changeMasterPassword() {
        System.out.println("\n------------- CHANGE MASTER PASSWORD -------------");
        String oldPass = InputUtil.nextLine(">> Enter Current Password: ");
        
        String newPass;
        while (true) {
            newPass = InputUtil.nextLine(">> Enter New Password: ");
            if (com.revpm.util.ValidationUtil.isStrongPassword(newPass)) break;
            System.out.println(">> [ERROR] Password too weak.");
        }

        if (userService.changePassword(userId, oldPass, newPass)) {
            System.out.println(">> [SUCCESS] Master Password changed successfully.");
        } else {
            System.out.println(">> [ERROR] Current password verification failed.");
        }
    }
}