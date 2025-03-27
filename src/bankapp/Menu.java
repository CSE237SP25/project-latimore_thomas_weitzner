package bankapp;
import java.util.Scanner;
import java.util.List;

public class Menu {

	private Scanner inputScanner;
	private BankAccount userAccount;
	private final Bank bank;
	private User user;
	private LoginMenu login;


	public static void main(String[] args) {
		Menu menu = new Menu();
		Boolean loggedIn = false;
		while(!loggedIn) {
			loggedIn = menu.loginInputChoices();
		}
		System.out.println("Welcome: " + menu.user.getUsername());
		while (true) { 
			menu.provideUserChoices();
			String userInput = menu.getUserInput();
			menu.processUserInput(userInput);
		}
	}

	
	public Menu() {
	    this.inputScanner = new Scanner(System.in);
		this.bank = new Bank();// Initialize the bank object
		this.login = new LoginMenu(bank.getUsers());
	    this.userAccount = new BankAccount("Placeholder Name");
	    this.user = new User("PlaceholderUsername", "PlaceholderPassword");
	    this.user.addAccount(userAccount);
		bank.addUser(user); // Add the user to the bank
	    System.out.println("Hello! Welcome to our bank app!");
	}

	public void provideUserChoices(){
		System.out.println("Would you like to: ");
		System.out.println("a.) Deposit money");
		System.out.println("b.) Withdraw money");
		System.out.println("c.) Check Balance");
		System.out.println("d.) Create a new account");
		System.out.println("f.) Rename an account"); //Choice e already used in issue #17
		System.out.println("g.) Remove an account");
		System.out.println("e.) Display all accounts");
		System.out.println("h.) View transaction history");
		System.out.println("i.) Logout");
	}

	public String getUserInput(){
		return this.inputScanner.nextLine();
	}

	public void processUserInput(String userInput){
		switch(userInput.toLowerCase()){
			case "a":
				deposit();
				break;
			case "b":
				withdraw();
				break;
			case "c":
				System.out.println("Your current balance is: " + userAccount.getCurrentBalance());
				break;
			case "d":
				createAccount();
				break;
			case "f":
				renameAccount();
				break;
			case "e":
				displayAccounts();
				break;
      		case "g":
				removeAccount();
				break;
			case "h":
				viewTransactionHistory();
				break;
			case "i":
                logout();
                break;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}
	
	private void logout() {
		System.out.println("Logging out...");
		this.user = null;
		this.userAccount = null;
		Boolean loggedIn = false;
		while (!loggedIn) {
			loggedIn = loginInputChoices();
		}
		this.userAccount = this.user.getAccounts().get(0);
		System.out.println("Welcome: " + user.getUsername());
		System.out.println();
	}
	
	private void displayAccounts() {
	    if (user == null) {
	        System.out.println("Error: No user is currently logged in!");
	        return;
	    }
	    System.out.println("Here are all of your accounts: ");
		for (BankAccount account : user.getAccounts()) {
			System.out.println("Account number: " + account.getAccountNumber());
			System.out.println("Account name: " + account.getAccountName());
			System.out.println("Balance: " + account.getCurrentBalance());
			System.out.println();
		}
	}

	public void deposit(){
		System.out.println("How much would you like to deposit?");
		boolean validDeposit = false;
		while (!validDeposit) { 
			try {
					double depositAmount = Double.parseDouble(getUserInput());
					userAccount.deposit(depositAmount);
					bank.saveAccountsToFile(); // Save the updated account info to file
					validDeposit = true;
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid deposit amount. Please enter a deposit amount greater than or equal to 0. You may only have at most $1.79*10^308 within a bank account at any given time.");
				}
			}
		System.out.println("Your new balance is: " + userAccount.getCurrentBalance());
	}
	
	public void createAccount() {
		this.userAccount = new BankAccount("Placeholder Name"); //NOTE: replace the name with the actual persons name from their profile
		bank.addAccount(userAccount); // Add the new account to the bank
		this.user.addAccount(userAccount);
		System.out.println("Your new account has been created");
		System.out.println("Your account number is: " + userAccount.getAccountNumber());
	}
	
	private BankAccount findAccount() {
	    System.out.println("Here are all of your accounts: ");
	    for (BankAccount account : user.getAccounts()) {
	        System.out.println("Account number: " + account.getAccountNumber() + ", Account name: " + account.getAccountName() + ", Balance: " + account.getCurrentBalance());
	    }
	    System.out.println("Enter the account number of the account you want to select:");
	    int input = Integer.parseInt(getUserInput());
	
	    BankAccount selectedAccount = null;
	    for (BankAccount account : user.getAccounts()) {
	        if (account.getAccountNumber() == input) {
	            selectedAccount = account;
	            break;
	        }
	    }
	
	    if (selectedAccount == null) {
	        System.out.println("Invalid account number. Transaction cancelled.");
	        findAccount();
	    }
	    return selectedAccount;
	}

	public void removeAccount() {
		if (user == null) {
			System.out.println("Error: No user is currently logged in!");
			return;
		}
		
		if (user.getAccounts().size() == 0) {
			System.out.println("You don't have any open accounts.");
			System.out.println();
			return;
		}
		
	    BankAccount accountToRemove = findAccount();
		
	    System.out.println("Are you sure you want to remove the account: " + accountToRemove.getAccountName() + "? (y/n)");
	    String userInput = getUserInput();
	    if (userInput.equals("n")) {
	        System.out.println("Account removal cancelled.");
	        return;
	    }
	    if (userInput.equals("y")) {
	        try {
	            user.removeAccount(accountToRemove);
	            bank.removeAccount(accountToRemove);
	            System.out.println("Your account has been removed.");
	        } catch (IllegalArgumentException e) {
	            System.out.println("Account does not exist.");
	        }
	    }
	}


	
	public void withdraw(){
		userAccount = findAccount();
		System.out.println("How much would you like to withdraw?");
		boolean validWithdraw = false;
		while (!validWithdraw) { 
			try {
					double withdrawAmount = Double.parseDouble(getUserInput());
					userAccount.withdraw(withdrawAmount);
					bank.saveAccountsToFile(); // Save the updated account info to file
					validWithdraw = true;
				} catch (IllegalArgumentException e) {
					double currentBalance = userAccount.getCurrentBalance();
					System.out.println("Invalid withdrawal amount. Please enter a positive withdrawal amount less than or equal to your current balance of: $" + currentBalance);
				}
			}

		System.out.println("Your new balance is: " + userAccount.getCurrentBalance());
	}
	
	public void renameAccount() {
		System.out.println("What would you like to rename your account to? [Must not contain special characters]");
		String newName = getUserInput();
		if (newName.equals("")) {
			System.out.println("Invalid account name. Please enter a non-empty name.");
			renameAccount();
			return;
		}
		if (newName.equals(userAccount.getAccountName())) {
            System.out.println("Your account name is already " + newName);
            return;
        }
		if (newName.length() > 25) {
            System.out.println("Invalid account name. Please enter a name with 25 characters or less.");
            renameAccount();
            return;
        }
		if (newName.contains("(") || 
				newName.contains(")") || 
				newName.contains(",") || 
				newName.contains(";") ||
				newName.contains(":") ||
				newName.contains("[") ||
				newName.contains("]") ||
				newName.contains("{") ||
				newName.contains("}") ||
				newName.contains("<") ||
				newName.contains(">") ||
				newName.contains("=") ||
				newName.contains("?") ||
				newName.contains("!") ||
				newName.contains("@") ||
				newName.contains("#") ||
				newName.contains("$") ||
				newName.contains("%") ||
				newName.contains("^") ||
				newName.contains("*") ||
				newName.contains("+") ||
				newName.contains("/") ||
				newName.contains("`") ||
				newName.contains("~")) 
		{
			System.out.println("Invalid account name. Please enter a name without special charachters.");
			renameAccount();
			return;
		}
		userAccount.setAccountHolderName(newName);
		System.out.println("Your account has been renamed to: " + newName);
	}
	
	//Login Code
	public Boolean loginInputChoices() {
		login.displayChoices();
		String userInput = getUserInput();
		switch(userInput.toLowerCase()) {
			case "a":
				return loginToAccount();
			case "b":
				return createProfile();
			default:
				System.out.println("Invalid Input");
				return false;
				
		}
		
	}
	
	public Boolean loginToAccount() {
		System.out.println("Enter Username:");
		String username = getUserInput();
		if(login.searchForProfile(username) == null) {
			System.out.println("Profile does not exist");
			return false;
		}
		else {
			User profile = login.searchForProfile(username);
			System.out.println("Enter Password:");
			String password = getUserInput();
			Boolean correctPassword = login.checkPassword(profile, password);
			if(correctPassword) {
				this.user = profile;
				return true;
			}
			System.out.println("Passwords do not match");
			return false;
		}
	}
	
	//creating new Profile
	public Boolean createProfile(){
		System.out.println("Welcome! Let's get you set up with a profile!");
		System.out.println("Enter a username: ");
		String username = getUserInput();
		if(login.searchForProfile(username) != null) {
			System.out.println("Username already exists");
			System.out.println("Cancelling account creation...");
			return false;
		}
		else if(username.isBlank() || username.length()>15) {
			System.out.println("Invalid Username");
			System.out.println("Cancelling account creation...");
			return false;
		}
		System.out.println("Enter a password:");
		String password = getUserInput();
		if(password.isBlank()) {
			System.out.println("Password may not be empty");
			System.out.println("Cancelling account creation...");
			return false;
		}
		System.out.println("Please confirm the information below is correct (y/n)");
		System.out.println("Username: " + username + " Password: " + password);
		if(getUserInput().toLowerCase().equals("y")){
			System.out.println("Your profile has been created! Logging you in...");
			User newUser = new User(username,password);
			this.user = newUser;
			BankAccount newAccount = new BankAccount(newUser.getUsername() + " Account");
			newUser.addAccount(newAccount);
			bank.addUser(newUser);
			
			return true;
		}
		else {
			System.out.println("Cancelling account creation...");
		}
		return false;
	}


	public void viewTransactionHistory() {
		if (userAccount == null){
			System.out.println("No account selected!");
			return;
		}

		List<String> history = userAccount.getTransactionHistory();
		if (history.isEmpty()){
			System.out.println("No transactions yet!");
			return;
		}

		System.out.println("\n -- Transaction History -- \n");
		for  (String transaction : history){
			System.out.println(transaction);
		}
	}
}
