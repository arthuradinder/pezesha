package com.arthurprojects.pezesha.repository;

import com.arthurprojects.pezesha.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}


