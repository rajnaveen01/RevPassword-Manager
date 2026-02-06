package com.revpm.dao;

import com.revpm.model.SecurityQuestion;
import com.revpm.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SecurityQuestionDAO {

    // Add Security Question
    public boolean addQuestion(SecurityQuestion sq) {
        String sql = "INSERT INTO SECURITY_QUESTIONS(question_id, user_id, question, answer) " +
                     "VALUES(seq_question_id.NEXTVAL, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, sq.getUserId());
            ps.setString(2, sq.getQuestion());
            ps.setString(3, sq.getAnswer());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get All Questions of User
    public List<SecurityQuestion> getQuestionsByUser(long userId) {
        List<SecurityQuestion> list = new ArrayList<>();
        String sql = "SELECT * FROM SECURITY_QUESTIONS WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SecurityQuestion sq = new SecurityQuestion(
                        rs.getLong("question_id"),
                        rs.getLong("user_id"),
                        rs.getString("question"),
                        rs.getString("answer")
                );
                list.add(sq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean deleteQuestion(long questionId) {
        String sql = "DELETE FROM SECURITY_QUESTIONS WHERE QUESTION_ID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, questionId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
