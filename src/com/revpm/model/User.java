package com.revpm.model;

import java.util.Date;

public class User {

    private long userId;
    private String username;
    private String email;
    private String masterPassword;
    private int failedAttempts;
    private boolean accountLocked;
    private Date createdAt;
    private Date updatedAt;
    private Date lastLogin;

    public User() {}

    public User(String username, String email, String masterPassword) {
        this.username = username;
        this.email = email;
        this.masterPassword = masterPassword;
    }

    public User(long userId, String username, String email, String masterPassword,
                int failedAttempts, boolean accountLocked,
                Date createdAt, Date updatedAt, Date lastLogin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.masterPassword = masterPassword;
        this.failedAttempts = failedAttempts;
        this.accountLocked = accountLocked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", failedAttempts=" + failedAttempts +
                ", accountLocked=" + accountLocked +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
