package tests;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import bankapp.LoginMenu;
import bankapp.Teller;
import bankapp.User;


public class LoginMenuTests{
	
	@Test 
	public void searchTest() {
		User testUser1 = new User("One","Pass1");
		User testUser2 = new User("Two","Pass2");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		users.add(testUser1);
		users.add(testUser2);
		LoginMenu login = new LoginMenu(users, tellers);
		assertEquals(null, login.searchForProfile("Three"));
		assertEquals(null, login.searchForProfile("one"));
		assertEquals(testUser1, login.searchForProfile("One"));
		assertEquals(testUser2, login.searchForProfile("Two"));
	}
	
	@Test
	public void searchTellerTest() {
		Teller testTeller1 = new Teller("One","Pass1");
		Teller testTeller2 = new Teller("Two","Pass2");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		tellers.add(testTeller1);
		tellers.add(testTeller2);
		
		LoginMenu login = new LoginMenu(users, tellers);
		assertEquals(null, login.searchForTeller("Three"));
		assertEquals(null, login.searchForTeller("one"));
		assertEquals(testTeller1, login.searchForTeller("One"));
		assertEquals(testTeller2, login.searchForTeller("Two"));
	}
	
	@Test
	public void passwordTest() {
		User testUser1 = new User("One","Pass1");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		Teller testTeller1 = new Teller("Teller1", "Password1");
		users.add(testUser1);
		LoginMenu login = new LoginMenu(users,tellers);
		assertEquals(false, login.checkPassword(testUser1, "PassWrong"));
		assertEquals(true, login.checkPassword(testUser1, "Pass1"));
		assertEquals(false, login.checkPassword(testUser1, "pass1"));
		assertEquals(false, login.checkPassword(testTeller1, "PassWrong"));
		assertEquals(true, login.checkPassword(testTeller1, "Password1"));
		assertEquals(false, login.checkPassword(testTeller1, "password1"));
		
		
		
	}
	
	
}