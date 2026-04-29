package com.erric.demo.dto;

import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private String type;
    private Long amount;
    private String status;
    private String referenceId;
    private LocalDateTime createdAt;

    public TransactionResponse(Long id, String type, Long amount,
                               String status, String referenceId,
                               LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.referenceId = referenceId;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public Long getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getReferenceId() { return referenceId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}