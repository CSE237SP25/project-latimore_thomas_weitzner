package bankapp;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
	
	private static int nextAccountNumber = 1;
	private int accountNumber;
	private double balance;
	private String accountName;
	private List<String> transactionHistory;
	
	public BankAccount(String accountName) {
		this.accountNumber = nextAccountNumber++;
		this.balance = 0;
		this.accountName = accountName;
		this.transactionHistory = new ArrayList<>();
	}
	
	public List<String> getTransactionHistory(){
		return new ArrayList<>(this.transactionHistory);
	}
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
		this.transactionHistory.add(String.format("Deposit: +$%.2f | Balance: $%.2f", amount, this.balance));
	}

	public void withdraw(double amount){
		if (amount < 0){
			throw new IllegalArgumentException("cannot complete a negative withdrawal");
		}
		if (this.balance - amount < 0){
			throw new IllegalArgumentException("insufficient funds");
		}
		this.balance -= amount;
		this.transactionHistory.add(String.format("Withdraw: +$%.2f | Balance: $%.2f", amount, this.balance));

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
		return this.accountName = accountHolderName;
	}
}
