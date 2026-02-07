package com.revpm.service;

import com.revpm.dao.UserDAO;
import com.revpm.model.User;
import com.revpm.util.EncryptionUtil;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean registerUser(String name, String email, String password) {
        if (userDAO.findByEmail(email).isPresent()) {
            return false; 
        }
        String hashedPassword = EncryptionUtil.hashSHA256(password);
        User user = new User(name, email, hashedPassword);
        return userDAO.registerUser(user);
    }

    public int login(String email, String password) {
        Optional<User> optionalUser = userDAO.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (EncryptionUtil.hashSHA256(password).equals(user.getMasterPassword())) {
                return (int) user.getUserId();
            }
        }
        return -1; 
    }

    public boolean isEmailRegistered(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    public boolean updateProfile(long userId, String name, String email) {
        return userDAO.updateProfile(userId, name, email);
    }

    public boolean changePassword(long userId, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userDAO.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (EncryptionUtil.hashSHA256(oldPassword).equals(user.getMasterPassword())) {
                String newHash = EncryptionUtil.hashSHA256(newPassword);
                return userDAO.updateMasterPassword(userId, newHash);
            }
        }
        return false;
    }

    public Long getUserIdByEmail(String email) {
        Optional<User> u = userDAO.findByEmail(email);
        return u.map(User::getUserId).orElse(null);
    }

    public boolean resetMasterPassword(long userId, String newPassword) {
        return userDAO.updateMasterPassword(userId, EncryptionUtil.hashSHA256(newPassword));
    }
}