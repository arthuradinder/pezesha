package com.arthurprojects.pezesha.service.impl;

import com.arthurprojects.pezesha.dto.AccountDTO;
import com.arthurprojects.pezesha.dto.AccountResponseDTO;
import com.arthurprojects.pezesha.entity.Account;
import com.arthurprojects.pezesha.exception.ResourceNotFoundException;
import com.arthurprojects.pezesha.mappers.AccountMapper;
import com.arthurprojects.pezesha.repository.AccountRepository;
import com.arthurprojects.pezesha.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountResponseDTO createAccount(AccountDTO accountDTO) {
        // Validate initial balance is not negative
        if (accountDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        Account account = new Account();
        account.setAccountName(accountDTO.getAccountName());
        account.setBalance(accountDTO.getBalance());

        Account savedAccount = accountRepository.save(account);
        return AccountMapper.toAccountResponseDTO(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return AccountMapper.toAccountResponseDTO(account);
    }


    @Override
    public Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account); // Saves the updated account (after balance change)
    }


    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toAccountResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO updateBalance(Long accountId, BigDecimal newBalance) {
        // Validate new balance is not negative
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        account.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.toAccountResponseDTO(updatedAccount);
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
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        return account.getBalance().compareTo(amount) >= 0;
    }
}
