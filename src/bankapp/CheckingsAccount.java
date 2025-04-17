package bankapp;

import static bankapp.BankAccount.dtf;
import java.time.LocalDateTime;

public class CheckingsAccount extends BankAccount{
	
	
	public CheckingsAccount(String accountName) {
		super(accountName);
	}

	public CheckingsAccount(String accountName, int accountNumber, double balance) {
		super(accountName, accountNumber, balance);
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
		this.addTransactionHistory(String.format("Time: %s | Withdraw: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
	}

	
	@Override
	public double getCurrentBalance() {
		return this.balance;
	}
	
	@Override
    public String getAccountType(){
        return "Checkings";
    }
	
}
