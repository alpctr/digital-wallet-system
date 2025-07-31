package com.alpctr.digitalwalletsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alpctr.digitalwalletsystem.dto.*;
import com.alpctr.digitalwalletsystem.model.Transaction;
import com.alpctr.digitalwalletsystem.model.Wallet;
import com.alpctr.digitalwalletsystem.repository.CustomerRepository;
import com.alpctr.digitalwalletsystem.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private CustomerRepository customerRepository;

    // Employees can create wallets
    @PreAuthorize("hasRole('EMPLOYEE')")
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

    // Employees can view any wallets, customers only their own (TCKN check)
    @PreAuthorize("hasRole('EMPLOYEE') or (hasRole('CUSTOMER') and @customerRepository.findById(#customerId).get().tckn == authentication.name)")
    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> listWallets(@RequestParam Long customerId) {
        return ResponseEntity.ok(walletService.listWallets(customerId));
    }

    // Deposit allowed for both roles
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER')")
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

    // Withdraw allowed for both roles
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER')")
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

    // Both roles can view transactions
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER')")
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> listTransactions(@RequestParam Long walletId) {
        return ResponseEntity.ok(walletService.listTransactions(walletId));
    }

    // Only employees can approve transactions
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/approve")
    public ResponseEntity<Transaction> approveTransaction(@Valid @RequestBody ApproveTransactionRequest request) {
        Transaction transaction = walletService.approveOrDenyTransaction(
                request.getTransactionId(),
                request.getStatus()
        );
        return ResponseEntity.ok(transaction);
    }
}
