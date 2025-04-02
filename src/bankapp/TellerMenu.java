package bankapp;

import java.util.List;
import java.util.Scanner;

public class TellerMenu{
	private Teller teller;
	private Bank bank;
	private LoginMenu login;
	private Scanner inputScanner;
	
	public TellerMenu(Bank bank, Teller teller) {
		this.bank = bank;
		this.teller = teller;
		login = new LoginMenu(bank.getUsers());
		inputScanner = new Scanner(System.in);
	}
	
	
	public void operateMenu(){
		while(true) {
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
		System.out.print("Hello! Would you like to:");
		System.out.print("a.) View active bank accounts?");
	}
	
	public void tellerProccessChoice(String userInput) {
		switch(userInput.toLowerCase()) {
		case "a":
			showcaseAccounts();
			break;
		default:
			System.out.println("Input Invalid!");
		}
		
	}
	
	public void showcaseAccounts() {
		System.out.println("Which user's account would you like to see?");
		List<User> users = bank.getUsers();
		for( User user : users) {
			System.out.println(user.getUsername());
		}
		System.out.print("Username of account:");
		String username = getUserInput();
		User currentUser = selectUser(username);
		System.out.println(currentUser.getAccounts());
	}
	
}