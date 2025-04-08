package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.List;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankAccount;
import bankapp.Teller;

public class BankTests {
    @Test
	public void testLoadingExampleAccounts() {
        //note that this test requires the bankResources/exampleBankInfo.txt file to be present in the project and properly formatted with account information
        //this is done beforehand so it should be fine
        Bank bank = new Bank("bankResources/exampleBankInfo.txt");
		
        List<BankAccount> accounts = bank.getAccounts();
        assertEquals(3, accounts.size());

        assertEquals("John Doe", accounts.get(0).getAccountName());
        assertEquals(100.0, accounts.get(0).getCurrentBalance(), 100.0);

        assertEquals("Jane Doe", accounts.get(1).getAccountName());
        assertEquals(17.50, accounts.get(1).getCurrentBalance(), 17.50);

        assertEquals("Jack Doe", accounts.get(2).getAccountName());
        assertEquals(2000.0, accounts.get(2).getCurrentBalance(), 2000.0);

        List<Teller> tellers = bank.getTellers();
        assertEquals(1, tellers.size());
        assertEquals("Teller1", tellers.get(0).getUsername());
	}

    public void testLoadingBlankBankInfo(){
        Bank bank = new Bank("bankResources/blankBankInfo.txt");

        List<BankAccount> accounts = bank.getAccounts();
        assertEquals(0, accounts.size());

        List<Teller> tellers = bank.getTellers();
        assertEquals(0, tellers.size());
    }

    public void testAddingAccount(){
        Bank bank = new Bank("bankResources/exampleAddBankInfo.txt");
        int initialSize = bank.getAccounts().size();

        BankAccount newAccount = new BankAccount("New Account Holder");
        bank.addAccount(newAccount);
        assertEquals(initialSize + 1, bank.getAccounts().size());
        assertEquals("New Account Holder", bank.getAccounts().get(initialSize).getAccountName());
    }

    public void testAddingTellerAccount(){
        Bank bank = new Bank("bankResources/exampleAddBankInfo.txt");
        int initialSize = bank.getTellers().size();

        Teller newTeller = new Teller("NewTeller", "password");
        bank.addTeller(newTeller);
        assertEquals(initialSize + 1, bank.getTellers().size());
        assertEquals("NewTeller", bank.getTellers().get(initialSize).getUsername());
    }
}