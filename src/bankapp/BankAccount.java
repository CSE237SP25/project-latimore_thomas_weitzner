package bankapp;

public class BankAccount {
	
	private static int nextAccountNumber = 1;
	private int accountNumber;
	private double balance;
	
	public BankAccount() {
		this.accountNumber = nextAccountNumber++;
		this.balance = 0;
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
}
