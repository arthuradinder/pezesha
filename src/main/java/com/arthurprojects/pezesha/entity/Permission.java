package com.arthurprojects.pezesha.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    // Admin Permissions
    ADMIN_READ("admin:read", "Permission to view admin-level data and settings."),
    ADMIN_UPDATE("admin:update", "Permission to update admin-level data and settings."),
    ADMIN_CREATE("admin:create", "Permission to create new admin-level data or settings."),
    ADMIN_DELETE("admin:delete", "Permission to delete admin-level data or settings."),
    ADMIN_ACCOUNT_USER_CREATE("admin:account_user:create", "Permission for admins to create account users."),

    // Account Permissions
    ACCOUNT_CREATE("account:create", "Permission to create new accounts."),
    ACCOUNT_UPDATE("account:update", "Permission to update existing accounts, such as extending validity or changing treatment details."),
    ACCOUNT_DELETE("account:delete", "Permission to delete accounts."),
    ACCOUNT_VIEW("account:view", "Permission to view the details of accounts."),
    ACCOUNT_USE("account:use", "Permission to redeem or apply an account for patient treatments or services."),

    // Transaction Permissions
    TRANSACTION_CREATE("transaction:create", "Permission to initiate a new transaction for account activities."),
    TRANSACTION_UPDATE("transaction:update", "Permission to update details of a transaction."),
    TRANSACTION_VIEW("transaction:view", "Permission to view details of transactions."),
    TRANSACTION_TRANSFER("transaction:transfer", "Permission to transfer money between accounts."),

    // User Permissions
    USER_CREATE("user:create", "Permission to create new users in the system (e.g., doctors, nurses, admin)."),
    USER_UPDATE("user:update", "Permission to update user details or roles."),
    USER_DELETE("user:delete", "Permission to delete users from the system."),
    USER_VIEW("user:view", "Permission to view user details.");

    @Getter
    private final String permission;
    @Getter
    private final String description;
}
