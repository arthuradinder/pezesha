package com.arthurprojects.pezesha.service;

import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.entity.Transfer;

import java.util.List;

public interface TransferService {
    // Create a new transfer
    Transfer createTransfer(TransferDTO transferDTO);

    // Get transfer by ID
    Transfer getTransferById(Long id);

    // Get all transfers
    List<Transfer> getAllTransfers();

    // Get transfers by source account ID
    List<Transfer> getTransfersBySourceAccountId(Long accountId);

    // Get transfers by destination account ID
    List<Transfer> getTransfersByDestinationAccountId(Long accountId);

    // Get all transfers for an account (both sent and received)
    List<Transfer> getAllTransfersByAccountId(Long accountId);
}
