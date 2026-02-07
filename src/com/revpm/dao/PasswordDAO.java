package com.revpm.dao;

import com.revpm.model.PasswordEntry;
import com.revpm.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordDAO {

    public boolean addPassword(PasswordEntry entry) {
    	
        String checkSql = "SELECT entry_id FROM PASSWORD_ENTRIES WHERE user_id = ? AND account_name = ? AND is_deleted = 'Y'";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psCheck = con.prepareStatement(checkSql)) {
             
            psCheck.setLong(1, entry.getUserId());
            psCheck.setString(2, entry.getAccountName());
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                long existingId = rs.getLong("entry_id");
                return reactivatePassword(existingId, entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String insertSql = "INSERT INTO PASSWORD_ENTRIES(entry_id, user_id, account_name, account_username, account_password) " +
                     "VALUES(seq_entry_id.NEXTVAL, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertSql)) {

            ps.setLong(1, entry.getUserId());
            ps.setString(2, entry.getAccountName());
            ps.setString(3, entry.getAccountUsername());
            ps.setString(4, entry.getAccountPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1) { // Unique Constraint Violation
                 System.out.println("Error: Account name already exists!");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean reactivatePassword(long entryId, PasswordEntry newData) {
        String sql = "UPDATE PASSWORD_ENTRIES SET account_username = ?, account_password = ?, is_deleted = 'N', updated_at = SYSDATE " +
                     "WHERE entry_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, newData.getAccountUsername());
            ps.setString(2, newData.getAccountPassword());
            ps.setLong(3, entryId);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAccountNameExists(long userId, String accountName) {
        String sql = "SELECT COUNT(*) FROM PASSWORD_ENTRIES WHERE USER_ID = ? AND ACCOUNT_NAME = ?"; 
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, accountName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PasswordEntry> getAllPasswords(long userId) {
        List<PasswordEntry> list = new ArrayList<>();
        String sql = "SELECT * FROM PASSWORD_ENTRIES WHERE user_id = ? AND is_deleted = 'N'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapEntry(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public PasswordEntry findByAccountName(long userId, String accountName) {
        String sql = "SELECT * FROM PASSWORD_ENTRIES WHERE user_id = ? AND account_name = ? AND is_deleted = 'N'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setString(2, accountName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapEntry(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(long entryId, String newPassword) {
        String sql = "UPDATE PASSWORD_ENTRIES SET account_password = ?, updated_at = SYSDATE WHERE entry_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setLong(2, entryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePassword(long entryId) {
        String sql = "UPDATE PASSWORD_ENTRIES SET is_deleted = 'Y' WHERE entry_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, entryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PasswordEntry> searchPasswords(long userId, String keyword) {
        List<PasswordEntry> list = new ArrayList<>();
        String sql = "SELECT * FROM PASSWORD_ENTRIES WHERE USER_ID=? AND ACCOUNT_NAME LIKE ? AND is_deleted = 'N'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapEntry(rs)); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private PasswordEntry mapEntry(ResultSet rs) throws SQLException {
        PasswordEntry entry = new PasswordEntry();
        entry.setEntryId(rs.getLong("entry_id"));
        entry.setUserId(rs.getLong("user_id"));
        entry.setAccountName(rs.getString("account_name"));
        entry.setAccountUsername(rs.getString("account_username")); 
        entry.setAccountPassword(rs.getString("account_password"));
        entry.setCreatedAt(rs.getDate("created_at"));
        return entry;
    }
}