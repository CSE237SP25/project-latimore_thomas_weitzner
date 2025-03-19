package bankapp;
import java.util.Scanner;

public class Menu {

	private Scanner inputScanner;
	private BankAccount userAccount;

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
		this.userAccount = new BankAccount();
		System.out.println("Hello! Welcome to our bank app!");
	}

	public void provideUserChoices(){
		System.out.println("Would you like to: ");
		System.out.println("a.) Deposit money");
		System.out.println("b.) Check Balance");
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
				System.out.println("Your current balance is: " + userAccount.getCurrentBalance());
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
}
