package com.arthurprojects.pezesha.service;


import com.arthurprojects.pezesha.dto.AccountDTO;
import com.arthurprojects.pezesha.entity.Account;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    // Create a new account
    Account createAccount(AccountDTO accountDTO);

    // Get account by ID
    Account getAccountById(Long id);

    // Get all accounts
    List<Account> getAllAccounts();

    // Update account balance
    Account updateBalance(Long accountId, BigDecimal newBalance);

    // Delete account
    void deleteAccount(Long id);

    // Check if account exists
    boolean accountExists(Long id);

    // Check if account has sufficient balance
    boolean hasSufficientBalance(Long accountId, BigDecimal amount);
}
