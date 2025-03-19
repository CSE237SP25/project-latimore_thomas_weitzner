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
	private String bankFilePath;
	
	public Bank() {
		this.accounts = new ArrayList<>();
		this.bankFilePath = "bankResources/bankPastInfo.txt"; // Default file path for account info
		File f=new File("bankResources/bankPastInfo.txt");
		Path fullPath = f.toPath();
		try {
			this.accountInfoList = Files.readAllLines(fullPath);
		} catch (IOException e) {
			System.out.println("Error reading account info file: " + e.getMessage());
		} 

		loadAccountsFromFile(); // Load accounts from file when the bank is created
	}

	public Bank(String filePath) {
		this.accounts = new ArrayList<>();
		this.bankFilePath = filePath; // Use provided file path for account info
		File f=new File(filePath);
		Path fullPath = f.toPath();
		try {
			this.accountInfoList = Files.readAllLines(fullPath);
		} catch (IOException e) {
			System.out.println("Error reading account info file: " + e.getMessage());
		} 

		loadAccountsFromFile(); // Load accounts from file when the bank is created
	}
	
	public void addAccount(BankAccount account) {
		for (BankAccount acc : accounts) {
			if (acc.getAccountNumber() == (account.getAccountNumber())) {
				throw new IllegalArgumentException("Account number already exists");
			}
		}
		this.accounts.add(account);
		saveAccountsToFile(); // Save the updated account info to file
	}
	

	public void saveAccountsToFile() {
		try (FileWriter writer = new FileWriter(bankFilePath)) {
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

	public List<BankAccount> getAccounts() {
		return this.accounts;
	}
}
