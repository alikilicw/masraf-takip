package com.masraf_takip.masraf_takip.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.masraf_takip.masraf_takip.model.Transaction;
import com.masraf_takip.masraf_takip.model.User;
import com.masraf_takip.masraf_takip.repository.TransactionRepository;
import com.masraf_takip.masraf_takip.util.ApiError;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private UserService userService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(String id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return (transaction.isPresent()) ? transaction.get() : null;
    }

    public Transaction add(Transaction transaction) {

        User userCheck = userService.findById(transaction.getUserId());
        if(userCheck == null) {
            throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
        }

        return transactionRepository.save(transaction);
    }

    public Transaction update(String id, Transaction transactionDetails) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()) {
            Transaction updateTransaction = transaction.get();
            if(transactionDetails.getAmount() != null) updateTransaction.setAmount(transactionDetails.getAmount());
            if(transactionDetails.getUserId() != null) {
                User userCheck = userService.findById(transactionDetails.getUserId());
                if(userCheck == null) {
                    throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
                }
                updateTransaction.setUserId(transactionDetails.getUserId());
            }
            return updateTransaction;
        } else {
            return null;
        }
    }

    public Transaction deleteById(String id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()) {
            transactionRepository.deleteById(id);
            return transaction.get();
        } else {
            return null;
        }
    }

    public Double findTotalAmountByUser(String userId) {
        User userCheck = userService.findById(userId);
        if(userCheck == null) {
            throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
        }

        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        if(transactions.isEmpty()) throw new ApiError(HttpStatus.NOT_FOUND, "User has no transactions.");

        return transactions.stream().mapToDouble(Transaction::getAmount).sum();

    }

    public List<Transaction> findByDateAndUser(String userId, Date startDate, Date enDate) {
        return transactionRepository.findByTransactionDateBetweenAndUserId(startDate, enDate, userId);
    }
            
}
