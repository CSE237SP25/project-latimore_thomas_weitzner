package bankapp;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private ArrayList<BankAccount> accounts;
	private ArrayList<SecurityQuestion> securityQuestions;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		accounts = new ArrayList<BankAccount>();
		securityQuestions = new ArrayList<SecurityQuestion>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public ArrayList<BankAccount> getAccounts(){
		return accounts;
	} 
	
	public void changePassword(String newPassword) {
		this.password = newPassword;
	}
	
	public void changeUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void addAccount(BankAccount account) {
		accounts.add(account);
	}
	
	public void removeAccount(BankAccount account) {
		accounts.remove(account);
	}

	public void addSecurityQuestions(String question, String answer) {
		securityQuestions.add(new SecurityQuestion(question, answer));
	}

	public ArrayList<SecurityQuestion> getSecurityQuestions() {
		return securityQuestions;
	}

	public boolean verifySecurityQuestion(String question, String answer) {
		for (SecurityQuestion sq : securityQuestions) {
			if (sq.getQuestion().equals(question)) {
				return sq.verifyAnswer(answer);
			}
		}
		return false;
	}
	
	
}