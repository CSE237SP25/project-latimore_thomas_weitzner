package tests;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;


import bankapp.BankAccount;
import bankapp.User;

public class UserTests {

	//Testing creation of new user
	
	@Test
	void newUserTest() {
		User user1 = new User("BillyIsCool", "socks");
		User user2 = new User("Username", "Password");
		assertEquals("Username",user2.getUsername());
		assertEquals("Password",user2.getPassword());
		assertEquals("BillyIsCool", user1.getUsername());
		assertEquals("socks", user1.getPassword());
	}
	
	@Test
	void addingAccountsTest(){
		User user1 = new User("JohnSmith1", "JaneDoe!");
		BankAccount newAccount = new BankAccount("SampleAccount");
		BankAccount anotherAccount = new BankAccount("SecondSample");
		user1.addAccount(newAccount);
		assertEquals(1, user1.getAccounts().size());
		user1.addAccount(anotherAccount);
		assertEquals(2, user1.getAccounts().size());
		
	}
	
	@Test
	void removingAccountsTest() {
		User user1 = new User("JohnSmith1", "JaneDoe!");
		BankAccount newAccount = new BankAccount("SampleAccount");
		BankAccount anotherAccount = new BankAccount("SecondSample");
		user1.addAccount(newAccount);
		assertEquals(1, user1.getAccounts().size());
		user1.addAccount(anotherAccount);
		assertEquals(2, user1.getAccounts().size());
		user1.removeAccount(anotherAccount);
		assertEquals(1, user1.getAccounts().size());
	}
	@Test
	void changePasswordTest() {
		User user1 = new User("JohnSmith1", "JaneDoe!");
		user1.changePassword("MaryJane2");
		assertEquals("MaryJane2", user1.getPassword());
	}
	
	@Test
	void changeUsernameTest() {
		User user1 = new User("JohnSmith1", "JaneDoe!");
		user1.changeUsername("PeterParker2");
		assertEquals("PeterParker2", user1.getUsername());
	}
	
	
}
