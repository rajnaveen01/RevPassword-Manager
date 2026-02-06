package com.revpm.service;

import com.revpm.dao.VerificationDAO;
import com.revpm.model.VerificationCode;

import java.util.Date;
import java.util.Random;

public class VerificationService {

    private final VerificationDAO verificationDAO = new VerificationDAO();

    // Generate Verification Code
    public String generateCode(long userId) {
        String code = String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit code

        Date expiryTime = new Date(System.currentTimeMillis() + 2 * 60 * 1000); // 2 minutes

        VerificationCode vc = new VerificationCode(userId, code, expiryTime);
        verificationDAO.saveCode(vc);

        return code;
    }

    // Validate Code
    public boolean validateCode(long userId, String code) {
        VerificationCode vc = verificationDAO.getValidCode(userId, code);

        if (vc == null) {
            System.out.println("Invalid verification code!");
            return false;
        }

        if (vc.isExpired()) {
            System.out.println("Verification code expired!");
            return false;
        }

        verificationDAO.markCodeUsed(vc.getCodeId());
        return true;
    }
}
