package bankapp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class Menu {

	private Scanner inputScanner;
	public BankAccount currentUserAccount;
	private final Bank bank;
	public User user;
	
	
	private Boolean active = true;

	public Menu(Bank bank, User user) {
		this.user = user;
	    this.inputScanner = new Scanner(System.in);
		this.bank = bank;// Initialize the bank object
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
	
	//Providing the user with choices
	
	public void provideUserChoices(){
		System.out.println();
		System.out.println(" -- Welcome to Bear Banks! -- ");
		System.out.println("Would you like to: ");
		System.out.println("(a) Deposit money");
		System.out.println("(b) Withdraw money");
		System.out.println("(c) Check Balance");
		System.out.println("(d) Create a new account");
		System.out.println("(e) Display all accounts");
		System.out.println("(f) Rename an account");
		System.out.println("(g) Remove an account");
		System.out.println("(h) View transaction history");
		System.out.println("(i) Make a transfer between accounts");
		System.out.println("(j) " + user.getUsername() + " profile options");
		System.out.println("(m) View Todays Rates");
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
	
	public void provideProfileChoices() {
		System.out.println();
		System.out.println("-- " + user.getUsername() + " profile --");
		System.out.println("Would you like to: ");
		System.out.println("(a) Change username");
		System.out.println("(b) Change password");
		System.out.println("(c) Update profile information");
		System.out.println("(x) Back");
	}

	//Getting user input
	
	public String getUserInput(){
		return this.inputScanner.nextLine();
	}
	
	//processing user choices depending on which set of choices they are selecting from

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
				boolean userProfile = true;
				while(userProfile) {
					provideProfileChoices();
					userProfile = processProfileInput(getUserInput());
				}
				return true;
			case "m":
				viewRates();
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
	
	public boolean processProfileInput(String userInput) {
		switch(userInput.toLowerCase()) {
			case "a":
				changeUsername();
				return true;
			case "b":
				changePassword();
				return true;
			case "c":
				updateProfile();
				return true;
			case "x":
				return false;
		
			default:
				System.out.println("Invalid choice. Please try again.");
				return true;
		}
	}
	
	//deposit money into a user's account
	
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
	
	//withdrawing money from a users account;
	
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
					System.out.println(e.getMessage());
				}
			}

		System.out.println("Your new balance is: " + userAccount.getCurrentBalance());
	}
	//Logout code
	
	private Boolean logout() {
		System.out.println("Logging out...");
		this.user = null;
		this.currentUserAccount = null;
		return false;
	 }
	
	//displaying balance of current account
	
	private void displayBalance() {
		System.out.println("Your current balance is: " + currentUserAccount.getCurrentBalance());
	 }

	// creating a new account
	
	public void createAccount() {
		BankAccount newAccount = selectAccountType();
		if(newAccount !=null){
			this.currentUserAccount = newAccount;
			bank.addAccount(currentUserAccount); // Add the new account to the bank
			this.user.addAccount(currentUserAccount);
			System.out.println("Your new account has been created");
			System.out.println("Your account number is: " + currentUserAccount.getAccountNumber());
		}else{
			System.out.println("Account creation failed. Please try again.");
		}
	}
	
	//helper function to find a certain account;
	
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
	
	//displaying all accounts a user has
	
	private void displayAccounts() {
	    if (user == null) {
	        System.out.println("Error: No user is currently logged in!");
	        return;
	    }
	    System.out.println("Here are all of your accounts: ");
		for (BankAccount account : user.getAccounts()) {
			System.out.println("Account number: " + account.getAccountNumber() +", Account type: "+account.getAccountType() +", Account name: " + account.getAccountName() + ", Balance: " + account.getCurrentBalance());
		}
		System.out.println();
	}
	//renaming an existing account;
	
	public void renameAccount() {
		BankAccount userAccount = findAccount();
		System.out.println("What would you like to rename your account to? [Must not contain special characters]");
		String newName = getUserInput();
		if (BankUtils.isInvalidAccountName(newName, currentUserAccount.getAccountName())!=BankUtils.InvalidNameReason.NONE) {
			renameAccount();
			return;
		}
		userAccount.setAccountHolderName(newName);
		bank.saveAccountsToFile(); // Save the updated account info to file
		System.out.println("Your account has been renamed to: " + newName);
	}
	
	//removing an account

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

	
	//helper function to see if a user has an account;

	public void checkUserHasAccounts(User user){
		if(user.getAccounts().isEmpty()) {
			System.out.println("You do not have any accounts. Making one for you!");
			createAccount();
		}
	}

	//shows a user's account transaction history

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
	
	//changes a user's username
	public void changeUsername() {
		System.out.println("Enter Current Password");
		if(getUserInput().equals(user.getPassword())) {
			System.out.println("Enter new Username");
			String newUsername = getUserInput();
			if(BankUtils.searchForProfile(newUsername, bank.getUsers()) != null) {
				System.out.println("Username is already taken");
			}
			else {
				if(BankUtils.isInvalidAccountName(newUsername, currentUserAccount.getAccountName())== BankUtils.InvalidNameReason.NONE) {
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
	
	//changes a user's password
	
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
	
	//transfers money 

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
	
	//Selecting a type of account user would like to make

	public BankAccount selectAccountType(){
		System.out.println("What type of account do you wish to create?");
		System.out.println("(a) Checkings Account");
		System.out.println("(b) Savings Account");
		System.out.println("(c) Money Market Account");
		String response = getUserInput();
		switch(response) {
			case "a":
				return new CheckingsAccount("Unnamed Account"); //NOTE: replace the name with the actual persons name from their profile
			case "b":
				try {
					return new SavingsAccount("Unnamed Account"); //NOTE: replace the name with the actual persons name from their profile
				} catch (Exception e) {
					System.out.println("Error creating Savings Account: " + e.getMessage());
					return null;
				}
			case "c":
				try {
					return new MoneyMarketAccount("Unnamed Account"); //NOTE: replace the name with the actual persons name from their profile
				} catch (Exception e) {
					System.out.println("Error creating Money Market Account: " + e.getMessage());
					return null;
				}
			default:
				System.out.println("Invalid choice. Please try again.");
				return selectAccountType();
		}
	}
	//updating a users profile information
	public void updateProfile() {
		if (user == null) {
			System.out.println("No user logged in!");
			return;
		}
		System.out.println("\n-- Update Profile --");
		System.out.println("Current Information:");
		displayUsersCurrentProfile();
		System.out.println("\nEnter your current password to continue:");
		if (!getUserInput().equals(user.getPassword())) {
			System.out.println("Incorrect password!");
			return;
		}
		boolean updated = false;
		boolean cancelled = false;
		while (!cancelled) {
			displayUserProfileChoices();
			String choice = getUserInput().toLowerCase();
        
			switch (choice) {
			case "a":
					updated = updateName();
					break;              
            	case "b":
            		updated = updatePhone();
            		break;
            	case "c":
            		updated = updateEmail();
            		break;
            	case "d":
            		updated = updateAddress();
            		break;
            	case "e":
            		updated = updateSSN();
            		break;
            	case "f":
            		if (updated) {
            			bank.saveAccountsToFile();
            			bank.saveCustomerInfoToFile();
            			System.out.println("Changes saved!");
            		}
            		return;
            	case "x":
            		cancelled = true;
            		updated = false;
            		break;
            
            	default:
            		System.out.println("Invalid choice. Please try again.");
			}
		}

		if (!updated) {
			System.out.println("Canceling Profile Update.");
		} 
		else {
			System.out.println("Profile updated successfully!");
			System.out.println("Updated Information:");
			displayUsersCurrentProfile();
			}
		} 
	
	//Displaying user profile items
	public void displayUsersCurrentProfile(){
		System.out.println("1. Name: " + user.getName());
		System.out.println("2. Phone: " + user.getPhone());
		System.out.println("3. Email: " + user.getEmail());
		System.out.println("4. Address: " + user.getAddress());
		System.out.println("5. SSN: " + user.getMaskedSsn());
	}
	
	public void displayUserProfileChoices(){
		System.out.println("Current Information:");
		System.out.println("Name: " + user.getName());
		System.out.println("Phone: " + user.getPhone());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Address: " + user.getAddress());
		System.out.println("SSN: " + user.getMaskedSsn());
		System.out.println("\nWhat would you like to update? (Enter number)");
		System.out.println("a. Name");
		System.out.println("b. Phone");
		System.out.println("c. Email");
		System.out.println("d. Address");
		System.out.println("e. SSN");
		System.out.println("f. Save changes and return");
		System.out.println("x. Cancel without saving");
	}
	
	//helper functions for update profile
	public boolean updateName() {
		 System.out.println("Enter new name:");
         String newName = getUserInput().trim();
         if (BankUtils.isInvalidAccountName(newName, " ") == BankUtils.InvalidNameReason.NONE) {
             user.setName(newName);
             System.out.println("Name updated.");
             return true;
             
         } 
         return false;
         
	}
	
	public boolean updatePhone(){
		boolean validPhone = false;
		while (!validPhone) {
			System.out.println("Enter new phone number:");
			String phoneInput = getUserInput().trim();
			String digitsOnly = phoneInput.replaceAll("-", "");
			if (digitsOnly.matches("\\d{10}")) {
				user.setFormattedPhone(phoneInput);
				System.out.println("Phone updated to: " + user.getPhone());
				validPhone = true;
				return true;
			} else {
				System.out.println("Invalid phone number. Try again.");
			}
		}			
		return false;
	}
	
	public boolean updateEmail(){
		boolean validEmail = false;
		while (!validEmail) {
			System.out.println("enter new email:");
			String emailInput = getUserInput().trim();
			try {
				user.setEmail(emailInput);
				validEmail = true;
				System.out.println("email updated successfully.");
				return true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid email format. Please include:");
				System.out.println("1. an @ symbol");
				System.out.println("2. a valid domain (something.com)");
				System.out.println("- no spaces or special characters except ._-+");
				System.out.println("please try again.");
			}
		}
		return false;
	}
	
	public boolean updateAddress() {
		System.out.println("Enter new address:");
        String newAddress = getUserInput().trim();
        if (!newAddress.isEmpty()) {
            user.setAddress(newAddress);
            System.out.println("Address updated.");
            return true;
        }
        return true;
	}
	
	public boolean updateSSN(){
		boolean validSSN = false;
		while (!validSSN) {
			System.out.println("Enter new SSN (9 digits):");
			String newSSN = getUserInput().trim();
			if (user.isValidSsn(newSSN)) {
				user.setSsn(newSSN);
				System.out.println("SSN updated.");
				validSSN = true;
				return true;
			} else {
				System.out.println("Invalid SSN. Must be exactly 9 digits. Try again.");
			}
		}
		return false;
	}

	//displays the rates of the day
	public void viewRates() {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	String date = LocalDateTime.now().format(dtf);
	System.out.println("=== Account Rates Summary for " + date + " ===\n");
	System.out.println("Account Type           | Checking (Default)| Savings          | Money Market");
	System.out.println("------------------------+--------------------+------------------+----------------");
	System.out.println("Interest Rate (%)      | 0.0               | 0.001            | 0.5 ");
	System.out.println("Min. Opening Balance   | $0                | $100             | $1,000\n");
	System.out.println();
}

	

}