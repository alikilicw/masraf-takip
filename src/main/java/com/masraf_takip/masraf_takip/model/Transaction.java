package com.masraf_takip.masraf_takip.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Transaction {
    
    @Id
    private String id;
    private Double amount;
    private Date transactionDate;
    private String userId;

    @PrePersist
        protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.transactionDate == null) {
            this.transactionDate = new Date();
        }
    }
    
    public String getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    

}
