package com.arthurprojects.pezesha.service.impl;

import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.entity.Account;
import com.arthurprojects.pezesha.entity.Transfer;
import com.arthurprojects.pezesha.entity.TransferStatus;
import com.arthurprojects.pezesha.exception.InsufficientBalanceException;
import com.arthurprojects.pezesha.exception.ResourceNotFoundException;
import com.arthurprojects.pezesha.repository.TransferRepository;
import com.arthurprojects.pezesha.service.AccountService;
import com.arthurprojects.pezesha.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final AccountService accountService;

    @Override
    public Transfer createTransfer(TransferDTO transferDTO) {
        // Validate transfer amount
        if (transferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }

        // Validate that source and destination accounts are different
        if (transferDTO.getSourceAccountId().equals(transferDTO.getDestinationAccountId())) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same");
        }

        // Get source and destination accounts
        Account sourceAccount = accountService.getAccountById(transferDTO.getSourceAccountId());
        Account destinationAccount = accountService.getAccountById(transferDTO.getDestinationAccountId());

        // Check if source account has sufficient balance
        if (!accountService.hasSufficientBalance(sourceAccount.getId(), transferDTO.getAmount())) {
            throw new InsufficientBalanceException("Insufficient balance in source account");
        }

        // Perform the transfer
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.getAmount()));

        // Create transfer record
        Transfer transfer = new Transfer();
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setAmount(transferDTO.getAmount());
        transfer.setTransferStatus(TransferStatus.COMPLETED);
        transfer.setDescription(transferDTO.getDescription());

        return transferRepository.save(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transfer> getTransfersBySourceAccountId(Long accountId) {
        return transferRepository.findBySourceAccount_Id(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transfer> getTransfersByDestinationAccountId(Long accountId) {
        return transferRepository.findByDestinationAccount_Id(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transfer> getAllTransfersByAccountId(Long accountId) {
        return transferRepository.findBySourceAccount_IdOrDestinationAccount_Id(accountId, accountId);
    }
}
