package com.sahyog.auth_service.entity;

public enum BlacklistReason {

    USER_LOGOUT("User initiated logout"),
    PASSWORD_CHANGED("Password was changed"),
    ACCOUNT_DISABLED("Account was disabled"),
    ACCOUNT_DELETED("Account was deleted"),
    SECURITY_BREACH("Security breach detected"),
    ADMIN_ACTION("Administrative action"),
    TOKEN_COMPROMISED("Token security compromised");

    private final String description;

    BlacklistReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
