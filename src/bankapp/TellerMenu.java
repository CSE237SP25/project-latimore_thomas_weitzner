package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TellerMenu{
	private Teller teller;
	private Bank bank;
	private LoginMenu login;
	private Scanner inputScanner;
	
	public TellerMenu(Bank bank, Teller teller) {
		this.bank = bank;
		this.teller = teller;
		login = new LoginMenu(bank.getUsers(), bank.getTellers());
		inputScanner = new Scanner(System.in);
	}
	
	
	public void operateMenu(){
		while(true) {
			tellerOption();
			String input = inputScanner.nextLine();
			tellerProccessChoice(input);
		}
		
	}
	
	public String getUserInput(){
		return inputScanner.nextLine();
		
	}
	
	public User selectUser(String username){
		return login.searchForProfile(username);
	}
	
	public void tellerOption() {
		System.out.println("Hello! Would you like to:");
		System.out.println("a.) View active bank accounts?");
	}
	
	public void tellerProccessChoice(String userInput) {
		switch(userInput.toLowerCase()) {
		case "a":
			showcaseAccounts();
			break;
		default:
			System.out.println("Input Invalid!");
		}
		
	}
	
	public void showcaseAccounts() {
		System.out.println("Which user's account would you like to see?");
		List<User> users = bank.getUsers();
		for( User user : users) {
			System.out.println(user.getUsername());
		}
		System.out.print("Username of account holder:");
		String username = getUserInput();
		if(selectUser(username) == null) {
			System.out.println("Invalid Username");
		}
		else {
			User currentUser = selectUser(username);
			
			ArrayList<BankAccount> accounts = currentUser.getAccounts();
			if(accounts.isEmpty()) {
				System.out.println();
				System.out.println("Profile holds no account");
				System.out.println();
			}
			for(BankAccount account : accounts) {
				System.out.println();
				System.out.println("Account number: " + account.getAccountNumber());
				System.out.println("Account name: " + account.getAccountName());
				System.out.println("Balance: " + account.getCurrentBalance());
				System.out.println("---------------------------------------------");
			}
		}
	}
	
}