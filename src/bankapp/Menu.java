package bankapp;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private Scanner inputScanner;
	private List<BankAccount> userAccounts;
	public BankAccount currentUserAccount;
	private final Bank bank;
	public User user;
	
	private LoginMenu login;
	private Boolean active = true;

	public enum InvalidNameReason {
		EMPTY,
		LONG,
		SAME_NAME,
		SPECIAL_CHARACTERS,
		NONE
	}


	public Menu(Bank bank, User user) {
		this.user = user;
	    this.inputScanner = new Scanner(System.in);
		this.bank = bank;// Initialize the bank object
		this.login = new LoginMenu(bank.getUsers(), bank.getTellers(), bank);
		checkUserHasAccounts(user);
		setCurrentUserAccount(user.getAccounts().get(0));
	    System.out.println("Hello! Welcome to our bank app!");
	
	}
	void setUser(User user) {
    this.user = user;
	}

	void setCurrentUserAccount(BankAccount acc) {
		this.currentUserAccount = acc;
	}
	
	public void operateMenu() {
		while(active) {
			if(user.getAccounts().isEmpty()) {
				provideEmptyChoices();
				active = processEmptyInput(getUserInput());
			}
			else {
				provideUserChoices();
				active = processUserInput(getUserInput());
			}
			
		}
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
    	System.out.println("(x) Logout");
	}
	
	public void provideEmptyChoices() {
		System.out.println();
		System.out.println(" -- Welcome to Bear Banks! -- ");
		System.out.println("Would you like to: ");
		System.out.println("(a) Create a new account");
		System.out.println("(b) Change username");
		System.out.println("(c) Change password");
    	System.out.println("(x) Logout");
		
	}

	public String getUserInput(){
		return this.inputScanner.nextLine();
	}

	public Boolean processUserInput(String userInput){
		switch(userInput.toLowerCase()){
			case "a":
				deposit();
				return true;
			case "b":
				withdraw();
				return true;
			case "c":
				displayBalance();
				return true;
			case "d":
				createAccount();
				return true;
			case "f":
				renameAccount();
				return true;
			case "e":
				displayAccounts();
				return true;
      		case "g":
				removeAccount();
				return true;
			case "h":
				viewTransactionHistory();
				return true;
			case "i":
				transferBetweenAccounts();
				return true;
			case "j":
				changeUsername();
				return true;
			case "k":
				changePassword();
				return true;
      		case "x":
				logout();
				return false;
			default:
				System.out.println("Invalid choice. Please try again.");
				return true;
		}
	}
	
	public boolean processEmptyInput(String userInput) {
		switch(userInput.toLowerCase()) {
		case "a":
			createAccount();
			return true;
		case "b":
			changeUsername();
			return true;
		case "c":
			changePassword();
			return true;
		case "x":
			logout();
			return false;
		default:
			System.out.println("Invalid choice. Please try again.");
			return true;
		}
	}
	
	private void displayBalance() {
		System.out.println("Your current balance is: " + currentUserAccount.getCurrentBalance());
  }

	private Boolean logout() {
		System.out.println("Logging out...");
		this.user = null;
		this.currentUserAccount = null;
		return false;
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
	

	
	

	public void checkUserHasAccounts(User user){
		if(user.getAccounts().isEmpty()) {
			System.out.println("You do not have any accounts. Making one for you!");
			createAccount();
		}
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
					user.changeUsername(newUsername);
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
			user.changePassword(newPassword);
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

	public LoginMenu getLogin() {
    return this.login;
	}

}

