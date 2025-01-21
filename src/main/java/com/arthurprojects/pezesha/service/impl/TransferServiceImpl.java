package com.arthurprojects.pezesha.service.impl;

import com.arthurprojects.pezesha.dto.AccountResponseDTO;
import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.dto.TransferResponseDTO;
import com.arthurprojects.pezesha.entity.Account;
import com.arthurprojects.pezesha.entity.Transfer;
import com.arthurprojects.pezesha.entity.TransferStatus;
import com.arthurprojects.pezesha.exception.InsufficientBalanceException;
import com.arthurprojects.pezesha.exception.ResourceNotFoundException;
import com.arthurprojects.pezesha.mappers.TransferMapper;
import com.arthurprojects.pezesha.repository.AccountRepository;
import com.arthurprojects.pezesha.repository.TransferRepository;
import com.arthurprojects.pezesha.service.AccountService;
import com.arthurprojects.pezesha.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public TransferResponseDTO createTransfer(TransferDTO transferDTO) {
        // Validate transfer amount
        if (transferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }

        // Validate that source and destination accounts are different
        if (transferDTO.getSourceAccountId().equals(transferDTO.getDestinationAccountId())) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same");
        }

        // Get source and destination accounts from accountService (which handles account retrieval and balance checks)
        AccountResponseDTO sourceAccountDTO = accountService.getAccountById(transferDTO.getSourceAccountId());
        AccountResponseDTO destinationAccountDTO = accountService.getAccountById(transferDTO.getDestinationAccountId());

        // Check if source account has sufficient balance using accountService's balance check
        if (!accountService.hasSufficientBalance(sourceAccountDTO.id(), transferDTO.getAmount())) {
            throw new InsufficientBalanceException("Insufficient balance in source account");
        }

        // Retrieve the actual Account entities from accountService (no need for accountRepository here)
        Account sourceAccount = accountService.getAccountEntityById(sourceAccountDTO.id());
        Account destinationAccount = accountService.getAccountEntityById(destinationAccountDTO.id());

        // Update balances
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferDTO.getAmount()));

        // Create and populate transfer record
        Transfer transfer = new Transfer();
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setAmount(transferDTO.getAmount());
        transfer.setTransferStatus(TransferStatus.COMPLETED); // Or consider PENDING if using a process flow
        transfer.setDescription(transferDTO.getDescription());
        transfer.setTimestamp(LocalDateTime.now());

        // Save transfer and update accounts (all in one transaction due to @Transactional)
        transferRepository.save(transfer); // Saving the transfer will be transactional
        accountService.saveAccount(sourceAccount);  // Account balance updated
        accountService.saveAccount(destinationAccount); // Account balance updated

        // Return the transfer response mapped from the saved transfer
        return TransferMapper.toTransferResponseDTO(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public TransferResponseDTO getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found with id: " + id));
        return TransferMapper.toTransferResponseDTO(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDTO> getAllTransfers() {
        return transferRepository.findAll()
                .stream()
                .map(TransferMapper::toTransferResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDTO> getTransfersBySourceAccountId(Long accountId) {
        return transferRepository.findBySourceAccount_Id(accountId)
                .stream()
                .map(TransferMapper::toTransferResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDTO> getTransfersByDestinationAccountId(Long accountId) {
        return transferRepository.findByDestinationAccount_Id(accountId)
                .stream()
                .map(TransferMapper::toTransferResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDTO> getAllTransfersByAccountId(Long accountId) {
        return transferRepository.findBySourceAccount_IdOrDestinationAccount_Id(accountId, accountId)
                .stream()
                .map(TransferMapper::toTransferResponseDTO)
                .collect(Collectors.toList());
    }
}
