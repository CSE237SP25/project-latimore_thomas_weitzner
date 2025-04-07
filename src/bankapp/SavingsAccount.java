package bankapp;

import java.time.LocalDateTime;
import java.util.Scanner;

public class SavingsAccount extends BankAccount{
	
    private static final double interestRate = 0.00001;//this is the interest rate which compounds daily and is added to the balance
    private static final double withdrawalPenalty = 0.05;
	private static final String lastTransactionTime = LocalDateTime.now().format(dtf);
	private static final Scanner sc = new Scanner(System.in);
	
	public SavingsAccount(String accountName) {
        super(accountName);
		System.out.println("Initial Deposit Amount: ");
		Double depositAmount = Double.parseDouble(sc.nextLine());
		if(depositAmount<100.0){
			throw new IllegalArgumentException("Initial deposit must be at least $100.00 for a savings account");
			//note that I don't know if doing this will auto delete this account or not
		}else{
			this.balance = depositAmount;
			String dateTime = LocalDateTime.now().format(dtf);
			this.transactionHistory.add(String.format("Time: %s | Deposit: +$%.2f | Balance: $%.2f", dateTime, depositAmount, this.balance));
		}
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
		String response = sc.nextLine();
		if (response.equalsIgnoreCase("y")){
			amount += amount * getWithdrawalPenalty();
			System.out.println("The amount after penalty is: " + amount);
			if (this.balance - amount < 0){
				throw new IllegalArgumentException("insufficient funds");
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
