package bankapp;

import bankapp.Menu.InvalidNameReason;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
	private String username;
	private String password;
	private ArrayList<BankAccount> accounts;
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
		this.password = newPassword;
	}
	
	public void changeUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void addAccount(BankAccount account) {
		if(InvalidNameReason.NONE != isInvalidAccountName(account.getAccountName()) || account.getAccountHolderName().equals("Unnamed Account")) {
			System.out.println("Enter a name for the new account:");
			String newAccountName = this.inputScanner.nextLine();
			account.setAccountHolderName(newAccountName);
		}
		if(isInvalidAccountName(account.getAccountName()) != InvalidNameReason.NONE) {
			System.out.println("Invalid account name. Please try again.");
			addAccount(account);
			return;
		}
		accounts.add(account);
	}
	
	public void removeAccount(BankAccount account) {
		accounts.remove(account);
	}
	
	public InvalidNameReason isInvalidAccountName(String name) {
		if(name.isEmpty()){
			System.out.println("Username or account name may not be empty.");
			return InvalidNameReason.EMPTY;
		}
		if(name.length() > 25) {
			System.out.println("Username or account name must be less than 25 characters.");
			return InvalidNameReason.LONG;
		}
		for (BankAccount existingAccount : accounts) {
			if (existingAccount.getAccountName().equals(name)) {
				System.out.println("New username or account name must be different from any other account names.");
				return InvalidNameReason.SAME_NAME;
			}
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
	
}