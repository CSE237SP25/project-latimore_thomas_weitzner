package bankapp;

import bankapp.Menu.InvalidNameReason;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
	private String username;
	private String password;
	private ArrayList<BankAccount> accounts;
	private Scanner inputScanner;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		accounts = new ArrayList<BankAccount>();
		this.inputScanner = new Scanner(System.in);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public ArrayList<BankAccount> getAccounts(){
		return accounts;
	} 
	
	public void changePassword(String newPassword) {
		this.password = newPassword;
	}
	
	public void changeUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void addAccount(BankAccount account) {
		if(InvalidNameReason.NONE != isInvalidAccountName(account.getAccountName()) || account.getAccountHolderName().equals("Unnamed Account")) {
			System.out.println("Enter a name for the new account:");
			String newAccountName = this.inputScanner.nextLine();
			account.setAccountHolderName(newAccountName);
		}
		if(isInvalidAccountName(account.getAccountName()) != InvalidNameReason.NONE) {
			System.out.println("Invalid account name. Please try again.");
			addAccount(account);
			return;
		}
		accounts.add(account);
	}
	
	public void removeAccount(BankAccount account) {
		accounts.remove(account);
	}
	
	public InvalidNameReason isInvalidAccountName(String name) {
		if(name.isEmpty()){
			System.out.println("Username or account name may not be empty.");
			return InvalidNameReason.EMPTY;
		}
		if(name.length() > 25) {
			System.out.println("Username or account name must be less than 25 characters.");
			return InvalidNameReason.LONG;
		}
		for (BankAccount existingAccount : accounts) {
			if (existingAccount.getAccountName().equals(name)) {
				System.out.println("New username or account name must be different from any other account names.");
				return InvalidNameReason.SAME_NAME;
			}
		}
		String specialCharacters = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
		for (char c : specialCharacters.toCharArray()) {
			if (name.contains(String.valueOf(c))) {
				System.out.println("Username or account name must contain no special characters.");
                return InvalidNameReason.SPECIAL_CHARACTERS;
            }
		}
        return InvalidNameReason.NONE;
	}
	
}