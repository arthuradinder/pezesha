package com.arthurprojects.pezesha.service.impl;

import com.arthurprojects.pezesha.dto.AccountDTO;
import com.arthurprojects.pezesha.entity.Account;
import com.arthurprojects.pezesha.exception.ResourceNotFoundException;
import com.arthurprojects.pezesha.repository.AccountRepository;
import com.arthurprojects.pezesha.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(AccountDTO accountDTO) {
        // Validate initial balance is not negative
        if (accountDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        Account account = new Account();
        account.setAccountName(accountDTO.getAccountName());
        account.setBalance(accountDTO.getBalance());

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateBalance(Long accountId, BigDecimal newBalance) {
        // Validate new balance is not negative
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        Account account = getAccountById(accountId);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean accountExists(Long id) {
        return accountRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasSufficientBalance(Long accountId, BigDecimal amount) {
        Account account = getAccountById(accountId);
        return account.getBalance().compareTo(amount) >= 0;
    }
}
