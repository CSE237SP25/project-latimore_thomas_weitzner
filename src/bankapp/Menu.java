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
		System.out.println("i.) Change username");
		System.out.println("j.) Change password");
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
				changeUsername();
				break;
			case "j":
				changePassword();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
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
					System.out.println("Invalid deposit amount. Please enter a deposit amount greater than or equal to 0.");
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
	
	public void removeAccount() {
		System.out.println("Are you sure you want to remove your account? (y/n)");
		String userInput = getUserInput();
		if (userInput.equals("n")) {
			System.out.println("Account removal cancelled");
			return;
		}
		if (userInput.equals("y")) {
			try {
				bank.removeAccount(userAccount);
				System.out.println("Your account has been removed");
			} catch (IllegalArgumentException e) {
				System.out.println("Account does not exist");
			}
		}
	}
	
	public void withdraw(){
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
				return false;
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
	
	public void changeUsername() {
		System.out.println("Enter Current Password");
		if(getUserInput().equals(user.getPassword())) {
			System.out.println("Enter new Username");
			String newUsername = getUserInput();
			if(login.searchForProfile(newUsername) != null) {
				System.out.println("Username is already taken");
			}
			else {
				if(validInput(newUsername)) {
					int index =login.existingUsers.indexOf(user);
					user.changeUsername(newUsername);
					login.existingUsers.remove(index);
					login.existingUsers.add(index, user);
					System.out.println("Username successfully changed to: " + user.getUsername());
					//Update files (Will create a new issue for it)
				}
				else {
					System.out.println("Username must contain no special characters and be less than 16 characters.");
				}
			}
		}
		else {
			System.out.println("Password is not correct");
		}
	}
	
	public void changePassword() {
		System.out.println("Enter Current Password");
		if(getUserInput().equals(user.getPassword())) {
			System.out.println("Enter new Password");
			String newPassword = getUserInput();
			int index =login.existingUsers.indexOf(user);
			user.changePassword(newPassword);
			login.existingUsers.remove(index);
			login.existingUsers.add(index, user);
			System.out.println("Password successfully changed");
			//Update files (Will create new issue for it)
		}
		else {
		System.out.println("Password is not correct");
		}
	}
}
