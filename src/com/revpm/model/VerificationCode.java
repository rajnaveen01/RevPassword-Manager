package com.revpm.model;

import java.util.Date;

public class VerificationCode {

    private long codeId;
    private long userId;
    private String code;
    private Date expiryTime;
    private boolean used;

    public VerificationCode() {}

    public VerificationCode(long userId, String code, Date expiryTime) {
        this.userId = userId;
        this.code = code;
        this.expiryTime = expiryTime;
        this.used = false;
    }

    public VerificationCode(long codeId, long userId, String code, Date expiryTime, boolean used) {
        this.codeId = codeId;
        this.userId = userId;
        this.code = code;
        this.expiryTime = expiryTime;
        this.used = used;
    }

    public long getCodeId() { return codeId; }
    public void setCodeId(long codeId) { this.codeId = codeId; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Date getExpiryTime() { return expiryTime; }
    public void setExpiryTime(Date expiryTime) { this.expiryTime = expiryTime; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }

    public boolean isExpired() {
        return new Date().after(expiryTime);
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "codeId=" + codeId +
                ", userId=" + userId +
                ", code='[PROTECTED]'" + // FIXED: Do not print actual code
                ", expiryTime=" + expiryTime +
                ", used=" + used +
                '}';
    }
}