package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankAccount;

public class BankTests {
    @Test
	public void testLoadingExampleAccounts() {
        //note that this test requires the bankResources/exampleBankInfo.txt file to be present in the project and properly formatted with account information
        //this is done beforehand so it should be fine
        Bank bank = new Bank("bankResources/exampleBankInfo.txt");
		
        List<BankAccount> accounts = bank.getAccounts();
        assertEquals(3, accounts.size());

        assertEquals("John Doe", accounts.get(0).getAccountHolderName());
        assertEquals(100.0, accounts.get(0).getCurrentBalance(), 100.0);

        assertEquals("Jane Doe", accounts.get(1).getAccountHolderName());
        assertEquals(17.50, accounts.get(1).getCurrentBalance(), 17.50);

        assertEquals("Jack Doe", accounts.get(2).getAccountHolderName());
        assertEquals(2000.0, accounts.get(2).getCurrentBalance(), 2000.0);
	}

    public void testLoadingBlankBankInfo(){
        Bank bank = new Bank("bankResources/blankBankInfo.txt");

        List<BankAccount> accounts = bank.getAccounts();
        assertEquals(0, accounts.size());
    }

    public void testAddingAccount(){
        Bank bank = new Bank("bankResources/exampleAddBankInfo.txt");
        int initialSize = bank.getAccounts().size();

        BankAccount newAccount = new BankAccount("New Account Holder");
        bank.addAccount(newAccount);
        assertEquals(initialSize + 1, bank.getAccounts().size());
        assertEquals("New Account Holder", bank.getAccounts().get(initialSize).getAccountHolderName());
    }
}