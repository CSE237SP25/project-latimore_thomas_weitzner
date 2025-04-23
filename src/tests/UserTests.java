package tests;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;


import bankapp.BankAccount;
import bankapp.Menu;
import bankapp.CheckingsAccount;
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
		BankAccount newAccount = new CheckingsAccount("SampleAccount");
		BankAccount anotherAccount = new CheckingsAccount("SecondSample");
		user1.addAccount(newAccount);
		assertEquals(1, user1.getAccounts().size());
		user1.addAccount(anotherAccount);
		assertEquals(2, user1.getAccounts().size());
		
	}
	
	@Test
	void removingAccountsTest() {
		User user1 = new User("JohnSmith1", "JaneDoe!");
		BankAccount newAccount = new CheckingsAccount("SampleAccount");
		BankAccount anotherAccount = new CheckingsAccount("SecondSample");
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

    @Test
    public void testIsInvalidAccountName() {
		User user1 = new User("JohnSmith1", "JaneDoe!");

        assertEquals(Menu.InvalidNameReason.EMPTY, user1.isInvalidAccountName(""));
        assertEquals(Menu.InvalidNameReason.LONG,user1.isInvalidAccountName("ThisNameIsWayTooLongToActuallyWerk"));
        assertEquals(Menu.InvalidNameReason.SAME_NAME, user1.isInvalidAccountName("OldName"));
        assertEquals(Menu.InvalidNameReason.SPECIAL_CHARACTERS, user1.isInvalidAccountName("B@@@@d@Name"));
        assertEquals(Menu.InvalidNameReason.NONE, user1.isInvalidAccountName("GoodName"));
    }	

	@Test
	void changePasswordToOldTest() {
		User user1 = new User("JohnSmith1", "JaneDoe!");
		user1.changePassword("JaneDoe!");
		try {
			user1.changePassword("JaneDoe!");
		} catch (IllegalArgumentException e) {
			assertEquals("New password must be different from the old password.", e.getMessage());
		}
	}
}
