package com.arthurprojects.pezesha.controller;

import com.arthurprojects.pezesha.dto.AccountDTO;
import com.arthurprojects.pezesha.entity.Account;
import com.arthurprojects.pezesha.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing accounts.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Creates a new account.
     *
     * @param accountDTO the account data transfer object containing account details.
     * @return ResponseEntity containing the created Account and HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        Account createdAccount = accountService.createAccount(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account to retrieve.
     * @return ResponseEntity containing the retrieved Account and HTTP status 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }
}