package tests;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import bankapp.LoginMenu;
import bankapp.User;


public class LoginMenuTests{
	
	@Test 
	public void searchTest() {
		User testUser1 = new User("One","Pass1");
		User testUser2 = new User("Two","Pass2");
		List<User> users = new ArrayList<User>();
		users.add(testUser1);
		users.add(testUser2);
		LoginMenu login = new LoginMenu(users);
		assertEquals(null, login.searchForProfile("Three"));
		assertEquals(null, login.searchForProfile("one"));
		assertEquals(testUser1, login.searchForProfile("One"));
		assertEquals(testUser2, login.searchForProfile("Two"));
	}
	
	@Test
	public void passwordTest() {
		User testUser1 = new User("One","Pass1");
		List<User> users = new ArrayList<User>();
		users.add(testUser1);
		LoginMenu login = new LoginMenu(users);
		assertEquals(false, login.checkPassword(testUser1, "PassWrong"));
		assertEquals(true, login.checkPassword(testUser1, "Pass1"));
		assertEquals(false, login.checkPassword(testUser1, "pass1"));
		
		
		
	}
	
	
}