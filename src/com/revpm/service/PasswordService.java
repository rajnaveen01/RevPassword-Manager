package com.revpm.service;

import com.revpm.dao.PasswordDAO;
import com.revpm.model.PasswordEntry;
import com.revpm.util.EncryptionUtil;
import com.revpm.util.PasswordGenerator;
import java.util.List;

public class PasswordService {

    private final PasswordDAO passwordDAO = new PasswordDAO();

    public boolean addPassword(long userId, String accountName, String username, String password) {  
        String encryptedPassword = EncryptionUtil.encryptAES(password);
        PasswordEntry entry = new PasswordEntry(userId, accountName, username, encryptedPassword);
        return passwordDAO.addPassword(entry);
    }

    public List<PasswordEntry> listPasswords(long userId) {
        return passwordDAO.getAllPasswords(userId);
    }

    public String viewPassword(long userId, String accountName) {
        PasswordEntry entry = passwordDAO.findByAccountName(userId, accountName);
        if (entry != null) {
            return EncryptionUtil.decryptAES(entry.getAccountPassword());
        }
        return null;
    }

    public boolean updatePassword(long entryId, String newPassword) {
        return passwordDAO.updatePassword(entryId, EncryptionUtil.encryptAES(newPassword));
    }

    public boolean deletePassword(long entryId) {
        return passwordDAO.deletePassword(entryId);
    }

    public List<PasswordEntry> searchPasswords(long userId, String keyword) {
        return passwordDAO.searchPasswords(userId, keyword);
    }

    public String generateStrongPassword(int length) {
        return PasswordGenerator.generate(length, true, true, true, true);
    }

    public String generateCustomPassword(int length, boolean upper, boolean lower, boolean digits, boolean special) {
        try {
            return PasswordGenerator.generate(length, upper, lower, digits, special);
        } catch (IllegalArgumentException e) {
            return "Error: You must select at least one character type!";
        }
    }
}