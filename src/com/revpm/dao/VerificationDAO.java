package com.revpm.dao;

import com.revpm.model.VerificationCode;
import com.revpm.util.DBConnection;

import java.sql.*;

public class VerificationDAO {

    public boolean saveCode(VerificationCode vc) {
        String sql = "INSERT INTO VERIFICATION_CODES(code_id, user_id, code, expiry_time, is_used) " +
                     "VALUES(seq_code_id.NEXTVAL, ?, ?, ?, 'N')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, vc.getUserId());
            ps.setString(2, vc.getCode());
            ps.setTimestamp(3, new Timestamp(vc.getExpiryTime().getTime()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public VerificationCode getValidCode(long userId, String code) {
        String sql = "SELECT * FROM VERIFICATION_CODES " +
                     "WHERE user_id = ? AND code = ? AND is_used = 'N'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                VerificationCode vc = new VerificationCode(
                        rs.getLong("code_id"),
                        rs.getLong("user_id"),
                        rs.getString("code"),
                        rs.getTimestamp("expiry_time"),
                        "Y".equals(rs.getString("is_used"))
                );
                return vc;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void markCodeUsed(long codeId) {
        String sql = "UPDATE VERIFICATION_CODES SET is_used = 'Y' WHERE code_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, codeId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
