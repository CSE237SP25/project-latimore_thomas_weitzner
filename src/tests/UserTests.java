package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


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
	
	@Test
    void testProfileInformationInitialization() {
        User user = new User("testGirl", "password123");
        assertEquals("", user.getName());
        assertEquals("", user.getPhone());
        assertEquals("", user.getEmail());
        assertEquals("", user.getAddress());
        assertEquals("", user.getSsn());
        assertEquals("", user.getTshirtSize());
    }

	@Test
	void profileInformationTest(){
		User user = new User("testGirl", "password123");
		user.setName("Jane Doe");
		user.setPhone("123-456-7890");
		user.setEmail("jane@doe.com");
		user.setAddress("123 Main St");
		user.setSsn("123-45-6789");
		user.setTshirtSize("M");

		assertEquals("Jane Doe", user.getName());
		assertEquals("123-456-7890", user.getPhone());
		assertEquals("123 Main St", user.getAddress());
		assertEquals("123-45-6789", user.getSsn());
        assertEquals("M", user.getTshirtSize());
	}

	@Test
    void testProfileInformationUpdates() {
        User user = new User("testUser", "password123");
        user.setName("Initial Name");
        user.setPhone("0000000000");
        
        user.setName("Updated Name");
        user.setPhone("1111111111");
        
        assertEquals("Updated Name", user.getName());
        assertEquals("1111111111", user.getPhone());
        assertNotEquals("Initial Name", user.getName());
        assertNotEquals("0000000000", user.getPhone());
    }
	
}
