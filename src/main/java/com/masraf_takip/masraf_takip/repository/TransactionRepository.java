package com.masraf_takip.masraf_takip.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masraf_takip.masraf_takip.model.Transaction;;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByTransactionDateBetweenAndUserId(Date startDate, Date endDate, String userId);
}