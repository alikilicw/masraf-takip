package com.masraf_takip.masraf_takip.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masraf_takip.masraf_takip.model.AggregatedTransaction;
import com.masraf_takip.masraf_takip.service.AggregatedTransactionService;
import com.masraf_takip.masraf_takip.util.ApiError;

@RestController
@RequestMapping("/api/aggregated-transactions")
public class AggregatedTransactionController {
    
    private AggregatedTransactionService aggregatedTransactionService;

    public AggregatedTransactionController(AggregatedTransactionService aggregatedTransactionService) {
        this.aggregatedTransactionService = aggregatedTransactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAggregatedTransactions() {
        try {
            return ResponseEntity.ok(aggregatedTransactionService.findAll());
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAggregatedTransactionById(@PathVariable String id) {
        try {
            AggregatedTransaction aggregatedTransaction = aggregatedTransactionService.findById(id);
            if(aggregatedTransaction == null) throw new ApiError(HttpStatus.NOT_FOUND, "Transaction not found.");
            return ResponseEntity.ok(aggregatedTransaction);
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @PostMapping
    public ResponseEntity<?> addAggregatedTransaction(@RequestBody AggregatedTransaction aggregatedTransaction) {
        try {
            if(aggregatedTransaction.getUserId() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send a userId.");
            if(aggregatedTransaction.getType() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send a type.");
            if(aggregatedTransaction.getSumOfTransactions() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send sum of transactions.");

            // if(!(Arrays.asList("DAILY", "WEEKLY", "ANNUALY").contains(aggregatedTransaction.getType().toString())))  throw new ApiError(HttpStatus.BAD_REQUEST, "Please send a valid type.");

            return ResponseEntity.ok(aggregatedTransactionService.add(aggregatedTransaction));
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAggregatedTransactionById(@PathVariable String id) {
        try {
            AggregatedTransaction aggregatedTransaction = aggregatedTransactionService.deleteById(id);
            if(aggregatedTransaction == null) throw new ApiError(HttpStatus.NOT_FOUND, "Transaction not found.");
            return ResponseEntity.ok().build();
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

}