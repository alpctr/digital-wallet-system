package com.alpctr.digitalwalletsystem.dto;

import java.math.BigDecimal;
import com.alpctr.enums.OppositePartyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class WithdrawRequest {

    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Destination type is required")
    private OppositePartyType destinationType;

    @NotBlank(message = "Destination cannot be blank")
    private String destination;


    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OppositePartyType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(OppositePartyType destinationType) {
        this.destinationType = destinationType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
