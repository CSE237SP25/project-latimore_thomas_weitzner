package bankapp;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private ArrayList<BankAccount> accounts;
	private String nickname;
    private String phone;
    private String email;
    private String address;
    private String ssn;
    private String tshirtSize;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		accounts = new ArrayList<BankAccount>();
		this.nickname = "";
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
        return nickname;
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

	public void setName(String nickname) {
        this.nickname = nickname;
    } // can be a string with a space

    public void setPhone(String phone) {
        
        this.phone = phone;
    } //test to make sure this is 10 digits

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSsn(String ssn) {
    
        this.ssn = ssn;

    } //test to make sure this is 9 digits

    public void setTshirtSize(String tshirtSize) {
        this.tshirtSize = tshirtSize;
    } //test to make sure this is a valid size
    //valid sizes are S, M, L, XL, XXL

	
	
}