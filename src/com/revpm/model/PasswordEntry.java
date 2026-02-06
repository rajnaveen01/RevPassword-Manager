package com.revpm.model;

import java.util.Date;

public class PasswordEntry {

    private long entryId;
    private long userId;
    private String accountName;
    private String accountUsername;
    private String accountPassword;
    private boolean deleted;
    private Date createdAt;
    private Date updatedAt;

    // Default Constructor
    public PasswordEntry() {}

    // Constructor for Adding New Password
    public PasswordEntry(long userId, String accountName, String accountUsername, String accountPassword) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }

    // Full Constructor
    public PasswordEntry(long entryId, long userId, String accountName, String accountUsername,
                         String accountPassword, boolean deleted, Date createdAt, Date updatedAt) {
        this.entryId = entryId;
        this.userId = userId;
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PasswordEntry{" +
                "entryId=" + entryId +
                ", userId=" + userId +
                ", accountName='" + accountName + '\'' +
                ", accountUsername='" + accountUsername + '\'' +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
