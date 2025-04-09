package  bankapp;


import java.util.List;

public class LoginMenu{
	List<User> existingUsers;
	List<Teller> existingTellers;
	
	
	public LoginMenu(List<User> existingUsers, List<Teller> existingTellers) {
		this.existingUsers = existingUsers;
		this.existingTellers = existingTellers;
	}
	
	public User searchForProfile(String username) {
		
		for(User user : existingUsers) {
			if (user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
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

	
	public void displayChoices() {
		System.out.println("\n -- Welcome to Bear Banks! -- \n");
		System.out.println("Welcome! Would you like to:");
		System.out.println("(a) Login");
		System.out.println("(b) Create a new bank profile");
		System.out.println("(c) Teller login");
		System.out.println("(x) Exit program");
	}
	
}