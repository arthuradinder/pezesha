package com.arthurprojects.pezesha.service;

import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.dto.TransferResponseDTO;

import java.util.List;

public interface TransferService {

    // Create a new transfer
    TransferResponseDTO createTransfer(TransferDTO transferDTO);

    // Get transfer by ID
    TransferResponseDTO getTransferById(Long id);

    // Get all transfers
    List<TransferResponseDTO> getAllTransfers();

    // Get transfers by source account ID
    List<TransferResponseDTO> getTransfersBySourceAccountId(Long accountId);

    // Get transfers by destination account ID
    List<TransferResponseDTO> getTransfersByDestinationAccountId(Long accountId);

    // Get all transfers for an account (both sent and received)
    List<TransferResponseDTO> getAllTransfersByAccountId(Long accountId);
}
