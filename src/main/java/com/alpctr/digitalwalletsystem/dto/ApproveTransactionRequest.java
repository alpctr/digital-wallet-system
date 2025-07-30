package com.alpctr.digitalwalletsystem.dto;


import com.alpctr.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public class ApproveTransactionRequest {

    @NotNull(message = "Transaction ID is required")
    private Long transactionId;

    @NotNull(message = "Status is required")
    private TransactionStatus status;

    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
