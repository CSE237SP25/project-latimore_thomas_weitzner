package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Bank {
	//NOTE: This is the entire bank, should only be ONE BANK OBJECT IN THE PROJECT
	private List<BankAccount> accounts;
	
	public Bank() {
		this.accounts = new ArrayList<>();
	}
	
	public void addAccount(BankAccount account) {
		for (BankAccount acc : accounts) {
			if (acc.getAccountNumber() == (account.getAccountNumber())) {
				throw new IllegalArgumentException("Account number already exists");
			}
		}
		this.accounts.add(account);
	}
	
	public List<BankAccount> getAccounts() {
		return this.accounts;
	}

	public void saveAccountsToFile() {
		try (FileWriter writer = new FileWriter('bankPastInfo.txt')) {
			for (BankAccount account : accounts) {
				writer.write(account.getAccountHolderName() + "," + account.getAccountNumber() + "," + account.getCurrentBalance() + "\n");
			}
		} catch (IOException e) {
			System.out.println("Error saving accounts to file: " + e.getMessage());
		}
	}

	public void loadAccountsFromFile() {
		Scanner scanPast = new Scanner(new File("bankPastInfo.txt"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
    		list.add(s.next());
		}
		s.close();
		//NEED TO IMPLEMENT THE REST OF THE LOGIC HERE
	}
	
}
