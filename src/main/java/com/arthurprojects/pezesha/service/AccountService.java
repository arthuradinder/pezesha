package com.arthurprojects.pezesha.service;


import com.arthurprojects.pezesha.dto.AccountDTO;
import com.arthurprojects.pezesha.dto.AccountResponseDTO;
import com.arthurprojects.pezesha.entity.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing account operations.
 */
public interface AccountService {

    /**
     * Creates a new account.
     *
     * @param accountDTO the account data transfer object containing details for the new account.
     * @return the created AccountResponseDTO.
     */
    AccountResponseDTO createAccount(AccountDTO accountDTO);

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account to retrieve.
     * @return the AccountResponseDTO.
     */
    AccountResponseDTO getAccountById(Long id);

    Account getAccountEntityById(Long id);

    void saveAccount(Account account);

    /**
     * Retrieves all accounts.
     *
     * @return a list of all AccountResponseDTOs.
     */
    List<AccountResponseDTO> getAllAccounts();

    /**
     * Updates the balance of an account.
     *
     * @param accountId  the ID of the account to update.
     * @param newBalance the new balance to set.
     * @return the updated AccountResponseDTO.
     */
    AccountResponseDTO updateBalance(Long accountId, BigDecimal newBalance);

    /**
     * Deletes an account by its ID.
     *
     * @param id the ID of the account to delete.
     */
    void deleteAccount(Long id);

    /**
     * Checks if an account exists by its ID.
     *
     * @param id the ID of the account to check.
     * @return true if the account exists, false otherwise.
     */
    boolean accountExists(Long id);

    /**
     * Checks if an account has sufficient balance.
     *
     * @param accountId the ID of the account to check.
     * @param amount    the amount to compare against the account balance.
     * @return true if the account has sufficient balance, false otherwise.
     */
    boolean hasSufficientBalance(Long accountId, BigDecimal amount);
}
