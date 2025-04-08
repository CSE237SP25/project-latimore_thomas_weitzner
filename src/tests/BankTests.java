package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankAccount;

public class BankTests {
	
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
	
	
	
    @Test
	public void testLoadingExampleAccounts() {
        //note that this test requires the bankResources/exampleBankInfo.txt file to be present in the project and properly formatted with account information
        //this is done beforehand so it should be fine
        String filePath = determineFilePathExampleInfo();


        Bank bank = new Bank(filePath);
        List<BankAccount> accounts = bank.getAccounts();

        assertEquals(3, accounts.size());

        assertEquals("John Doe", accounts.get(0).getAccountName());
        assertEquals(100.0, accounts.get(0).getCurrentBalance(), 100.0);

        assertEquals("Jane Doe", accounts.get(1).getAccountName());
        assertEquals(17.50, accounts.get(1).getCurrentBalance(), 17.50);

        assertEquals("Jack Doe", accounts.get(2).getAccountName());
        assertEquals(2000.0, accounts.get(2).getCurrentBalance(), 2000.0);
	}

    @Test
    public void testLoadingBlankBankInfo(){
    	String filePathBlankInfo = determineFilePathBlankInfo();
        Bank bank = new Bank(filePathBlankInfo);

        List<BankAccount> accounts = bank.getAccounts();
        assertEquals(0, accounts.size());
    }

    @Test
    public void testAddingAccount(){
    	String filePathBlankInfo = determineFilePathExampleAddInfo();
		Bank bank = new Bank(filePathBlankInfo);
        int initialSize = bank.getAccounts().size();

        BankAccount newAccount = new BankAccount("New Account Holder");
        bank.addAccount(newAccount);
        assertEquals(initialSize + 1, bank.getAccounts().size());
        assertEquals("New Account Holder", bank.getAccounts().get(initialSize).getAccountName());
    }
}