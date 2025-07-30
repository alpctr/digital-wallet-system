package com.alpctr.digitalwalletsystem.dto;

import java.math.BigDecimal;
import com.alpctr.enums.OppositePartyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionRequest {

    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Source type is required")
    private OppositePartyType sourceType;

    @NotBlank(message = "Source cannot be blank")
    private String source;



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

    public OppositePartyType getSourceType() {
        return sourceType;
    }

    public void setSourceType(OppositePartyType sourceType) {
        this.sourceType = sourceType;
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
