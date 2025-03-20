package bankapp;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	//NOTE: This is the entire bank, should only be ONE BANK OBJECT IN THE PROJECT
	private List<BankAccount> accounts;
	
	public Bank() {
		this.accounts = new ArrayList<>();
	}
	
	public void addAccount(BankAccount account) {
		for (BankAccount acc : accounts) {
			if (acc.getAccountNumber() == (account.getAccountNumber())) {
				throw new IllegalArgumentException("Account number already exists");
			}
		}
		this.accounts.add(account);
	}
	
	public void removeAccount(BankAccount account) {
		if (!this.accounts.contains(account)) {
            throw new IllegalArgumentException("Account does not exist");
            }
		this.accounts.remove(account);
	}
	
	public List<BankAccount> getAccounts() {
		return this.accounts;
	}
	
}
