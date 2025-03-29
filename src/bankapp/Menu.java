package bankapp;
import java.util.Scanner;
import java.util.List;

public class Menu {

	private Scanner inputScanner;
	private BankAccount userAccount;
	private final Bank bank;
	private User user;


	public static void main(String[] args) {
		Menu menu = new Menu();
		while (true) { 
			menu.provideUserChoices();
			String userInput = menu.getUserInput();
			menu.processUserInput(userInput);
		}
	}

	
	public Menu() {
	    this.inputScanner = new Scanner(System.in);
		this.bank = new Bank(); // Initialize the bank object
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
		System.out.println("i.) Make a transfer between accounts");
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
				transferBetweenAccounts();
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

	public void viewTransactionHistory(){
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

	public void transferBetweenAccounts(){
		if (user.getAccounts().size() < 2){
			System.out.println("You need to have more than one account to transfer between");
			return;
		}
		System.out.println("Your current account:");
		System.out.println("Account #"+userAccount.getAccountNumber()+ " Balance: "+ userAccount.getCurrentBalance());
		System.out.println("Accounts avaliable: ");
		for (BankAccount account : user.getAccounts()){
			if (account.getAccountNumber() != userAccount.getAccountNumber()){
				System.out.println("Account #" + account.getAccountNumber()+ " - Balance: $" + account.getCurrentBalance());
			}
		}
		
		try{
			System.out.print("\nEnter account number to transfer to: ");
			int targetAccount = Integer.parseInt(getUserInput());

			BankAccount target = null;
			for (BankAccount account: user.getAccounts()){
				if(account.getAccountNumber() == targetAccount){
					target = account;
					break;
				}
			}
			if (target.getAccountNumber() == userAccount.getAccountNumber()) {
				System.out.println("Error: Cannot transfer to same account");
				return;
			}
			if (target == null){
				System.out.println("Target account does not exist.");
				return;
			}
			System.out.print("Enter an amount to transfer: $");
			double amount = Double.parseDouble(getUserInput());
			userAccount.transfer(target, amount);
			bank.saveAccountsToFile();
			System.out.printf("\n$%.2f has been transferred to account #%d\n", amount, targetAccount);
			System.out.printf("Your new balance is: $%.2f\n", userAccount.getCurrentBalance());
			System.out.printf("Account #%d new balance: $%.2f\n",target.getAccountNumber(), target.getCurrentBalance());

		} catch (NumberFormatException e) {
        System.out.println("❌ Please enter valid numbers");
		} 
	}
}
