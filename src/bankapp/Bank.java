package bankapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Bank {
	//NOTE: This is the entire bank, should only be ONE BANK OBJECT IN THE PROJECT
	private List<BankAccount> accounts;
	private List<String> accountInfoList = new ArrayList<>(); // List to hold account info strings
	private List<User> users = new ArrayList<>();// List to hold users (if needed)
	private String bankFilePath;
	
	public Bank() {
		this.accounts = new ArrayList<>();
		this.bankFilePath = "./bankResources/bankPastInfo.txt"; // Default file path for account info
		File f=new File("./bankResources/bankPastInfo.txt");
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

	public void addUser(User user){
		this.users.add(user); // Add a new user to the list
		for (BankAccount account : user.getAccounts()) { // Add user's accounts to the bank
			addAccount(account);
		}
	}

	public void saveAccountsToFile() {
		try (FileWriter writer = new FileWriter(bankFilePath)) {
			for(User user : users) { // Save user info if needed
				for (BankAccount account : user.getAccounts()) {
					writer.write(user.getUsername() + "," + user.getPassword()+"," +account.getAccountName() + "," + account.getAccountNumber() + "," + account.getCurrentBalance() + "\n");
				}
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
		if (parts.length != 5) {
			throw new IllegalArgumentException("Invalid account info format");
		}
		String Username = parts[0];
		String password = parts[1];
		String AccountName = parts[2];
		int accountNumber = Integer.parseInt(parts[3]);
		double balance = Double.parseDouble(parts[4]);

		List<String> usernames = new ArrayList<>(); // List to check if the user already exists
		User currentUser = null;
		for(User user : this.users){
			usernames.add(user.getUsername());
		}
		
		if (!usernames.contains(Username)) {
			currentUser = new User(Username,password);
			this.users.add(currentUser); // Add new user to the list
		}else{
			for(User user : this.users){
				if(user.getUsername().equals(Username)){
					currentUser = user; // Get the existing user
				}
			}
		}

		BankAccount account = new BankAccount(AccountName);
		account.deposit(balance); //use deposit method to set the initial balance
		addAccount(account);
		currentUser.addAccount(account); // Add the account to the user
	}

	public void removeAccount(BankAccount account) {
		if (!this.accounts.contains(account)) {
            throw new IllegalArgumentException("Account does not exist");
            }
		this.accounts.remove(account);
		saveAccountsToFile();
	}
	
	public List<BankAccount> getAccounts() {
		return this.accounts;
	}

	public List<User> getUsers(){
		return this.users;
	}
}
