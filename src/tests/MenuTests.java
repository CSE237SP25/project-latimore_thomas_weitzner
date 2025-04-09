package tests;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bankapp.*;

public class MenuTests {
    private Menu menu;
    private Bank bank;
    private User testUser;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        menu = new Menu();
        testUser = new User("testUser", "testPass");
        bank.addUser(testUser);
        menu.user = testUser; // Simulate logged in user
    }

    @Test
    void testCreateAccount() {
        int initialAccountCount = testUser.getAccounts().size();
        menu.createAccount();
        assertEquals(initialAccountCount + 1, testUser.getAccounts().size());
    }

    @Test
    void testDepositValidAmount() {
        BankAccount account = new BankAccount("Test Account");
        testUser.addAccount(account);
        menu.currentUserAccount = account;
        
        double initialBalance = account.getCurrentBalance();
        double depositAmount = 100.0;
        
        // Simulate user input
        menu.inputScanner = new java.util.Scanner(String.valueOf(depositAmount));
        menu.deposit();
        
        assertEquals(initialBalance + depositAmount, account.getCurrentBalance(), 0.001);
    }

    @Test
    void testWithdrawValidAmount() {
        BankAccount account = new BankAccount("Test Account");
        account.deposit(200.0); // Start with some balance
        testUser.addAccount(account);
        menu.currentUserAccount = account;
        
        double withdrawAmount = 50.0;
        
        // Simulate user input
        menu.inputScanner = new java.util.Scanner(String.valueOf(withdrawAmount));
        menu.withdraw();
        
        assertEquals(150.0, account.getCurrentBalance(), 0.001);
    }

    @Test
    void testRenameAccountValidName() {
        BankAccount account = new BankAccount("Old Name");
        testUser.addAccount(account);
        menu.currentUserAccount = account;
        
        String newName = "New Valid Name";
        menu.inputScanner = new java.util.Scanner(newName);
        menu.renameAccount();
        
        assertEquals(newName, account.getAccountName());
    }

    @Test
    void testChangeUsernameValid() {
        String newUsername = "newUsername";
        menu.inputScanner = new java.util.Scanner("testPass\n" + newUsername);
        menu.changeUsername();
        
        assertEquals(newUsername, testUser.getUsername());
    }

    @Test
    void testChangePasswordValid() {
        String newPassword = "newPassword123";
        menu.inputScanner = new java.util.Scanner("testPass\n" + newPassword);
        menu.changePassword();
        
        assertEquals(newPassword, testUser.getPassword());
    }

    @Test
    void testTransferBetweenAccounts() {
        BankAccount source = new BankAccount("Source");
        source.deposit(300.0);
        BankAccount target = new BankAccount("Target");
        testUser.addAccount(source);
        testUser.addAccount(target);
        
        // Simulate user input: select source, then target, then amount 100
        menu.inputScanner = new java.util.Scanner(
            source.getAccountNumber() + "\n" + 
            target.getAccountNumber() + "\n" + 
            "100");
        
        menu.transferBetweenAccounts();
        
        assertEquals(200.0, source.getCurrentBalance(), 0.001);
        assertEquals(100.0, target.getCurrentBalance(), 0.001);
    }

    @Test
    void testIsInvalidAccountName() {
        // Test empty name
        assertEquals(Menu.InvalidNameReason.EMPTY, 
                   menu.isInvalidAccountName(""));
        
        // Test too long namels
    }