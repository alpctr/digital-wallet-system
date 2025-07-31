Project structure:

digital-wallet-system/
 ├─ src/
 │  ├─ main/
 │  │  ├─ java/com/alpctr/digitalwalletsystem/
 │  │  │  ├─ controller/       # REST API endpoints
 │  │  │  ├─ service/          # Business logic
 │  │  │  ├─ model/            # Entities (Wallet, Transaction, Customer)
 │  │  │  ├─ dto/              # Request/response objects
 │  │  │  ├─ repository/       # Data access layer
 │  │  │  ├─ config/           # Security and app config
 │  │  ├─ resources/
 │  │     ├─ application.properties
 │  │     ├─ data.sql          # Preload customer data
 │  ├─ test/java/...           # Integration tests
 ├─ pom.xml
 ├─ README.md
 ├─ DOCUMENTATION.md
 
 In-memory H2 database was used to store data for CUSTOMER, TRANSACTION and WALLET.
 
 SecurityConfig class was added to determine and authorize EMPLOYEE and CUSTOMER roles.
 
 data.sql file was populated with the test customers and initially these customers are inserted to CUSTOMER table once the application is started.
 
 As username customer TCKN is used and customers can only access their own wallet
 
 Amount check is managed in the WalletService class.
 
 testWithdrawAndListTransactions test method validates the end-to-end functionality of withdrawing money from a wallet
 and listing all transactions for that wallet. Firstly it creates wallet. Two times 750 EUR is deposited to wallet from IBAN
 and then 50 EUR withdrawn to IBAN. Then transactions are listed and checked if withdrawal transaction exists in the list
 
 testDepositAndListTransactions test method creates wallet first then deposits 100 EUR. Then lists wallets for same customer.
 Transactions are listed for the first wallet from the list.
 
 Project can be packaged and tests can be executed with the following maven command:
 - mvn clean package
 
 
 
 