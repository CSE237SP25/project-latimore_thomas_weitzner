package bankapp;

import java.util.List;

public class BankUtils{
	
	public enum InvalidNameReason {
		EMPTY,
		LONG,
		SAME_NAME,
		SPECIAL_CHARACTERS,
		NONE
	}
	
	public static InvalidNameReason isInvalidAccountName(String name, String currentAccountName) {
		if(name.isEmpty()){
			System.out.println("Username or account name may not be empty.");
			return InvalidNameReason.EMPTY;
		}
		if(name.length() > 25) {
			System.out.println("Username or account name must be less than 25 characters.");
			return InvalidNameReason.LONG;
		}
		if(name.equals(currentAccountName)) {
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
	
	public static User searchForProfile(String username, List<User> existingUsers) {
		for(User user : existingUsers) {
			if (user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
	}
	
	public static Teller searchForTeller(String username, List<Teller> existingTellers) {
		for(Teller teller : existingTellers) {
			if(teller.getUsername().equals(username)) {
				return teller;
			}
		}
		return null;
	}
	
}