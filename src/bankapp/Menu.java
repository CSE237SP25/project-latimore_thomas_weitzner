package bankapp;
import java.util.Scanner;

public class Menu {

	private Scanner inputScanner;
	private BankAccount userAccount;
	private Bank bank;

	public static void main(String[] args) {
		Menu menu = new Menu();
		while (true) { 
			menu.provideUserChoices();
			String userInput = menu.getUserInput();
			menu.processUserInput(userInput);
		}
	}

	public Menu(){
		this.inputScanner = new Scanner(System.in);
		this.userAccount = new BankAccount("Placeholder Name"); //NOTE: replace the name with the actual persons name from their profile
		this.bank = new Bank();
		bank.addAccount(userAccount);
		System.out.println("Hello! Welcome to our bank app!");
	}

	public void provideUserChoices(){
		System.out.println("Would you like to: ");
		System.out.println("a.) Deposit money");
		System.out.println("b.) Withdraw money");
		System.out.println("c.) Check Balance");
		System.out.println("d.) Create a new account");
		System.out.println("g.) Remove an account");
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
			case "g":
				removeAccount();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}

	public void deposit(){
		System.out.println("How much would you like to deposit?");
		boolean validDeposit = false;
		while (!validDeposit) { 
			try {
					double depositAmount = Double.parseDouble(getUserInput());
					userAccount.deposit(depositAmount);
					validDeposit = true;
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid deposit amount. Please enter a deposit amount greater than or equal to 0.");
				}
			}
		System.out.println("Your new balance is: " + userAccount.getCurrentBalance());
	}
	
	public void createAccount() {
		this.userAccount = new BankAccount("Placeholder Name"); //NOTE: replace the name with the actual persons name from their profile
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
					validWithdraw = true;
				} catch (IllegalArgumentException e) {
					double currentBalance = userAccount.getCurrentBalance();
					System.out.println("Invalid withdrawal amount. Please enter a positive withdrawal amount less than or equal to your current balance of: $" + currentBalance);
				}
			}
		System.out.println("Your new balance is: " + userAccount.getCurrentBalance());
	}
}
