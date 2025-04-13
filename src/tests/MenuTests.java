package tests;

import bankapp.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTests {

    private Menu menu;
    private User testUser;
    private BankAccount account1;
    private BankAccount account2;
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        testUser = new User("testUser", "testPass");
        account1 = new BankAccount("AccountOne");
        account2 = new BankAccount("AccountTwo");
        account1.deposit(100.0);
        account2.deposit(50.0);
        testUser.addAccount(account1);
        testUser.addAccount(account2);
        bank.addUser(testUser);
        menu = new Menu();
        menu.user = testUser;
        menu.currentUserAccount = account1;
    }

    //existing account can add a user
    @Test
    public void testCreateAccountAddsAccount() {
        int initialSize = testUser.getAccounts().size();
        menu.createAccount();
        assertEquals(initialSize + 1, testUser.getAccounts().size());
    }

    //user can remove an account
    @Test
    public void testDeleteAccountRemovesAccount() {
        int initialSize = testUser.getAccounts().size();
        menu.currentUserAccount = account1; 
        menu.removeAccount();
        assertEquals(initialSize - 1, testUser.getAccounts().size());
    }

    //testing the account name (special characters)
    @Test
    public void testIsInvalidAccountName() {
        menu.currentUserAccount = new BankAccount("OldName");

        assertEquals(Menu.InvalidNameReason.EMPTY, menu.isInvalidAccountName(""));
        assertEquals(Menu.InvalidNameReason.LONG, menu.isInvalidAccountName("ThisNameIsWayTooLongToActuallyWerk"));
        assertEquals(Menu.InvalidNameReason.SAME_NAME, menu.isInvalidAccountName("OldName"));
        assertEquals(Menu.InvalidNameReason.SPECIAL_CHARACTERS, menu.isInvalidAccountName("B@@@@d@Name"));
        assertEquals(Menu.InvalidNameReason.NONE, menu.isInvalidAccountName("GoodName"));
    }
    
    //testing account deposit
    @Test
    public void testDepositIncreasesBalance() {
        double before = account1.getCurrentBalance();
        account1.deposit(25.0);
        assertEquals(before + 25.0, account1.getCurrentBalance());
    }

    //testing account withdraw
    @Test
    public void testWithdrawDecreasesBalance() {
        double initialBalance = account1.getCurrentBalance();
        account1.withdraw(30.0);
        assertEquals(initialBalance - 30.0, account1.getCurrentBalance());
    }
/* this didnt work because something in menu would need to change
    @Test
    public void testDisplayBalanceShowsCorrectAmount() {
        menu.currentUserAccount = account1;
        menu.displayBalance();
    }
*/
    //account transfer tests
    @Test
    public void testTransferBetweenAccounts() {
        account1.transfer(account2, 30.0);
        assertEquals(70.0, account1.getCurrentBalance());
        assertEquals(80.0, account2.getCurrentBalance());
    }

    //new accounts have diff numbers
    @Test
    public void testCreateAccountAssignsUniqueNumber() {
        BankAccount newAccount1 = new BankAccount("New1");
        BankAccount newAccount2 = new BankAccount("New2");
        assertNotEquals(newAccount1.getAccountNumber(), newAccount2.getAccountNumber());
    }

    //testing the login
    @Test
    public void testValidPassword() {
        User validUser = new User("validUser", "correctPass");
        bank.addUser(validUser);

        assertTrue(menu.getLogin().checkPassword(validUser, "correctPass"));
    }

}
