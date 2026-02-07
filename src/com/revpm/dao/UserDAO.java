package com.revpm.dao;

import com.revpm.model.User;
import com.revpm.util.DBConnection;
import java.sql.*;
import java.util.Optional;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO USERS(user_id, username, email, master_password) VALUES(seq_user_id.NEXTVAL, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getMasterPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapUser(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findById(long userId) {
        String sql = "SELECT * FROM USERS WHERE USER_ID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapUser(rs)); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean updateProfile(long userId, String username, String email) {
        String sql = "UPDATE USERS SET username = ?, email = ?, updated_at = SYSDATE WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setLong(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMasterPassword(long userId, String newPassword) {
        String sql = "UPDATE USERS SET master_password = ?, updated_at = SYSDATE WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setLong(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username")); 
        user.setEmail(rs.getString("email"));
        user.setMasterPassword(rs.getString("master_password"));
        user.setCreatedAt(rs.getDate("created_at"));
        return user;
    }
}