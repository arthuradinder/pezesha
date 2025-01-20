package com.arthurprojects.pezesha.repository;

import com.arthurprojects.pezesha.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findBySourceAccount_Id(Long sourceAccountId);
    List<Transfer> findByDestinationAccount_Id(Long destinationAccountId);
    List<Transfer> findBySourceAccount_IdOrDestinationAccount_Id(Long sourceAccountId, Long destinationAccountId);
}

