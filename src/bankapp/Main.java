package bankapp;



public class Main{
	public static void main(String[] args) {
		Bank bank = new Bank();
		boolean active = true;
		while(active) {
			LoginMenu loginMenu = new LoginMenu(bank.getUsers(), bank.getTellers(), bank);
			active = loginMenu.operateMenu();
		}
		
	}
}