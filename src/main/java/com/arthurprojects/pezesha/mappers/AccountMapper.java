package com.arthurprojects.pezesha.mappers;

import com.arthurprojects.pezesha.dto.AccountResponseDTO;
import com.arthurprojects.pezesha.entity.Account;

public class AccountMapper {

    /**
     * Converts an Account entity to an AccountResponseDTO.
     *
     * @param account the Account entity to be converted
     * @return the corresponding AccountResponseDTO
     */
    public static AccountResponseDTO toAccountResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountName(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
