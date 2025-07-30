package com.alpctr.digitalwalletsystem.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpctr.digitalwalletsystem.dto.WalletRequest;
import com.alpctr.digitalwalletsystem.model.Transaction;
import com.alpctr.digitalwalletsystem.model.Wallet;
import com.alpctr.digitalwalletsystem.service.WalletService;
import com.alpctr.enums.OppositePartyType;
import com.alpctr.enums.TransactionStatus;

@RestController
@RequestMapping("/api")
public class WalletController {

    @Autowired
    private WalletService walletService;
/*
    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(
            @RequestParam Long customerId,
            @RequestParam String walletName,
            @RequestParam Currency currency,
            @RequestParam boolean activeForShopping,
            @RequestParam boolean activeForWithdraw
    ) {
        Wallet wallet = walletService.createWallet(customerId, walletName, currency, activeForShopping, activeForWithdraw);
        return ResponseEntity.ok(wallet);
    }
*/   
    
    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletRequest request) {
        Wallet wallet = walletService.createWallet(
            request.getCustomerId(),
            request.getWalletName(),
            request.getCurrency(),
            request.isActiveForShopping(),
            request.isActiveForWithdraw()
        );
        return ResponseEntity.ok(wallet);
    }
    

    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> listWallets(@RequestParam Long customerId) {
        return ResponseEntity.ok(walletService.listWallets(customerId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @RequestParam Long walletId,
            @RequestParam BigDecimal amount,
            @RequestParam OppositePartyType sourceType,
            @RequestParam String source
    ) {
        return ResponseEntity.ok(walletService.deposit(walletId, amount, sourceType, source));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @RequestParam Long walletId,
            @RequestParam BigDecimal amount,
            @RequestParam OppositePartyType destinationType,
            @RequestParam String destination
    ) {
        return ResponseEntity.ok(walletService.withdraw(walletId, amount, destinationType, destination));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> listTransactions(@RequestParam Long walletId) {
        return ResponseEntity.ok(walletService.listTransactions(walletId));
    }

    @PostMapping("/approve")
    public ResponseEntity<Transaction> approveTransaction(
            @RequestParam Long transactionId,
            @RequestParam TransactionStatus status
    ) {
        return ResponseEntity.ok(walletService.approveOrDenyTransaction(transactionId, status));
    }
}


