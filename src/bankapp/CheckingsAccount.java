package bankapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class CheckingsAccount extends BankAccount{
	
	
	public CheckingsAccount(String accountName) {
		super(accountName);
	}
	
	public List<String> getTransactionHistory(){
		return new ArrayList<>(this.transactionHistory);
	}

	//different types of accounts will have different withdraw methods since they have different rules for withdrawing
	@Override
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
	
	@Override
	public double getCurrentBalance() {
		return this.balance;
	}
	
	@Override
    public String getAccountType(){
        return "Checkings";
    }
	
	public String setAccountHolderName(String accountHolderName) {
		this.transactionHistory.add("Account name changed to: " + accountHolderName);
		return this.accountName = accountHolderName;
	}
}
