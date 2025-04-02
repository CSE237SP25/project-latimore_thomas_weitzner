package bankapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
	protected static int nextAccountNumber = 1;
	protected int accountNumber;
	protected double balance;
	protected String accountName;
	protected List<String> transactionHistory;
	protected static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
	
	public BankAccount(String accountName) {
		this.accountNumber = nextAccountNumber++;
		this.balance = 0;
		this.accountName = accountName;
		this.transactionHistory = new ArrayList<>();
	}
	
	public List<String> getTransactionHistory(){
		return new ArrayList<>(this.transactionHistory);
	}

	//typically for all types of accounts depositing is the same so it's fine
	public void deposit(double amount) {
		this.balance = getCurrentBalance();
		if(amount < 0) {
			throw new IllegalArgumentException();
		}else if(Double.isInfinite(this.balance+amount) || Double.isNaN(this.balance+amount)) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
		String dateTime = LocalDateTime.now().format(dtf);
		this.transactionHistory.add(String.format("Time: %s | Deposit: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
	}

	//different types of accounts will have different withdraw methods since they have different rules for withdrawing
	abstract public void withdraw(double amount);
	
	public void addTransactionHistory(String transaction) {
		this.transactionHistory.add(transaction);
	}
	
	abstract public double getCurrentBalance();
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getAccountName() {
		return this.accountName;
	}

	abstract public String getAccountType();
	
	public String setAccountHolderName(String accountHolderName) {
		this.transactionHistory.add("Account name changed to: " + accountHolderName);
		return this.accountName = accountHolderName;
	}
}
