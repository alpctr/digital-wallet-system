package com.alpctr.digitalwalletsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpctr.digitalwalletsystem.dto.ApproveTransactionRequest;
import com.alpctr.digitalwalletsystem.dto.TransactionRequest;
import com.alpctr.digitalwalletsystem.dto.WalletRequest;
import com.alpctr.digitalwalletsystem.dto.WithdrawRequest;
import com.alpctr.digitalwalletsystem.model.Transaction;
import com.alpctr.digitalwalletsystem.model.Wallet;
import com.alpctr.digitalwalletsystem.service.WalletService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class WalletController {

    @Autowired
    private WalletService walletService;

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
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = walletService.deposit(
                request.getWalletId(),
                request.getAmount(),
                request.getSourceType(),
                request.getSource()
        );
        return ResponseEntity.ok(transaction);
    }


    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid @RequestBody WithdrawRequest request) {
        Transaction transaction = walletService.withdraw(
                request.getWalletId(),
                request.getAmount(),
                request.getDestinationType(),
                request.getDestination()
        );
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> listTransactions(@RequestParam Long walletId) {
        return ResponseEntity.ok(walletService.listTransactions(walletId));
    }

    
    @PostMapping("/approve")
    public ResponseEntity<Transaction> approveTransaction(@Valid @RequestBody ApproveTransactionRequest request) {
        Transaction transaction = walletService.approveOrDenyTransaction(
                request.getTransactionId(),
                request.getStatus()
        );
        return ResponseEntity.ok(transaction);
    }
    
    }


