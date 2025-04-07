package bankapp;

public class Teller{
	private String username;
	private String password;
	
	public Teller(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void changeUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void changePassword(String newPassword) {
		this.password = newPassword;
	}
}