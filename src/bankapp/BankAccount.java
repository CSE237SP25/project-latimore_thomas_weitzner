
package bankapp;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankAccount {

    private static int nextAccountNumber = 1;
    private int accountNumber;
    private double balance;
    private String accountName;
    private List<String> transactionHistory;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private static final String FILE_PATH = "bankResources/transactionHistoryStore/";

    public BankAccount(String accountName) {
        this.accountNumber = nextAccountNumber++;
        this.balance = 0;
        this.accountName = accountName;
        this.transactionHistory = new ArrayList<>();
        loadTransactionHistory();
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(this.transactionHistory);
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        } else if (Double.isInfinite(this.balance + amount) || Double.isNaN(this.balance + amount)) {
            throw new IllegalArgumentException();
        }
        this.balance += amount;
        String dateTime = LocalDateTime.now().format(dtf);
        addTransactionHistory(String.format("Time: %s | Deposit: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
        saveTransactionHistory();
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("cannot complete a negative withdrawal");
        }
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException("insufficient funds");
        }
        this.balance -= amount;
        String dateTime = LocalDateTime.now().format(dtf);
        addTransactionHistory(String.format("Time: %s | Withdraw: +$%.2f | Balance: $%.2f", dateTime, amount, this.balance));
        saveTransactionHistory();
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
        String dateTime = LocalDateTime.now().format(dtf);
        addTransactionHistory(String.format("Time: %s | Account name changed to: %s", dateTime, accountHolderName));
        saveTransactionHistory();
        return this.accountName = accountHolderName;
    }

    private void saveTransactionHistory() {
        File directory = new File(FILE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(FILE_PATH + getAccountNumber() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String transaction : transactionHistory) {
                writer.write(transaction);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
