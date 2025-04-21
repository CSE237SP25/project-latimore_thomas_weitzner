package bankapp;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MoneyMarketAccount extends BankAccount{
	
    private static final double interestRate = 0.005;//this is the interest rate which compounds daily and is added to the balance
    private static final double withdrawalPenalty = 0.07;
	private static String lastTransactionTime = LocalDateTime.now().format(dtf);
	private static final double minimumBalance = 1000.00;
	private static final Scanner sc = new Scanner(System.in);

	
	public MoneyMarketAccount(String accountName) {
		super(accountName);
		System.out.println("Initial Deposit Amount: ");
		Double depositAmount = Double.parseDouble(sc.nextLine());
		if(depositAmount<minimumBalance){
			throw new IllegalArgumentException("Initial deposit must be at least $1,000.00 for a money market account");
			//note that I don't know if doing this will auto delete this account or not
		}else{
			this.balance = depositAmount;
			String dateTime = LocalDateTime.now().format(dtf);
			this.transactionHistory.add(String.format("Time: %s | Initial Deposit: +$%.2f | Balance: $%.2f", dateTime, depositAmount, this.balance));
		}
	}

	public MoneyMarketAccount(String accountName, int accountNumber, double balance) {
		super(accountName, accountNumber, balance);
		if(balance<minimumBalance){
			throw new IllegalArgumentException("Initial deposit must be at least $1,000.00 for a money market account");
			//note that I don't know if doing this will auto delete this account or not
		}else{
			this.balance = balance;
			String dateTime = LocalDateTime.now().format(dtf);
			if(this.transactionHistory.isEmpty()){
				lastTransactionTime = dateTime;
				this.addTransactionHistory(String.format("Time: %s | Initial Deposit: +$%.2f | Balance: $%.2f", dateTime, balance, this.balance));
			}else{
				setLastTransactionTimeFromRestart();
			}
		}
		getCurrentBalance();
	}

    @Override
	public void withdraw(double amount){
        //need to implement some kind of penalty for withdrawing from a savings account
		if (amount < 0){
			throw new IllegalArgumentException("cannot complete a negative withdrawal");
		}
		//apply the interest before checking if you have enough money
		getCurrentBalance();
		System.out.println("Note that withdrawing from a money market account will incur a penalty of 7% of the amount withdrawn.");
		System.out.println("Do you still want to continue with the transaction? Please indicate y or n below.");
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
			lastTransactionTime = dateTime;
		}else if (response.equalsIgnoreCase("n")){
			System.out.println("Transaction cancelled.");
		}
	}
	
	@Override
	public double getCurrentBalance() {
    	LocalDateTime time = LocalDateTime.parse(lastTransactionTime, dtf);
		LocalDateTime now = LocalDateTime.now();
		long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(time, now);
		if(daysBetween>0){
			double interest = this.balance * Math.pow(1 + getInterestRate(), daysBetween);
			this.balance += interest;
			this.addTransactionHistory(String.format("Time: %s | Interest: +$%.2f | Balance: $%.2f", now.format(dtf), interest, this.balance));
			lastTransactionTime = now.format(dtf);
		}
		return this.balance;
	}

	public void setLastTransactionTimeFromRestart() {
		if(!this.transactionHistory.isEmpty()){
			String latestTransaction = this.transactionHistory.get(this.transactionHistory.size()-1);
			lastTransactionTime=latestTransaction.substring(6,25);
		}
	}

    @Override
    public String getAccountType(){
        return "Money Market";
    }

	public static double getInterestRate() {
		return interestRate;
	}

	public static double getWithdrawalPenalty() {
		return withdrawalPenalty;
	}

}
