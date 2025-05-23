package bankapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    protected static final String FILE_PATH = "src/bankapp/bankResources/transactionHistoryStore/";
	
    public BankAccount(String accountName) {
        this.accountNumber = nextAccountNumber++;
        this.balance = 0;
        this.accountName = accountName;
        this.transactionHistory = new ArrayList<>();
        loadTransactionHistory();
    }

	public BankAccount(String accountName, int accountNumber, double balance) {
		this.accountNumber = accountNumber;
		nextAccountNumber = Math.max(nextAccountNumber, accountNumber + 1);
		if(balance < 0) {
			throw new IllegalArgumentException("Initial balance cannot be negative");
		}
		this.balance = balance;
		this.accountName = accountName;
		this.transactionHistory = new ArrayList<>();
		loadTransactionHistory();
	}

    public List<String> getTransactionHistory() {
        return new ArrayList<>(this.transactionHistory);
    }
    
    public void initializeAccountBalance(double savedBalance) {
		if (savedBalance < 0) {
			throw new IllegalArgumentException("Initial balance cannot be negative");
		}
		this.balance = savedBalance;
	}

	//typically for all types of accounts depositing is the same so it's fine
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

	//different types of accounts will have different withdraw methods since they have different rules for withdrawing
	abstract public void withdraw(double amount);

    private void loadTransactionHistory() {
         File file = new File(FILE_PATH + getAccountNumber() + ".txt");
         if (!file.exists()) {
             return;
         }
         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 transactionHistory.add(line);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

	 public void transfer(BankAccount targetAccount, double amount){
		this.balance = getCurrentBalance();
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
		
		String dateTime = LocalDateTime.now().format(dtf);
		String transferOut = String.format("Time: %s | Transfer to #%d: -$%.2f | Balance: $%.2f", dateTime,  targetAccount.getAccountNumber(),amount, this.balance);
		String transferIn = String.format("Time: %s | Transfer from #%d: +$%.2f | Balance: $%.2f", dateTime, this.accountNumber, amount, targetAccount.getCurrentBalance());
		
    	this.addTransactionHistory(transferOut);
		targetAccount.addTransactionHistory(transferIn);
	}
	
	public void addTransactionHistory(String transaction) {
		this.transactionHistory.add(transaction);
		System.out.println(transaction);
		this.saveTransactionHistory();
	}

	private void saveTransactionHistory() {
        File directory = new File(FILE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(FILE_PATH + getAccountNumber() + ".txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("could not create transaction file");
			}
		}
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String transaction : transactionHistory) {
                writer.write(transaction);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
		String dateTime = LocalDateTime.now().format(dtf);
		addTransactionHistory(String.format("Time: %s | Account name changed to: %s", dateTime, accountHolderName));
		return this.accountName = accountHolderName;
	}

	public String getAccountHolderName() {
    	return this.accountName;
	}
}
