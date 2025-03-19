package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;

public class Bank {
	//NOTE: This is the entire bank, should only be ONE BANK OBJECT IN THE PROJECT
	private List<BankAccount> accounts;
	private List<String> accountInfoList; // List to hold account info strings
	
	public Bank() {
		this.accounts = new ArrayList<>();
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		File f=new File("bankResources/bankPastInfo.txt");
		Path fullPath = f.toPath();
		try {
			// do stuff
			this.accountInfoList = Files.readAllLines(fullPath);
		} catch (IOException e) {
			// do whatever
			System.out.println("Error reading account info file: " + e.getMessage());
		} 

		//System.out.println("Current absolute path is: " + fullPath);
		//this.fileReader = new FileReader("bankResources/bankPastInfo.txt"); 
		loadAccountsFromFile(); // Load accounts from file when the bank is created
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
		try (FileWriter writer = new FileWriter("bankResources/bankPastInfo.txt")) {
			for (BankAccount account : accounts) {
				writer.write(account.getAccountHolderName() + "," + account.getAccountNumber() + "," + account.getCurrentBalance() + "\n");
			}
		} catch (IOException e) {
			System.out.println("Error saving accounts to file: " + e.getMessage());
		}
	}

	public void loadAccountsFromFile() {
		for(String accountInfo : accountInfoList) {
			makeAccountFromFile(accountInfo);
		}
	}
	
	public void makeAccountFromFile(String accountInfo){
		String[] parts = accountInfo.split(",");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Invalid account info format");
		}
		String name = parts[0];
		int accountNumber = Integer.parseInt(parts[1]);
		double balance = Double.parseDouble(parts[2]);
		
		BankAccount account = new BankAccount(name);
		account.deposit(balance); //use deposit method to set the initial balance
		addAccount(account);
	}
}
