package com.alpctr.digitalwalletsystem.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alpctr.digitalwalletsystem.dto.TransactionRequest;
import com.alpctr.digitalwalletsystem.dto.WalletRequest;
import com.alpctr.digitalwalletsystem.dto.WithdrawRequest;
import com.alpctr.digitalwalletsystem.model.Transaction;
import com.alpctr.digitalwalletsystem.model.Wallet;
import com.alpctr.enums.Currency;
import com.alpctr.enums.OppositePartyType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/api";
    }

    @Test
    void testCreateWalletAndListWallets() {
    	
    	TestRestTemplate employeeClient = restTemplate.withBasicAuth("00000000000", "password");
    	
        // Prepare wallet request
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setCustomerId(1L);  // Use a valid customer ID in your test DB
        walletRequest.setWalletName("TestWallet");
        walletRequest.setCurrency(Currency.EUR);
        walletRequest.setActiveForShopping(true);
        walletRequest.setActiveForWithdraw(true);

        // Create Wallet
        ResponseEntity<Wallet> createResponse = employeeClient.postForEntity(
            baseUrl + "/wallets", walletRequest, Wallet.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet createdWallet = createResponse.getBody();
        assertThat(createdWallet).isNotNull();
        assertThat(createdWallet.getWalletName()).isEqualTo("TestWallet");

        // List Wallets by customerId
        ResponseEntity<Wallet[]> listResponse = employeeClient.getForEntity(
            baseUrl + "/wallets?customerId=1", Wallet[].class);

        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet[] wallets = listResponse.getBody();
        assertThat(wallets).isNotNull();
        assertThat(wallets.length).isGreaterThan(0);
    }
    
    @Test
    void testDepositAndListTransactions() {
    	
    	TestRestTemplate employeeClient = restTemplate.withBasicAuth("00000000000", "password");
    	
        // Prepare wallet request
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setCustomerId(1L);
        walletRequest.setWalletName("TestWallet");
        walletRequest.setCurrency(Currency.EUR);
        walletRequest.setActiveForShopping(true);
        walletRequest.setActiveForWithdraw(true);

        // Create Wallet
        ResponseEntity<Wallet> createResponse = employeeClient.postForEntity(
            baseUrl + "/wallets", walletRequest, Wallet.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet createdWallet = createResponse.getBody();
        assertThat(createdWallet).isNotNull();
        assertThat(createdWallet.getWalletName()).isEqualTo("TestWallet");

        // List Wallets by customerId
        ResponseEntity<Wallet[]> listResponse = employeeClient.getForEntity(
            baseUrl + "/wallets?customerId=1", Wallet[].class);

        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet[] wallets = listResponse.getBody();
        assertThat(wallets).isNotNull();
        assertThat(wallets.length).isGreaterThan(0);
        
        // Deposit
        TransactionRequest depositRequest = new TransactionRequest();
        depositRequest.setWalletId(wallets[0].getId());
        depositRequest.setAmount(BigDecimal.valueOf(100.0));
        depositRequest.setSourceType(OppositePartyType.IBAN);
        depositRequest.setSource("TR330006100519786457841326");

        ResponseEntity<Transaction> depositResponse = employeeClient.postForEntity(
            baseUrl + "/deposit", depositRequest, Transaction.class);

        assertThat(depositResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transaction transaction = depositResponse.getBody();
        assertThat(transaction).isNotNull();
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));

        // List transactions for the wallet
        ResponseEntity<Transaction[]> listTxResponse = employeeClient.getForEntity(
            baseUrl + "/transactions?walletId=" + wallets[0].getId(), Transaction[].class);

        assertThat(listTxResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transaction[] transactions = listTxResponse.getBody();
        assertThat(transactions).isNotNull();
        assertThat(transactions.length).isGreaterThan(0);
    }

    
    @Test
    void testWithdrawAndListTransactions() {
    	
    	TestRestTemplate employeeClient = restTemplate.withBasicAuth("00000000000", "password");
    	
        // Assume walletId 1L exists; create or adjust as needed
        Long walletId = 1L;
        
        // Prepare wallet request
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setCustomerId(1L);
        walletRequest.setWalletName("TestWallet");
        walletRequest.setCurrency(Currency.EUR);
        walletRequest.setActiveForShopping(true);
        walletRequest.setActiveForWithdraw(true);

        // Create Wallet
        ResponseEntity<Wallet> createResponse = employeeClient.postForEntity(
            baseUrl + "/wallets", walletRequest, Wallet.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet createdWallet = createResponse.getBody();
        assertThat(createdWallet).isNotNull();
        assertThat(createdWallet.getWalletName()).isEqualTo("TestWallet");

        // List Wallets by customerId
        ResponseEntity<Wallet[]> listResponse = employeeClient.getForEntity(
            baseUrl + "/wallets?customerId=1", Wallet[].class);

        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Wallet[] wallets = listResponse.getBody();
        assertThat(wallets).isNotNull();
        assertThat(wallets.length).isGreaterThan(0);
        
        // Deposit for usableBalance
        TransactionRequest depositRequest = new TransactionRequest();
        depositRequest.setWalletId(wallets[0].getId());
        depositRequest.setAmount(BigDecimal.valueOf(750));
        depositRequest.setSourceType(OppositePartyType.IBAN);
        depositRequest.setSource("TR330006100519786457841326");

        ResponseEntity<Transaction> depositResponse = employeeClient.postForEntity(
            baseUrl + "/deposit", depositRequest, Transaction.class);

        assertThat(depositResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transaction transaction = depositResponse.getBody();
        assertThat(transaction).isNotNull();
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(750));
        
        ResponseEntity<Transaction> depositResponse2 = employeeClient.postForEntity(
                baseUrl + "/deposit", depositRequest, Transaction.class);

        assertThat(depositResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transaction transaction2 = depositResponse2.getBody();
        assertThat(transaction2).isNotNull();
        assertThat(transaction2.getAmount()).isEqualTo(BigDecimal.valueOf(750));

        // Withdraw
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setWalletId(walletId);
        withdrawRequest.setAmount(BigDecimal.valueOf(50));
        withdrawRequest.setDestinationType(OppositePartyType.IBAN);
        withdrawRequest.setDestination("TR330006100519786457841326"); // example IBAN

        // Withdraw request
        ResponseEntity<Transaction> withdrawResponse = employeeClient.postForEntity(
            baseUrl + "/withdraw", withdrawRequest, Transaction.class);

        assertThat(withdrawResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transaction withdrawTransaction = withdrawResponse.getBody();
        assertThat(withdrawTransaction).isNotNull();
        assertThat(withdrawTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(50));
        assertThat(withdrawTransaction.getWallet().getId()).isEqualTo(walletId);

        // List transactions for the wallet
        ResponseEntity<Transaction[]> listTxResponse = employeeClient.getForEntity(
            baseUrl + "/transactions?walletId=" + walletId, Transaction[].class);

        assertThat(listTxResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Transaction[] transactions = listTxResponse.getBody();
        assertThat(transactions).isNotNull();
        assertThat(transactions.length).isGreaterThan(0);

        // Optional: Check if withdrawal transaction exists in the list
        boolean withdrawalFound = Arrays.stream(transactions)
            .anyMatch(tx -> tx.getId().equals(withdrawTransaction.getId()));
        assertThat(withdrawalFound).isTrue();
    }
    
    

}
