package com.revpm.service;

import com.revpm.dao.SecurityQuestionDAO;
import com.revpm.dao.UserDAO;
import com.revpm.model.SecurityQuestion;
import com.revpm.model.User;
import com.revpm.util.EncryptionUtil;

import java.util.List;
import java.util.Optional;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final SecurityQuestionDAO securityQuestionDAO = new SecurityQuestionDAO();

    // Register
    public boolean register(String name, String email, String password) {
        String hashedPassword = EncryptionUtil.hashSHA256(password);
        User user = new User(name, email, hashedPassword);
        return userDAO.registerUser(user);
    }

    // Login
    public User login(String email, String password) {
        Optional<User> optionalUser = userDAO.findByEmail(email);

        if (!optionalUser.isPresent()) {
            System.out.println("User not found!");
            return null;
        }

        User user = optionalUser.get();
        String hashedInput = EncryptionUtil.hashSHA256(password);

        if (hashedInput.equals(user.getMasterPassword())) {
            System.out.println("Login successful!");
            return user;
        }

        System.out.println("Invalid password!");
        return null;
    }

    // Reset Password using Security Questions
    public boolean resetPassword(String email, List<String> answers, String newPassword) {
        Optional<User> optionalUser = userDAO.findByEmail(email);

        if (!optionalUser.isPresent()) {
            System.out.println("User not found!");
            return false;
        }

        User user = optionalUser.get();
        List<SecurityQuestion> questions = securityQuestionDAO.getQuestionsByUser(user.getUserId());

        for (int i = 0; i < questions.size(); i++) {
            String storedHash = questions.get(i).getAnswer();
            String inputHash = EncryptionUtil.hashSHA256(answers.get(i));

            if (!storedHash.equalsIgnoreCase(inputHash)) {
                System.out.println("Wrong security answer!");
                return false;
            }
        }

        String newHashedPassword = EncryptionUtil.hashSHA256(newPassword);
        return userDAO.updateMasterPassword(user.getUserId(), newHashedPassword);
    }
}
