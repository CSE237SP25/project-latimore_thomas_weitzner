package bankapp;

public class BankAccount {
	
	private static int nextAccountNumber = 1;
	private int accountNumber;
	private double balance;
	private String accountHolderName;
	
	public BankAccount(String accountHolderName) {
		this.accountNumber = nextAccountNumber++;
		this.balance = 0;
		this.accountHolderName = accountHolderName;
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
	
	public String getAccountHolderName() {
		return this.accountHolderName;
	}
}
