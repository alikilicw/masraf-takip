package com.masraf_takip.masraf_takip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.masraf_takip.masraf_takip.model.AggregatedTransaction;
import com.masraf_takip.masraf_takip.model.Transaction;
import com.masraf_takip.masraf_takip.model.User;
import com.masraf_takip.masraf_takip.repository.AggregatedTransactionRepository;
import com.masraf_takip.masraf_takip.util.ApiError;

import java.util.List;
import java.util.Optional;

@Service
public class AggregatedTransactionService {

    private AggregatedTransactionRepository aggregatedTransactionRepository;
    private UserService userService;

    @Autowired
    public AggregatedTransactionService(AggregatedTransactionRepository aggregatedTransactionRepository, UserService userService) {
        this.aggregatedTransactionRepository = aggregatedTransactionRepository;
        this.userService = userService;
    }
    
    public List<AggregatedTransaction> findAll() {
        return aggregatedTransactionRepository.findAll();
    }

    public AggregatedTransaction findById(String id) {
        Optional<AggregatedTransaction> agrregatedTransaction = aggregatedTransactionRepository.findById(id);
        if(agrregatedTransaction.isPresent()) {
            return agrregatedTransaction.get();
        } else {
            return null;
        }
    }

    public AggregatedTransaction add(AggregatedTransaction transaction) {

        User userCheck = userService.findById(transaction.getUserId());
        if(userCheck == null) {
            throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
        }

        return aggregatedTransactionRepository.save(transaction);
    }

    public AggregatedTransaction deleteById(String id) {
        Optional<AggregatedTransaction> aggregatedTransaction = aggregatedTransactionRepository.findById(id);
        if(aggregatedTransaction.isPresent()) {
            aggregatedTransactionRepository.deleteById(id);
            return aggregatedTransaction.get();
        } else {
            return null;
        }
    }
}
