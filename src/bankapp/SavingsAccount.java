package bankapp;

import java.time.LocalDateTime;

public class SavingsAccount extends BankAccount{
	
    private static final double interestRate = 0.01;//this is the interest rate which compounds daily and is added to the balance
    private static final double withdrawalPenalty = 0.05;

	
	public SavingsAccount(String accountName) {
        super(accountName);
	}
	

    @Override
	public void withdraw(double amount){
        //need to implement some kind of penalty for withdrawing from a savings account
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
        //implement way for the interest to be updated
		return this.balance;
	}

    @Override
    public String getAccountType(){
        return "Savings";
    }
}
