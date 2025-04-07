package bankapp;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MoneyMarketAccount extends BankAccount{
	
    private static final double interestRate = 0.005;//this is the interest rate which compounds daily and is added to the balance
    private static final double withdrawalPenalty = 0.05;
	private static final String lastTransactionTime = LocalDateTime.now().format(dtf);
	private static final double minimumBalance = 1000.00;

	
	public MoneyMarketAccount(String accountName) {
        super(accountName);
	}

    @Override
	public void withdraw(double amount){
        //need to implement some kind of penalty for withdrawing from a savings account
		if (amount < 0){
			throw new IllegalArgumentException("cannot complete a negative withdrawal");
		}
		//apply the interest before checking if you have enough money
		getCurrentBalance();
		System.out.println("Note that withdrawing from a savings account will incur a penalty of 5% of the amount withdrawn.");
		System.out.println("Do you still want to continue with the transaction? Please indicate y or n below.");
		Scanner sc = new Scanner(System.in);
		String response = sc.nextLine();
		if (response.equalsIgnoreCase("y")){
			amount += amount * getWithdrawalPenalty();
			System.out.println("The amount after penalty is: " + amount);
			if (this.balance - amount < minimumBalance){
				throw new IllegalArgumentException("Insufficient funds. A minimum balance of $1000.00 is required.");
			}
			this.balance -= amount;
			String dateTime = LocalDateTime.now().format(dtf);
			this.transactionHistory.add(String.format("Time: %s | Withdraw: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
		}else if (response.equalsIgnoreCase("n")){
			System.out.println("Transaction cancelled.");
		}
	}
	
	@Override
	public double getCurrentBalance() {
    	LocalDateTime time = LocalDateTime.parse(lastTransactionTime, dtf);
		LocalDateTime now = LocalDateTime.now();
		long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(time, now);
		double interest = this.balance * Math.pow(1 + getInterestRate(), daysBetween);
		this.balance += interest;
		this.transactionHistory.add(String.format("Time: %s | Interest: +$%.2f | Balance: $%.2f", now.format(dtf), interest, this.balance));
		return this.balance;
	}

    @Override
    public String getAccountType(){
        return "Savings";
    }

	public static double getInterestRate() {
		return interestRate;
	}

	public static double getWithdrawalPenalty() {
		return withdrawalPenalty;
	}

}
