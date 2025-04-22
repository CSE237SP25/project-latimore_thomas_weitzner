package bankapp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Bank {
	//NOTE: This is the entire bank, should only be ONE BANK OBJECT IN THE PROJECT
	//also note that accounts doesn't include the accounts of the tellers
	private List<BankAccount> accounts;
	private List<String> accountInfoList = new ArrayList<>(); // List to hold account info strings
	private List<User> users = new ArrayList<>();// List to hold users (if needed)
	private String bankFilePath;
	private List<Teller> tellers = new ArrayList<>();
	
	
	public Bank() {
		this.accounts = new ArrayList<>();
		this.bankFilePath = determineFilePath();
		this.accountInfoList = readAccountInfoFromFile();
		loadAccountsFromFile(); // Load accounts from file when the bank is created
	}

	public Bank(String filePath) {
		this.accounts = new ArrayList<>();
		this.bankFilePath = filePath; // Use provided file path for account info
		this.accountInfoList = readAccountInfoFromFile();
		loadAccountsFromFile(); // Load accounts from file when the bank is created
	}
	
	private String determineFilePath() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		String[] pathParts = s.split("[/\\\\]");
		String lastPart = pathParts[pathParts.length - 1];
		
		switch (lastPart) {
		case "bankapp":
			return "./bankResources/bankPastInfo.txt";
		case "src":
			return "./bankapp/bankResources/bankPastInfo.txt";
        case "project-latimore_thomas_weitzner":
        	return "./src/bankapp/bankResources/bankPastInfo.txt";
        default:
        	System.out.println("Please run the bankapp from the project-latimore_thomas_weitzner, bankapp, or src directories.");
        	System.out.println("The bankapp will not be able to save account information.");
        	return null;
		}
	}
	
	private List<String> readAccountInfoFromFile() {
		List<String> accountInfo = new ArrayList<>();
		if (bankFilePath != null) {
			try {
				accountInfo = Files.readAllLines(Paths.get(bankFilePath));
			} catch (IOException e) {
				System.out.println("Error reading account info file: " + e.getMessage());
			}
		}
		return accountInfo;
	}
	
	
	public void addAccount(BankAccount account) {
		for (BankAccount existingAccount : this.accounts) {
			if (existingAccount.getAccountName() == account.getAccountName()) {
				throw new IllegalArgumentException("Account Name already exists");
			}
		}
		if (isDuplicateAccountNumber(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }
		this.accounts.add(account);
		saveAccountsToFile(); // Save the updated account info to file
	}
	
	private boolean isDuplicateAccountNumber(int accountNumber) {
		for (BankAccount account : this.accounts) {
			if (account.getAccountNumber() == accountNumber) {
				return true;
			}
		}
		return false;
	}

	public void addUser(User user){
		this.users.add(user); // Add a new user to the list
		for (BankAccount account : user.getAccounts()) { // Add user's accounts to the bank
			addAccount(account);
		}
	}
	
	public void addTeller(Teller teller) {
		this.tellers.add(teller);
		saveAccountsToFile();
	}

	public void saveAccountsToFile() {
		try (FileWriter writer = new FileWriter(bankFilePath)) {
			for(User user : users) {
			    if(user.getAccounts().isEmpty()) {
			        writer.write("User,"+user.getUsername() + "," + user.getPassword() + ",EMPTY,0,0.0\n");
			    } else {
			        for (BankAccount account : user.getAccounts()) {
			            writer.write("User,"+user.getUsername() + "," + user.getPassword() + "," +
			                         account.getAccountName() + "," + account.getAccountNumber() + "," +
			                         account.getCurrentBalance());
			        }
			    }
				
				for (SecurityQuestion question : user.getSecurityQuestions()) {
					writer.write(","+"SecurityQuestion," + user.getUsername() + "," + question.getQuestion() + "," + question.getAnswer());
				}
				writer.write("\n");
			}
			int i=0;
			for(Teller teller:tellers) {
				if(i==0) {
			    	writer.write("Teller,"+teller.getUsername() + "," + teller.getPassword() + ",EMPTY,0,0.0");
				}else{
			    	writer.write("\nTeller,"+teller.getUsername() + "," + teller.getPassword() + ",EMPTY,0,0.0");
				}
				i++;
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
		if (parts.length < 6) {
			throw new IllegalArgumentException("Invalid account info format");
		}
		String type = parts[0];
		String username = parts[1];
		String password = parts[2];
		String accountName = parts[3];
		int accountNumber = Integer.parseInt(parts[4]);
		double balance = Double.parseDouble(parts[5]);
		if("Teller".equals(type)) {
			Teller teller = new Teller(username, password);
			addTeller(teller);
			return;
		}else if("User".equals(type)) {
			User currentUser = initializeUser(username, password);
			if (!accountName.equals("EMPTY")) {
				BankAccount account = createAccount(accountName, accountNumber, balance);
				currentUser.addAccount(account);
			}

			for (int i = 6; i < parts.length; i += 2) {
				if (i + 1 < parts.length) {
					String question = parts[i];
					String answer = parts[i + 1];
					currentUser.addSecurityQuestions(question, answer);
				}
			}
		}
		saveAccountsToFile();
	}
	
	private User initializeUser(String username, String password) {
		for (User user : this.users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		User newUser = new User(username, password);
		users.add(newUser);
		return newUser;
	}
	
	private BankAccount createAccount(String accountName, int accountNumber, double balance) {
		BankAccount account = new BankAccount(accountName,accountNumber,balance);
		//account.initializeAccountBalance(balance);
		addAccount(account);
		return account;
	}

	public void removeAccount(BankAccount account) {
		if (!this.accounts.contains(account)) {
            throw new IllegalArgumentException("Account does not exist");
        }
		this.accounts.remove(account);
		saveAccountsToFile();
	}

	public List<BankAccount> getUserAccounts(User user) {
		List<BankAccount> userAccounts = new ArrayList<>();
		for (User tempUser : users) {
			if (tempUser.getUsername().equals(user.getUsername())) {
				userAccounts = user.getAccounts();
			}
		}
		return userAccounts;
	}
	
	public List<BankAccount> getAccounts() {
		return this.accounts;
	}

	public List<User> getUsers(){
		return this.users;
	}

	
	public List<Teller> getTellers(){
		return this.tellers;
	}

}
