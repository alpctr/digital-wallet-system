package com.alpctr.digitalwalletsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alpctr.digitalwalletsystem.model.Customer;
import com.alpctr.digitalwalletsystem.model.Transaction;
import com.alpctr.digitalwalletsystem.model.Wallet;
import com.alpctr.digitalwalletsystem.repository.CustomerRepository;
import com.alpctr.digitalwalletsystem.repository.TransactionRepository;
import com.alpctr.digitalwalletsystem.repository.WalletRepository;
import com.alpctr.enums.Currency;
import com.alpctr.enums.OppositePartyType;
import com.alpctr.enums.TransactionStatus;
import com.alpctr.enums.TransactionType;

@Service
public class WalletService {

	public WalletService() {
		// TODO Auto-generated constructor stub
	}
	
    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    public Wallet createWallet(Long customerId, String name, Currency currency,
                                boolean forShopping, boolean forWithdraw) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Wallet wallet = new Wallet();
        wallet.setCustomer(customer);
        wallet.setWalletName(name);
        wallet.setCurrency(currency);
        wallet.setActiveForShopping(forShopping);
        wallet.setActiveForWithdraw(forWithdraw);
        return walletRepo.save(wallet);
    }

    public List<Wallet> listWallets(Long customerId) {
        return walletRepo.findByCustomerId(customerId);
    }

    @Transactional
    public Transaction deposit(Long walletId, BigDecimal amount, OppositePartyType type, String source) {
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Transaction txn = new Transaction();
        txn.setWallet(wallet);
        txn.setAmount(amount);
        txn.setType(TransactionType.DEPOSIT);
        txn.setOppositeType(type);
        txn.setOppositeParty(source);

        if (amount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            txn.setStatus(TransactionStatus.PENDING);
            wallet.setBalance(wallet.getBalance().add(amount));
        } else {
            txn.setStatus(TransactionStatus.APPROVED);
            wallet.setBalance(wallet.getBalance().add(amount));
            wallet.setUsableBalance(wallet.getUsableBalance().add(amount));
        }

        walletRepo.save(wallet);
        return transactionRepo.save(txn);
    }

    @Transactional
    public Transaction withdraw(Long walletId, BigDecimal amount, OppositePartyType type, String dest) {
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (!wallet.isActiveForWithdraw()) {
            throw new RuntimeException("Withdrawals not allowed on this wallet");
        }

        if (wallet.getUsableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient usable balance");
        }

        Transaction txn = new Transaction();
        txn.setWallet(wallet);
        txn.setAmount(amount);
        txn.setType(TransactionType.WITHDRAW);
        txn.setOppositeType(type);
        txn.setOppositeParty(dest);

        if (amount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            txn.setStatus(TransactionStatus.PENDING);
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(amount));
        } else {
            txn.setStatus(TransactionStatus.APPROVED);
            wallet.setBalance(wallet.getBalance().subtract(amount));
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(amount));
        }

        walletRepo.save(wallet);
        return transactionRepo.save(txn);
    }

    public List<Transaction> listTransactions(Long walletId) {
        return transactionRepo.findByWalletId(walletId);
    }

    @Transactional
    public Transaction approveOrDenyTransaction(Long txnId, TransactionStatus newStatus) {
        Transaction txn = transactionRepo.findById(txnId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Wallet wallet = txn.getWallet();

        if (txn.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction already finalized");
        }

        if (newStatus == TransactionStatus.APPROVED) {
            if (txn.getType() == TransactionType.DEPOSIT) {
                wallet.setUsableBalance(wallet.getUsableBalance().add(txn.getAmount()));
            } else if (txn.getType() == TransactionType.WITHDRAW) {
                wallet.setBalance(wallet.getBalance().subtract(txn.getAmount()));
            }
        } else if (newStatus == TransactionStatus.DENIED) {
            if (txn.getType() == TransactionType.DEPOSIT) {
                wallet.setBalance(wallet.getBalance().subtract(txn.getAmount()));
            } else if (txn.getType() == TransactionType.WITHDRAW) {
                wallet.setUsableBalance(wallet.getUsableBalance().add(txn.getAmount()));
            }
        }

        txn.setStatus(newStatus);
        walletRepo.save(wallet);
        return transactionRepo.save(txn);
    }

}
