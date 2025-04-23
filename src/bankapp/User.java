package bankapp;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
	private String username;
	private String password;
	private ArrayList<BankAccount> accounts;
	private ArrayList<SecurityQuestion> securityQuestions;
	private Scanner inputScanner;

 	private String name;
 	private String phone;
 	private String email;
 	private String address;
 	private String ssn;

	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		accounts = new ArrayList<BankAccount>();
		securityQuestions = new ArrayList<SecurityQuestion>();
		this.inputScanner = new Scanner(System.in);
		this.name = "";
        this.phone = "";
        this.email = "";
        this.address = "";
        this.ssn = "";
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
	public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getSsn() {
        return ssn;
    }


	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setFormattedPhone (String phone){
		this.phone = phone.replaceAll("[^0-9]", "");
	}

	public boolean isValidEmail(String email) {
    return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	}

	public void setEmail(String email) {
    if (isValidEmail(email)) {
        this.email = email;
    } else {
        throw new IllegalArgumentException("Invalid email format");
    	}
	}

	public boolean isValidAddress(String address) {
		return address != null && !address.isEmpty() && address.matches("^[\\w\\s\\d\\-\\,\\.#]+$");
	}

	public void setAddress(String address) {
		if(isValidAddress(address)) {
			this.address = address;
		} else {
			throw new IllegalArgumentException("Invalid address format");
		}
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getMaskedSsn(){
		if (ssn == null || ssn.length() < 4) return "***-**-****";
		return "***-**-" + ssn.substring(ssn.length() - 4);
	}

	public boolean isValidSsn(String ssn) {
    return ssn != null && ssn.matches("^\\d{9}$");
	}
	
	
	
	public void changePassword(String newPassword) {
		if(this.password.equals(newPassword)) {
			System.out.println("New password must be different from the old password.");
		}else{
			this.password = newPassword;
			System.out.println("Password successfully changed");
		}
	}
	
	public void changeUsername(String newUsername) {
		//the checks for this are already handled in the menu class
		this.username = newUsername;
	}
	
	public void addAccount(BankAccount account) {
		if(BankUtils.InvalidNameReason.NONE != BankUtils.isInvalidAccountName(account.getAccountName(), "") || account.getAccountHolderName().equals("Unnamed Account")) {
			System.out.println("Enter a name for the new account:");
			String newAccountName = this.inputScanner.nextLine();
			account.setAccountHolderName(newAccountName);
		}
		if(BankUtils.isInvalidAccountName(account.getAccountName(),"") != BankUtils.InvalidNameReason.NONE) {
			System.out.println("Invalid account name. Please try again.");
			addAccount(account);
			return;
		}
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