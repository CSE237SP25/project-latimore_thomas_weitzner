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
	private List<BankAccount> accounts;
	private List<String> accountInfoList = new ArrayList<>(); // List to hold account info strings
	private List<User> users = new ArrayList<>();// List to hold users (if needed)
	private String bankFilePath;
	private List<Teller> tellers = new ArrayList<>();
	
	
	public Bank() {
		tellers.add(new Teller("Teller1", "Password1"));
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

	public void saveAccountsToFile() {
		try (FileWriter writer = new FileWriter(bankFilePath)) {
			for(User user : users) {
                String userInfo = String.join(',' + "," + user.getPassword() + "," +
                        user.getName() + "," + user.getPhone() + "," +user.getEmail() + "," +
                        user.getAddress() + "," + user.getSsn() + "," + user.getTshirtSize() + "\n");
                if(user.getAccounts().isEmpty()) {
                    writer.write(user.getUsername() + "," + user.getPassword() + ",EMPTY,0,0.0\n");
                } else {
                    for (BankAccount account : user.getAccounts()) {
                        writer.write(userInfo + "," +
                                    account.getAccountName() + "," + account.getAccountNumber() + "," +
                                    account.getCurrentBalance() + "\n");
                    }
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
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid account info format");
        }//can be between 5-10
        String username = parts[0];
        String password = parts[1];

        User currentUser = initializeUser(username, password);

        if (parts.length >= 8){
            currentUser.setName(parts[2]);
            currentUser.setPhone(parts[3]);
            currentUser.setEmail(parts[4]);
            currentUser.setAddress(parts[5]);
            currentUser.setSsn(parts[6]);
            currentUser.setTshirtSize(parts[7]);
        }
        if (parts.length >= 11 && !parts[8].equals("EMPTY")) {
            String accountName = parts[8];
            int accountNumber = Integer.parseInt(parts[9]);
            double balance = Double.parseDouble(parts[10]);
            BankAccount account = createAccount(accountName, accountNumber, balance);
            currentUser.addAccount(account);
        } else if (parts.length == 5) {
            BankAccount account = createAccount("Default Account", 0, 0.0);
            currentUser.addAccount(account);
        } else {
            System.out.println("No accounts found for user: " + username);

        }
        
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
		BankAccount account = new BankAccount(accountName);
		account.initializeAccountBalance(balance);
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
