package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankAccount;

public class BankAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount("John Doe");
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new BankAccount("John Doe");

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
        Bank bank = new Bank();

        // Add a new account
        BankAccount newAccount = new BankAccount("John Doe");
        bank.addAccount(newAccount);

        // Verify the account was added
        assertEquals(1, bank.getAccounts().size());
        assertEquals("John Doe", bank.getAccounts().get(0).getAccountName());
	}
	
	@Test
    public void testAddMultipleAccounts() {
        // Create a bank object
        Bank bank = new Bank();

        // Add multiple accounts
        BankAccount account1 = new BankAccount("John Doe");
        BankAccount account2 = new BankAccount("Jane Smith");
        bank.addAccount(account1);
        bank.addAccount(account2);

        // Verify the accounts were added
        assertEquals(2, bank.getAccounts().size());
        assertEquals("John Doe", bank.getAccounts().get(0).getAccountName());
        assertEquals("Jane Smith", bank.getAccounts().get(1).getAccountName());
    }
	
	
	@Test
    public void testAddDuplicateAccount() {
        // Create a bank object
        Bank bank = new Bank();

        // Add an account
        BankAccount account = new BankAccount("John Doe");
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
	
	@Test 
	public void testSimpleWithdraw() {
		BankAccount account = new BankAccount("John Doe");
		
		account.deposit(50);
		account.withdraw(25);
		
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}

	@Test 
	public void testWithdrawMoreThanBalance() {
		BankAccount account = new BankAccount("John Doe");
		
		account.deposit(50);
		
		try {
			account.withdraw(75);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}

	@Test 
	public void testWithdrawNegativeAmount() {
		BankAccount account = new BankAccount("John Doe");
		
		account.deposit(50);
		try {
			account.withdraw(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}

	@Test 
	public void testWithdrawFromEmptyAccount() {
		BankAccount account = new BankAccount("John Doe");
		
		try {
			account.withdraw(25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	@Test
	public void testChangeAccountName() {
		BankAccount account = new BankAccount("John Doe");
		account.setAccountHolderName("Jane Smith");
		assertEquals(account.getAccountName(), "Jane Smith");
	}
	
	@Test
	public void testChangeAccountNameToEmpty() {
		BankAccount account = new BankAccount("John Doe");
		account.setAccountHolderName("");
		assertEquals(account.getAccountName(), "");
	}
	
	@Test
	public void testChangeAccountNameToLong() {
		BankAccount account = new BankAccount("John Doe");
		account.setAccountHolderName("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		assertEquals(account.getAccountName(), "John Doe");
	}
	
	@Test
	public void testChangeAccountNameToSpecialCharacters() {
		BankAccount account = new BankAccount("John Doe");
		for (char c : "!@#$%^&*()_+-=[]{}|;':,.<>?/".toCharArray()) {
			account.setAccountHolderName("John Doe" + c);
			assertEquals(account.getAccountName(), "John Doe");
			}
		}
	
	public void testRemoveAccount() {
		Bank bank = new Bank();
		BankAccount account = new BankAccount("John Doe");
		bank.addAccount(account);
		bank.removeAccount(account);
		assertEquals(0, bank.getAccounts().size());
	}
	
	@Test
	public void testRemoveAccountNotThere() {
		Bank bank = new Bank();
		BankAccount account = new BankAccount("John Doe");
		try {
			bank.removeAccount(account);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);

		}
	}
	
	@Test
	public void testTransactionHistory() {
		BankAccount account = new BankAccount("John Doe");
		account.deposit(50);
		account.withdraw(25);
		
		List<String> history = account.getTransactionHistory();
		assertEquals(history.size(), 2);
		assertTrue(history.get(0).contains("Deposit"));
		assertTrue(history.get(1).contains("Withdraw"));
	}
	
	@Test
	public void testTransactionHistoryEmpty() {
		BankAccount account = new BankAccount("John Doe");
		List<String> history = account.getTransactionHistory();
		assertEquals(history.size(), 0);
	}
	
	@Test
	public void testTransactionHistoryTime() {
		BankAccount account = new BankAccount("John Doe");
		account.deposit(50);
		account.withdraw(25);

		List<String> history = account.getTransactionHistory();
		assertTrue(history.get(0).contains("Time"));
		assertTrue(history.get(1).contains("Time"));
	}
	
}
