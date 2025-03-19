package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        assertEquals("John Doe", bank.getAccounts().get(0).getAccountHolderName());
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
        assertEquals("John Doe", bank.getAccounts().get(0).getAccountHolderName());
        assertEquals("Jane Smith", bank.getAccounts().get(1).getAccountHolderName());
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
	
}
