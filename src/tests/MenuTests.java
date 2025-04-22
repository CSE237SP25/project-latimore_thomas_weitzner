package tests;

import bankapp.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTests {
    private Menu menu;
    private User testUser;
    private BankAccount account1;
    private BankAccount account2;
    private Bank bank;
    

	private String determineFilePathExampleAddInfo() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String[] pathParts = s.split("[/\\\\]");
		String lastPart = pathParts[pathParts.length - 1];
		
		switch (lastPart) {
		case "bankapp":
			return "./bankResources/exampleAddBankInfo.txt";
		case "src":
			return "./bankapp/bankResources/exampleAddBankInfo.txt";
        case "project-latimore_thomas_weitzner":
        	return "./src/bankapp/bankResources/exampleAddBankInfo.txt";
        default:
        	System.out.println("Please run the bankapp from the project-latimore_thomas_weitzner, bankapp, or src directories.");
        	System.out.println("The bankapp will not be able to save account information.");
        	return null;
		}
	}

    @BeforeEach
    public void setUp() {
        String filePath = determineFilePathExampleAddInfo();
        bank = new Bank(filePath);
        testUser = null;
        for(User user : bank.getUsers()) {
            if(user.getUsername().equals("testUser")) {
                testUser = user;
            }
        }
        account1 = null;
        account2 = null;
        for(BankAccount account : testUser.getAccounts()) {
            if(account.getAccountName().equals("AccountOne")) {
                account1 = account;
            }
            if(account.getAccountName().equals("AccountTwo")) {
                account2 = account;
            }
        }

        menu = new Menu(bank,testUser);
        menu.user = testUser;
        menu.currentUserAccount = account1;
    }

    

    //existing account can add a user
    //this test is no longer valid since createAccount now requires IO
    /*@Test
    public void testCreateAccountAddsAccount() {
        int initialSize = testUser.getAccounts().size();
        menu.createAccount();
        assertEquals(initialSize + 1, testUser.getAccounts().size());
    }*/


    //testing the account name (special characters)
    @Test
    public void testIsInvalidAccountName() {
        menu.currentUserAccount = new CheckingsAccount("OldName");

        assertEquals(Menu.InvalidNameReason.EMPTY, menu.isInvalidAccountName(""));
        assertEquals(Menu.InvalidNameReason.LONG, menu.isInvalidAccountName("ThisNameIsWayTooLongToActuallyWerk"));
        assertEquals(Menu.InvalidNameReason.SAME_NAME, menu.isInvalidAccountName("OldName"));
        assertEquals(Menu.InvalidNameReason.SPECIAL_CHARACTERS, menu.isInvalidAccountName("B@@@@d@Name"));
        assertEquals(Menu.InvalidNameReason.NONE, menu.isInvalidAccountName("GoodName"));
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
        BankAccount newAccount1 = new CheckingsAccount("New1");
        BankAccount newAccount2 = new CheckingsAccount("New2");
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