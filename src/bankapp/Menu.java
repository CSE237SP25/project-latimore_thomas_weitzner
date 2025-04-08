package bankapp;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private Scanner inputScanner;
	private List<BankAccount> userAccounts;
	private BankAccount currentUserAccount;
	private final Bank bank;
	private User user;
	private Teller teller;
	private Boolean isTeller = false;
	private LoginMenu login;
	private enum InvalidNameReason {
		EMPTY,
		LONG,
		SAME_NAME,
		SPECIAL_CHARACTERS,
		NONE
	}


	public static void main(String[] args) {
		Menu menu = new Menu();
		Boolean loggedIn = false;
		while(!loggedIn) {
			loggedIn = menu.loginInputChoices();
		}
		if (menu.isTeller) {
			TellerMenu tellerMenu = new TellerMenu(menu.bank, menu.teller);
			tellerMenu.operateMenu();
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

		this.login = new LoginMenu(bank.getUsers(), bank.getTellers());
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
		System.out.println("(i) Make a transfer between accounts");
		System.out.println("(j) Change username");
		System.out.println("(k) Change password");
    	System.out.println("(X) Logout");
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
				displayBalance();
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
				transferBetweenAccounts();
				break;
			case "j":
				changeUsername();
				break;
			case "k":
				changePassword();
				break;
      		case "x":
				logout();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}
	
	private void displayBalance() {
		System.out.println("Your current balance is: " + currentUserAccount.getCurrentBalance());
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
			System.out.println("Account number: " + account.getAccountNumber() + ", Account name: " + account.getAccountName() + ", Balance: " + account.getCurrentBalance());
		}
		System.out.println();
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
		this.currentUserAccount = new BankAccount("Unnamed Account"); //NOTE: replace the name with the actual persons name from their profile
		bank.addAccount(currentUserAccount); // Add the new account to the bank
		this.user.addAccount(currentUserAccount);
		System.out.println("Your new account has been created");
		System.out.println("Your account number is: " + currentUserAccount.getAccountNumber());
	}
	
	private BankAccount findAccount() {
	    displayAccounts();
	    System.out.println("Enter the account number of the account you want to select:");
		boolean validAccountNumber=false;
		int input=0;
		while(!validAccountNumber) {
			try {	
	    		input = Integer.parseInt(getUserInput());
				validAccountNumber = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid account number. Please enter a valid account number.");
				displayAccounts();
			}
		}
	
	    BankAccount selectedAccount = null;
	    for (BankAccount account : user.getAccounts()) {
	        if (account.getAccountNumber() == input) {
	            selectedAccount = account;
	            break;
	        }
	    }
	
	    if (selectedAccount == null) {
	        System.out.println("Invalid account number. Please enter a valid account number.");
	        selectedAccount=findAccount();
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
		if (isInvalidAccountName(newName)!=InvalidNameReason.NONE) {
			renameAccount();
			return;
		}
		userAccount.setAccountHolderName(newName);
		bank.saveAccountsToFile(); // Save the updated account info to file
		System.out.println("Your account has been renamed to: " + newName);
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
		if(name.equals(this.currentUserAccount.getAccountName())) {
			System.out.println("New username or account name must be different from the current name.");
			return InvalidNameReason.SAME_NAME;
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
			case "c":
				return loginToTellerAccount();
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
			if(login.checkPassword(profile, password)) {
				this.user = profile;
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
	public Boolean loginToTellerAccount(){
		System.out.println("Enter Username:");
		String username = getUserInput();
		if(login.searchForTeller(username) == null) {
			System.out.println("Teller does not exist");
			return false;
		}
		else {
			Teller teller = login.searchForTeller(username);
			System.out.println("Enter Password:");
			String password = getUserInput();
			Boolean correctPassword = login.checkPassword(teller, password);
			if(correctPassword) {
				this.teller = teller;
				this.isTeller = true;
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
	
	
	public void changeUsername() {
		System.out.println("Enter Current Password");
		if(getUserInput().equals(user.getPassword())) {
			System.out.println("Enter new Username");
			String newUsername = getUserInput();
			if(login.searchForProfile(newUsername) != null) {
				System.out.println("Username is already taken");
			}
			else {
				if(isInvalidAccountName(newUsername)==InvalidNameReason.NONE) {
					int index =login.existingUsers.indexOf(user);
					user.changeUsername(newUsername);
					login.existingUsers.remove(index);
					login.existingUsers.add(index, user);
					System.out.println("Username successfully changed to: " + user.getUsername());
					bank.saveAccountsToFile(); // Save the updated account info to file
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

	public void transferBetweenAccounts() {
    if (user.getAccounts().size() < 2) {
        System.out.println("You need to have more than one account to transfer between");
        return;
    }
    
    try {
		System.out.println("Select the account you want to transfer from:");
		BankAccount userAccount = findAccount();
		this.currentUserAccount = userAccount;
        System.out.println("Your current account:");
        System.out.println("Account #"+userAccount.getAccountNumber()+ " Balance: "+ userAccount.getCurrentBalance());
        System.out.println("Accounts available: ");
		displayAccounts();
        
        System.out.print("\nEnter account number to transfer to: ");
        int targetAccountNum = Integer.parseInt(getUserInput());
        
        BankAccount targetAccount = null;
        for (BankAccount account : user.getAccounts()) {
            if (account.getAccountNumber() == targetAccountNum) {
                targetAccount = account;
                break;
            }
        }
        
        if (targetAccount == null) {
            System.out.println("Invalid account number");
            return;
        }
        
        if (targetAccount.getAccountNumber() == userAccount.getAccountNumber()) {
            System.out.println("Cannot transfer to the same account");
            return;
        }
        
        System.out.print("Enter amount to transfer: $");
        double amount = Double.parseDouble(getUserInput());
        
        try {
            userAccount.transfer(targetAccount, amount);
            bank.saveAccountsToFile();
            System.out.printf("\n✅ $%.2f successfully transferred to account #%d\n", 
                amount, targetAccountNum);
            System.out.printf("Your new balance: $%.2f\n", userAccount.getCurrentBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Transfer failed: " + e.getMessage());
        }
    } catch (NumberFormatException e) {
        System.out.println("❌ Please enter valid numbers");
    }
}
}