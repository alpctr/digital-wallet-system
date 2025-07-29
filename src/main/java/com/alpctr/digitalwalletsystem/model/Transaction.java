package com.alpctr.digitalwalletsystem.model;

import java.math.BigDecimal;

import com.alpctr.enums.OppositePartyType;
import com.alpctr.enums.TransactionStatus;
import com.alpctr.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

	public Transaction() {
		// TODO Auto-generated constructor stub
	}
	
    @Id 
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private OppositePartyType oppositeType;

    private String oppositeParty;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public OppositePartyType getOppositeType() {
		return oppositeType;
	}

	public void setOppositeType(OppositePartyType oppositeType) {
		this.oppositeType = oppositeType;
	}

	public String getOppositeParty() {
		return oppositeParty;
	}

	public void setOppositeParty(String oppositeParty) {
		this.oppositeParty = oppositeParty;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

}
