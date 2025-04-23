package  bankapp;


import java.util.List;
import java.util.Scanner;

public class LoginMenu{
	List<User> existingUsers;
    List<Teller> existingTellers;
	private Scanner inputScanner;
	private Boolean active;
	private User user;
	private Teller teller;
	private Bank bank;
	private String type;

	
	public LoginMenu(List<User> existingUsers, List<Teller> existingTellers, Bank bank) {
		this.existingUsers = existingUsers;
		this.existingTellers = existingTellers;
		active = true;
		this.bank = bank;
		inputScanner = new Scanner(System.in);
		
	}
	
	public User searchForProfile(String username) {
		
		for(User user : existingUsers) {
			if (user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
	}
	public Boolean doesProfileExist(String username){
		if(searchForProfile(username) == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	public Teller searchForTeller(String username) {
		for(Teller teller : existingTellers) {
			if(teller.getUsername().equals(username)) {
				return teller;
			}
		}
		return null;
	}
	
	public Boolean checkPassword(User user, String password){
		if(user.getPassword().equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean checkPassword(Teller teller, String password) {
		if(teller.getPassword().equals(password)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public Boolean checkUserHasAccounts(User user){
		if(user.getAccounts().isEmpty()) {
			System.out.println("You do not have any accounts. Making one for you!");
			return true;
		}
		return true;
	}
	
	public boolean operateMenu(){
		while(active) {
			displayChoices();
			if(loginInputChoices()){
				loginToMenu(type);
				user = null;
				teller = null;
				return true;
			}
		}
		return false;
		
	}
	
	public void loginToMenu(String type){
		switch(type) {
		case "teller":
			TellerMenu tellerMenu = new TellerMenu(bank,teller);
			tellerMenu.operateMenu();
			
			break;
		case "user":
			Menu menu = new Menu(bank,user);
			menu.operateMenu();
			break;
		}
		
	}

	
	
	public void displayChoices() {
		System.out.println("\n -- Welcome to Bear Banks! -- \n");
		System.out.println("Welcome! Would you like to:");
		System.out.println("(a) Login");
		System.out.println("(b) Create a new bank profile");
		System.out.println("(c) Teller login");
		System.out.println("(x) Exit program");
	}
	
	public Boolean loginInputChoices() {
		String userInput = getUserInput();
		switch(userInput.toLowerCase()) {
			case "a":
				return loginToAccount();
			case "b":
				System.out.println("Creating a new account...");
				return createProfile();
			case "c":
				return loginToTellerAccount();
			case "x":
				System.out.println("Thank you for using Bear Banks! Hope to see you again soon!");
				System.out.println("Exiting the program...");
				active = false;
				return false;
			default:
				System.out.println("Invalid Input");
				return false;
		}
		
	}
	
	public Boolean loginToAccount() {
		System.out.println("Enter Username:");
		String username = getUserInput();
		User profile = searchForProfile(username);
		if(profile == null) {
			System.out.println("Profile does not exist");
			return false;
		}
		else {
			System.out.println("Enter Password:");
			String password = getUserInput();
			if(checkPassword(profile, password)) {
				user = profile;
				type = "user";
				return true;
			}
			System.out.println("Passwords do not match");
			return false;
		}
	}
	
	public String getUserInput(){
		return this.inputScanner.nextLine();
	}
	
	public Boolean createProfile(){
		System.out.println("Welcome! Let's get you set up with a profile!");
		System.out.println("Enter a username: ");
		String username = getUserInput();
		if(doesProfileExist(username)) {
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
			user = newUser;
			//by default we create a checkings account for the user 
			BankAccount newAccount = new CheckingsAccount(newUser.getUsername() + " Account");
			newUser.addAccount(newAccount);
			bank.addUser(newUser);
			setupSecurityQuestions(newUser);
			type = "user";
			return true;
		}
		else {
			System.out.println("Cancelling account creation...");
		}
		return false;
	}
	
	public Boolean loginToTellerAccount(){
		System.out.println("Enter Username:");
		String username = getUserInput();
		if(searchForTeller(username) == null) {
			System.out.println("Teller does not exist");
			return false;
		}
		else {
			Teller teller = searchForTeller(username);
			System.out.println("Enter Password:");
			String password = getUserInput();
			Boolean correctPassword = checkPassword(teller, password);
			if(correctPassword) {
				type = "teller";
				this.teller = teller;
				return true;
			}
			System.out.println("Passwords do not match");
			return false;
		}
		
	}
	// i can change so they have to pick two to answer?
	private void setupSecurityQuestions (User user){
		System.out.println("Please set up your security questions.");
		String[] questions = {
			"What is your mother's maiden name?",
			"What was the name of your first pet?",
			"What city were you born in?",
			"What is your favorite color?",
		};
		for (String question : questions) {
			System.out.println(question);
			String answer = getUserInput();
			user.addSecurityQuestions(question, answer);
		}
		System.out.println("Security questions set up successfully!");
		System.out.println("Please remember your answers, as they will be used for account recovery.");
	}

	public Boolean resetPassword(User user){
		if (user == null || user.getSecurityQuestions().isEmpty()) {
			System.out.println("No security questions set up. Cannot reset password.");
			return false;
		}
		System.out.println("Please answer your security questions to reset your password.");
		for (SecurityQuestion sq : user.getSecurityQuestions()) {
			System.out.println(sq.getQuestion());
			String answer = getUserInput();
			if (!sq.verifyAnswer(answer)) {
				System.out.println("Incorrect answer. Please try again.");
				return false;
			}
		}
		System.out.println("All answers are correct! Please enter your new password:");
		String newPassword = getUserInput();
		user.changePassword(newPassword);
	    bank.saveAccountsToFile();
		System.out.println("Password reset successfully!");
		return true;
	}
	
}