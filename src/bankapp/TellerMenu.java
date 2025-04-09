package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TellerMenu{
	private Teller teller;
	private Bank bank;
	private LoginMenu login;
	private Scanner inputScanner;
	private Boolean active;
	
	public TellerMenu(Bank bank, Teller teller) {
		this.bank = bank;
		this.teller = teller;
		login = new LoginMenu(bank.getUsers(), bank.getTellers());
		inputScanner = new Scanner(System.in);
		active = true;
	}
	
	
	public void operateMenu(){
		while(active) {
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
		System.out.println("b.) Create New Teller?");
    System.out.println("c.) Deposit into account");
		System.out.println("x.) Logout");
	}
	
	public void tellerProccessChoice(String userInput) {
		switch(userInput.toLowerCase()) {
		case "a":
			showcaseAccounts();
			break;
		case "b":
			createTeller();
      break;
    case "c":
		  depositIntoAccount();
      break;
		case "x":
			logout();
			break;
		default:
			System.out.println("Input Invalid!");
		}
		
	}
	
	public void logout() {
		System.out.println("Logging Out...");
		active = false;
		
	}
	
	public User showcaseAccounts() {
		System.out.println("Which user's account would you like to see?");
		List<User> users = bank.getUsers();
		for( User user : users) {
			System.out.println(user.getUsername());
		}
		System.out.print("Username of account holder:");
		String username = getUserInput();
		if(selectUser(username) == null) {
			System.out.println("Invalid Username");
			return null;
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
			return currentUser;
			
		}
	}
	public BankAccount searchForAccount(User user,int input) {
	  
	    for (BankAccount account : user.getAccounts()) {
	        if (account.getAccountNumber() == input) {
	            return account;
	        }
	    }
	    return null;
	}
	public void depositIntoAccount(){
		System.out.println("Select an account to deposit into");
		User user = showcaseAccounts();
		if(user == null || user.getAccounts().isEmpty()) {
			System.out.println("Cancelling deposit...");
			return;
		}
		System.out.println("Enter the account number of the account you want to select:");
		int input = 0;
		try {
			input = Integer.parseInt(getUserInput());
		}
		catch(NumberFormatException e){
			System.out.println("Invalid Account Number");
    		System.out.println("Cancelling deposit...");
			return;
			
		}
	    if(searchForAccount(user, input) ==  null) {
	    	System.out.println("Invalid Account number");
	    	System.out.println("Cancelling deposit...");
	    	System.out.println();
	    }
	    else{
	    	BankAccount account = searchForAccount(user,input);
	    	System.out.println("How much would you like to deposit?");
	    	double amount = 0;
	    	try {
	    		amount = Double.parseDouble(getUserInput());
	    	}
	    	catch(NumberFormatException e) {
	    		System.out.println("Invalid Amount");
	    		System.out.println("Cancelling deposit...");
	    		return;
	    	}
	    	
	    	if(amount <= 0) {
	    		System.out.println("Invalid Amount");
	    		System.out.println("Cancelling deposit...");
	    	}
	    	else {
	    		System.out.println("Deposit was succesful!");
	    		account.deposit(amount);
	    		bank.saveAccountsToFile();
	    		System.out.println("Your new balance is: " + account.getCurrentBalance());
	    	}
	    	
	    }
	    
	}

	public void createTeller(){
		System.out.println("Enter new teller username: ");
		String username = getUserInput();
		if(login.searchForTeller(username) != null) {
			System.out.println("Teller username already exists");
			System.out.println("Cancelling account creation...");
			return;
		}
		else if(!validInput(username)) {
			System.out.println("Invalid Username");
			System.out.println("Cancelling account creation...");
			return;
		}
		System.out.println("Enter a password:");
		String password = getUserInput();
		if(password.isBlank()) {
			System.out.println("Password may not be empty");
			System.out.println("Cancelling account creation...");
			return;
			
		}
		System.out.println("Please confirm the information below is correct (y/n)");
		System.out.println("Username: " + username + " Password: " + password);
		if(getUserInput().toLowerCase().equals("y")){
			System.out.println("New teller has been created");
			Teller newTeller = new Teller(username, password);
			bank.addTeller(newTeller);
		}
		
	}
	
	//valid Input will be moved to a different file next cycle. For now I will make a copy of it here for testing purposes.
	
	public boolean validInput(String input) {
		if(input.length() > 15) {
			System.out.println("Input is too long");
			return false;
		}
		else if(input.isBlank()){
			System.out.println("Input must not be blank");
			return false;
			
		}
		else if (input.contains("(") || 
				input.contains(")") || 
				input.contains(",") || 
				input.contains(";") ||
				input.contains(":") ||
				input.contains("[") ||
				input.contains("]") ||
				input.contains("{") ||
				input.contains("}") ||
				input.contains("<") ||
				input.contains(">") ||
				input.contains("=") ||
				input.contains("?") ||
				input.contains("!") ||
				input.contains("@") ||
				input.contains("#") ||
				input.contains("$") ||
				input.contains("%") ||
				input.contains("^") ||
				input.contains("*") ||
				input.contains("+") ||
				input.contains("/") ||
				input.contains("`") ||
				input.contains("~")) 
		{
			System.out.println("Input Contains Invalid Characters");
			return false;
		}
		else {
			return true;
		}
	}
}