package com.masraf_takip.masraf_takip.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class AggregatedTransaction {
    
    @Id
    private String id;
    private String userId;
    private Double sumOfTransactions;

    @Enumerated(EnumType.STRING)
    private RecordType type;
    
    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public Double getSumOfTransactions() {
        return sumOfTransactions;
    }

    public void setSumOfTransactions(Double sumOfTransactions) {
        this.sumOfTransactions = sumOfTransactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    
}