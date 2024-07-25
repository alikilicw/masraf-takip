package com.masraf_takip.masraf_takip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masraf_takip.masraf_takip.model.AggregatedTransaction;

@Repository
public interface AggregatedTransactionRepository extends JpaRepository<AggregatedTransaction, String> {
    
}