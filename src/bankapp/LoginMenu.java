package  bankapp;


import java.util.List;

public class LoginMenu{
	List<User> existingUsers;
	User profile;
	
	public LoginMenu(List<User> existingUsers) {
		this.existingUsers = existingUsers;
	}
	
	public User searchForProfile(String username) {
		
		for(User user : existingUsers) {
			if (user.getUsername().equals(username)){
				profile = user;
				return profile;
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

	
	public void displayChoices() {
		System.out.println("\n -- Welcome to Bear Banks! -- \n");
		System.out.println("Welcome! Would you like to:");
		System.out.println("(a) Login");
		System.out.println("(b) Create New Account");
	}
	
	
	
	
	
	
	
	
	
}