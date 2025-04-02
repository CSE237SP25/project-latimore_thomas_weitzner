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
		this.transactionHistory.add(String.format("Time: %s | Deposit: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
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
		this.transactionHistory.add(String.format("Time: %s | Withdraw: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));

	}

	public void transfer(BankAccount targetAccount, double amount){
		if (amount <= 0) {
			throw new IllegalArgumentException("Must transfer positive amount");
		}
		if (this == targetAccount){
			throw new IllegalArgumentException("Cannot transfer balance into the same account");
		}
		if (this.balance < amount){
			throw new IllegalArgumentException("insufficient funds");
		}

		this.withdraw(amount);
		targetAccount.deposit(amount);

		String transferOut = String.format("Transfer to #%d: -$%.2f", targetAccount.getAccountNumber(), amount);
		String transferIn = String.format("Transfer from #%d: +$%.2f", this.accountNumber, amount);
    
    	this.transactionHistory.add(transferOut + "Balance: $" + this.balance);
		targetAccount.addTransactionHistory(transferIn + "Balance: $" + targetAccount.getCurrentBalance());
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
		return this.accountName = accountHolderName;
	}

	public String getAccountHolderName() {
    return this.accountName;
}
}
