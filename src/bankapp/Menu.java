package bankapp;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private Scanner inputScanner;
	private List<BankAccount> userAccounts;
	private BankAccount currentUserAccount;
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
	    System.out.println("Hello! Welcome to our bank app!");
	}

	public void provideUserChoices(){
		System.out.println();
		System.out.println(" -- Welcome to Bear Banks! -- ");
		System.out.println("Would you like to: ");
		System.out.println("(a) Deposit money");
		System.out.println("(b) Withdraw money");
		System.out.println("(c) Check Balance");
		System.out.println("(d) Create a new account");
		System.out.println("(e) Display all accounts");
		System.out.println("(f) Rename an account"); //Choice e already used in issue #17
		System.out.println("(g) Remove an account");
		System.out.println("(h) View transaction history");
	    System.out.println("(i) Logout");
	    System.out.println("(j) Change username");
	    System.out.println("(k) Change password");
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
        //if we want to entirely get rid of the currentUserAccount setup then this func would basically just be same as e so should have one or the other
        //but it's still also an option to keep the setup we have rn
				System.out.println("Your current balance is: " + currentUserAccount.getCurrentBalance());
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
			case "j":
				changeUsername();
				break;
			case "k":
				changePassword();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}
	
	private void logout() {
		System.out.println("Logging out...");
		this.user = null;
		this.currentUserAccount = null;
		Boolean loggedIn = false;
		while (!loggedIn) {
			loggedIn = loginInputChoices();
		}
		this.currentUserAccount = this.user.getAccounts().get(0);
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
		BankAccount userAccount = findAccount();
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
		this.currentUserAccount = new BankAccount("Placeholder Name"); //NOTE: replace the name with the actual persons name from their profile
		bank.addAccount(currentUserAccount); // Add the new account to the bank
		this.user.addAccount(currentUserAccount);
		System.out.println("Your new account has been created");
		System.out.println("Your account number is: " + currentUserAccount.getAccountNumber());
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
		this.currentUserAccount = selectedAccount;
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
		BankAccount userAccount = findAccount();
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
		BankAccount userAccount = findAccount();
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
		bank.saveAccountsToFile(); // Save the updated account info to file
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
				System.out.println("Creating a new account...");
				return createProfile();
			default:
				System.out.println("Invalid Input");
				return false;
		}
		
	}
	
	public Boolean loginToAccount() {
		System.out.println("Enter Username:");
		String username = getUserInput();
		User profile = login.searchForProfile(username);
		if(profile == null) {
			System.out.println("Profile does not exist");
			return false;
		}
		else {
			System.out.println("Enter Password:");
			String password = getUserInput();
			Boolean correctPassword = login.checkPassword(profile, password);
			if(correctPassword) {
				this.user = profile;
				System.out.println("Login Successful");
				if(checkUserHasAccounts(user)){
					this.currentUserAccount = user.getAccounts().get(0);
					this.userAccounts = user.getAccounts();
				}
				return true;
			}
			System.out.println("Passwords do not match");
			return false;
		}
	}
	
	public Boolean checkUserHasAccounts(User user){
		if(user.getAccounts().isEmpty()) {
			System.out.println("You do not have any accounts. Making one for you!");
			createAccount();
			return true;
		}
		return true;
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
			this.currentUserAccount = newAccount;
			return true;
		}
		else {
			System.out.println("Cancelling account creation...");
		}
		return false;
	}


	public void viewTransactionHistory() {
		BankAccount userAccount = findAccount();
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
					bank.saveAccountsToFile(); // Save the updated account info to file
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
			bank.saveAccountsToFile(); // Save the updated account info to file
		}
		else {
		System.out.println("Password is not correct");
		}
	}
}
