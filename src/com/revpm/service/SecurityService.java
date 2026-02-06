package com.revpm.service;

import com.revpm.dao.SecurityQuestionDAO;
import com.revpm.model.SecurityQuestion;
import com.revpm.util.EncryptionUtil;
import java.util.List;

public class SecurityService {

    private final SecurityQuestionDAO securityQuestionDAO = new SecurityQuestionDAO();

    public boolean addQuestion(long userId, String question, String answer) {
        // FIXED: Check if question already exists for this user
        List<SecurityQuestion> existing = securityQuestionDAO.getQuestionsByUser(userId);
        for (SecurityQuestion sq : existing) {
            if (sq.getQuestion().equalsIgnoreCase(question)) {
                System.out.println("Error: This security question already exists.");
                return false;
            }
        }

        String hashedAnswer = EncryptionUtil.hashSHA256(answer);
        return securityQuestionDAO.addQuestion(new SecurityQuestion(userId, question, hashedAnswer));
    }

    public List<SecurityQuestion> getQuestions(long userId) {
        return securityQuestionDAO.getQuestionsByUser(userId);
    }

    public boolean deleteQuestion(long questionId) {
        return securityQuestionDAO.deleteQuestion(questionId);
    }

    // FIXED: Verify answers logic moved to Service. Returns true only if ALL match.
    public boolean verifySecurityAnswers(long userId, List<String> providedAnswers) {
        List<SecurityQuestion> storedQuestions = securityQuestionDAO.getQuestionsByUser(userId);
        if (storedQuestions.isEmpty() || storedQuestions.size() != providedAnswers.size()) {
            return false;
        }
        for (int i = 0; i < storedQuestions.size(); i++) {
            String storedHash = storedQuestions.get(i).getAnswer();
            String providedHash = EncryptionUtil.hashSHA256(providedAnswers.get(i));
            if (!storedHash.equals(providedHash)) {
                return false;
            }
        }
        return true;
    }
}