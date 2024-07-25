package com.masraf_takip.masraf_takip.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masraf_takip.masraf_takip.model.Transaction;
import com.masraf_takip.masraf_takip.service.TransactionService;
import com.masraf_takip.masraf_takip.util.ApiError;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionService.findAll());
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        try {
            Transaction transaction = transactionService.findById(id);
            if(transaction == null) throw new ApiError(HttpStatus.NOT_FOUND, "Transaction not found.");
            return ResponseEntity.ok(transaction);
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        try {
            if(transaction.getAmount() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send an amount");
            if(transaction.getUserId() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send a userId");
            return ResponseEntity.ok(transactionService.add(transaction));
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable String id, @RequestBody Transaction transactionDetails) {
        try {
            Transaction transaction = transactionService.update(id, transactionDetails);
            if(transaction == null) throw new ApiError(HttpStatus.NOT_FOUND, "Transaction not found.");
            return ResponseEntity.ok(transaction);
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable String id) {
        try {
            Transaction transaction = transactionService.deleteById(id);
            if(transaction == null) throw new ApiError(HttpStatus.NOT_FOUND, "Transaction not found.");
            return ResponseEntity.ok().build();
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @GetMapping("/total-of/{id}")
    public ResponseEntity<?> getTotalAmountOfTransactionsByUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok(transactionService.findTotalAmountByUser(id));
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }


}
