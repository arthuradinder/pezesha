package com.arthurprojects.pezesha.mappers;

import com.arthurprojects.pezesha.dto.TransferResponseDTO;
import com.arthurprojects.pezesha.entity.Transfer;

public class TransferMapper {

    /**
     * Converts a Transfer entity to a TransferResponseDTO.
     *
     * @param transfer the Transfer entity to be converted
     * @return the corresponding TransferResponseDTO
     */
    public static TransferResponseDTO toTransferResponseDTO(Transfer transfer) {
        return new TransferResponseDTO(
                transfer.getId(),
                transfer.getSourceAccount().getId(),
                transfer.getDestinationAccount().getId(),
                transfer.getAmount(),
                transfer.getTimestamp(),
                transfer.getDescription(),
                transfer.getTransferStatus().name()  // Enum to String conversion
        );
    }
}
