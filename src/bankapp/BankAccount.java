package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankAccount {
	
	private static int nextAccountNumber = 1;
	private int accountNumber;
	private double balance;
	private String accountName;
	private List<String> transactionHistory;
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
	
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
		}else if(Double.isInfinite(this.balance+amount) || Double.isNaN(this.balance+amount)) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
		String dateTime = LocalDateTime.now().format(dtf);
		addTransactionHistory(String.format("Time: %s | Deposit: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
	}

	public void withdraw(double amount){
		if (amount < 0){
			throw new IllegalArgumentException("cannot complete a negative withdrawal");
		}
		if (this.balance - amount < 0){
			throw new IllegalArgumentException("insufficient funds");
		}
		this.balance -= amount;
		String dateTime = LocalDateTime.now().format(dtf);
		addTransactionHistory(String.format("Time: %s | Withdraw: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));

	}
	
	public void addTransactionHistory(String transaction) {
		this.transactionHistory.add(transaction);
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
		this.transactionHistory.add("Account name changed to: " + accountHolderName);
		String dateTime = LocalDateTime.now().format(dtf);
		addTransactionHistory(String.format("Time: %s | Account name changed to: %s", dateTime, accountHolderName));
		return this.accountName = accountHolderName;
	}
}
