package bankapp;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private ArrayList<BankAccount> accounts;
	private String name;
    private String phone;
    private String email;
    private String address;
    private String ssn;
    private String tshirtSize;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		accounts = new ArrayList<BankAccount>();
		this.name = "";
        this.phone = "";
        this.email = "";
        this.address = "";
        this.ssn = "";
        this.tshirtSize = "";
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
	public String getTshirtSize() {
		return tshirtSize;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	public void setTshirtSize(String tshirtSize) {
		this.tshirtSize = tshirtSize;
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
	
	
}