<<<<<<< HEAD
package bankapp;

public class BankAccount {
	
	private static int nextAccountNumber = 1;
	private int accountNumber;
	private double balance;
	private String accountName;
	
	public BankAccount(String accountName) {
		this.accountNumber = nextAccountNumber++;
		this.balance = 0;
		this.accountName = accountName;
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}

	public void withdraw(double amount) {
		if(amount < 0 || amount > this.balance) {
			throw new IllegalArgumentException();
		}
		this.balance -= amount;
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	
	public String setAccountHolderName(String accountHolderName) {
		return this.accountHolderName = accountHolderName;
	}
}
=======
package bankapp;

public class BankAccount {

	private double balance;
	
	public BankAccount() {
		this.balance = 0;
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}
	
	public void withdraw(double amount){
		if (amount < 0){
			throw new IllegalArgumentException("cannot complete a negative withdrawal");
		}
		if (this.balance - amount < 0){
			throw new IllegalArgumentException("insufficient funds")
		}
		this.balance -= amount;
	}
	public double getCurrentBalance() {
		return this.balance;
	}
}
>>>>>>> overdraft
