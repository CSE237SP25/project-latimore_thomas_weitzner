package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankAccount;
import bankapp.MoneyMarketAccount;

public class MoneyMarketAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1000);
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 1025.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1000);

		try {
			account.deposit(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}

	
	@Test
    public void testAddNewAccount() {
        // Create a bank object
		String filePath = determineFilePathBlankInfo();
        Bank bank = new Bank(filePath);

        // Add a new account
        BankAccount newAccount = new MoneyMarketAccount("John Doe",12345,1000);
        bank.addAccount(newAccount);

        // Verify the account was added
        assertEquals(1, bank.getAccounts().size());
        assertEquals("John Doe", bank.getAccounts().get(0).getAccountName());
	}
	
	@Test
    public void testAddMultipleAccounts() {
        // Create a bank object
		String filePath = determineFilePathBlankInfo();
        Bank bank = new Bank(filePath);

        // Add multiple accounts
        BankAccount account1 = new MoneyMarketAccount("John Doe",12345,1000);
        BankAccount account2 = new MoneyMarketAccount("Jane Smith",112346,1000);
        bank.addAccount(account1);
        bank.addAccount(account2);

        // Verify the accounts were added
        assertEquals(2, bank.getAccounts().size());
        assertEquals("John Doe", bank.getAccounts().get(0).getAccountName());
        assertEquals("Jane Smith", bank.getAccounts().get(1).getAccountName());
    }
	
	private String determineFilePathBlankInfo() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String[] pathParts = s.split("[/\\\\]");
		String lastPart = pathParts[pathParts.length - 1];
		
		switch (lastPart) {
		case "bankapp":
			return "./bankResources/blankBankInfo.txt";
		case "src":
			return "./bankapp/bankResources/blankBankInfo.txt";
        case "project-latimore_thomas_weitzner":
        	return "./src/bankapp/bankResources/blankBankInfo.txt";
        default:
        	System.out.println("Please run the bankapp from the project-latimore_thomas_weitzner, bankapp, or src directories.");
        	System.out.println("The bankapp will not be able to save account information.");
        	return null;
		}
	}


	private String determineFilePathExampleInfo() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String[] pathParts = s.split("[/\\\\]");
		String lastPart = pathParts[pathParts.length - 1];
		
		switch (lastPart) {
		case "bankapp":
			return "./bankResources/exampleBankInfo.txt";
		case "src":
			return "./bankapp/bankResources/exampleBankInfo.txt";
        case "project-latimore_thomas_weitzner":
        	return "./src/bankapp/bankResources/exampleBankInfo.txt";
        default:
        	System.out.println("Please run the bankapp from the project-latimore_thomas_weitzner, bankapp, or src directories.");
        	System.out.println("The bankapp will not be able to save account information.");
        	return null;
		}
	}
	
	@Test
    public void testAddDuplicateAccount() {
        // Create a bank object
		String filePath = determineFilePathBlankInfo();
        Bank bank = new Bank(filePath);

        // Add an account
        BankAccount account = new MoneyMarketAccount("John Doe",12345,1000);
        bank.addAccount(account);
        // Try to add the same account again
        try {
            bank.addAccount(account);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e != null);
        }
        // Verify only one account was added
        assertEquals(1, bank.getAccounts().size());
    }
	
	//we require input before checking if any of these withdraw options are valid so can't test it here
	/*@Test 
	public void testSimpleWithdraw() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		
		account.withdraw(250);
		
		assertEquals(account.getCurrentBalance(), 1250.0, 0.005);
	}

	@Test 
	public void testWithdrawMoreThanBalance() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		
		try {
			account.withdraw(1650);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}

	@Test 
	public void testWithdrawPastMinimum() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		
		try {
			account.withdraw(600);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	*/

	@Test 
	public void testWithdrawNegativeAmount() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		
		try {
			account.withdraw(-2500);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testChangeAccountName() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1000);
		account.setAccountHolderName("Jane Smith");
		assertEquals(account.getAccountName(), "Jane Smith");
	}
	
	@Test
	public void testChangeAccountNameToEmpty() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1000);
		account.setAccountHolderName("");
		assertEquals(account.getAccountName(), "");
	}
	
// This was moved to the Menu class as renameAccount

//	@Test
//	public void testChangeAccountNameToLong() {
//		BankAccount account = new MoneyMarketAccount("John Doe");
//		account.setAccountHolderName("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//		assertEquals(account.getAccountName(), "John Doe");
//	}

//	@Test
//	public void testChangeAccountNameToSpecialCharacters() {
//		BankAccount account = new MoneyMarketAccount("John Doe");
//		for (char c : "!@#$%^&*()_+-=[]{}|;':,.<>?/".toCharArray()) {
//			account.setAccountHolderName("John Doe" + c);
//			assertEquals("John Doe", account.getAccountName());
//			}
//		}

	@Test
	public void testRemoveAccount() {
		String filePath = determineFilePathBlankInfo();
        Bank bank = new Bank(filePath);
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		bank.addAccount(account);
		bank.removeAccount(account);
		assertEquals(0, bank.getAccounts().size());
	}
	
	@Test
	public void testRemoveAccountNotThere() {
		String filePath = determineFilePathExampleInfo();
        Bank bank = new Bank(filePath);
		
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		try {
			bank.removeAccount(account);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);

		}
	}
	// In BankAccountTests.java, update the transfer tests to use assertTrue() instead of assertEquals()
	//we can't test transfering from money market because it requires input
	/*@Test
	public void testSimpleTransfer() {
		BankAccount source = new MoneyMarketAccount("Source",12345,2000);
		BankAccount target = new MoneyMarketAccount("Target",12346,1000);
		
		source.transfer(target, 500);
		
		assertTrue(Math.abs(source.getCurrentBalance() - 1500) < 0.001);
		assertTrue(Math.abs(target.getCurrentBalance() - 1500) < 0.001);
	}

	@Test
	public void testTransferNegativeAmount() {
		BankAccount source = new MoneyMarketAccount("Source",12345,2000);
		BankAccount target = new MoneyMarketAccount("Target",12346,1000);
		
		try {
			source.transfer(target, -10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("positive"));
			assertTrue(Math.abs(source.getCurrentBalance() - 2000) < 0.001);
			assertTrue(Math.abs(target.getCurrentBalance() - 1000) < 0.001);
		}
	}

	@Test
	public void testTransferInsufficientFunds() {
		BankAccount source = new MoneyMarketAccount("Source",12345,2000);
		BankAccount target = new MoneyMarketAccount("Target",12346,1000);
		
		try {
			source.transfer(target, 250);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("funds"));
			assertTrue(Math.abs(source.getCurrentBalance() - 2000) < 0.001);
			assertTrue(Math.abs(target.getCurrentBalance() - 1000) < 0.001);
		}
	}

	@Test
	public void testTransferPastMinimum() {
		BankAccount source = new MoneyMarketAccount("Source",12345,2000);
		BankAccount target = new MoneyMarketAccount("Target",12346,1000);
		
		try {
			source.transfer(target, 1500);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(Math.abs(source.getCurrentBalance() - 2000) < 0.001);
			assertTrue(Math.abs(target.getCurrentBalance() - 1000) < 0.001);
		}
	}

	@Test
	public void testTransferToSameAccount() {
		BankAccount account = new MoneyMarketAccount("Account",12345,1500);
		
		try {
			account.transfer(account, 50);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("same account"));
			assertTrue(Math.abs(account.getCurrentBalance() - 1500) < 0.001);
		}
	}*/

	@Test
	public void testTransactionHistory() {
		BankAccount account = new MoneyMarketAccount("John Doe", 12345, 1500);
		List<String> previousHistory = account.getTransactionHistory();
		account.deposit(50);
		
		List<String> history = account.getTransactionHistory();
		assertEquals(1+previousHistory.size(),history.size());
		assertTrue(history.get(0).contains("Deposit"));
	}
	
	@Test
	public void testTransactionHistoryEmpty() {
		BankAccount account = new MoneyMarketAccount("John Doe",123456,1500);
		List<String> history = account.getTransactionHistory();
		//we expect it to be 1 because of the initial creation of the account
		assertEquals(history.size(), 1);
	}
	
	@Test
	public void testTransactionHistoryTime() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
		account.deposit(50);

		List<String> history = account.getTransactionHistory();
		assertTrue(history.get(0).contains("Time"));
	}
	
	//don't think we have this anymore for MoneyMarket 
	/*@Test
	public void testInitializeBalance() {
		BankAccount account = new MoneyMarketAccount("John Doe",12345,1500);
        account.initializeAccountBalance(1000);
		List<String> history = account.getTransactionHistory();
		
        assertEquals(account.getCurrentBalance(), 1000.0, 0.005);
        assertEquals(history.size(), 0);
	}*/
	
}
