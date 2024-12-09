package com.Onedev.transaksiQ.repository;

import com.Onedev.transaksiQ.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    Optional<UserBalance> findByUserEmail(String email);


    }
