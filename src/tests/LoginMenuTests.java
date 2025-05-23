package tests;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import bankapp.LoginMenu;
import bankapp.Teller;
import bankapp.User;
import bankapp.Bank;

public class LoginMenuTests{
	@Test 
	public void profileExistTest() {
		Bank bank = new Bank();
		User testUser1 = new User("One","Pass1");
		User testUser2 = new User("Two","Pass2");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		users.add(testUser1);
		users.add(testUser2);
		LoginMenu login = new LoginMenu(users, tellers, bank);
		assertEquals(false, login.doesProfileExist("Three"));
		assertEquals(false, login.doesProfileExist("one"));
		assertEquals(true, login.doesProfileExist("One"));
		assertEquals(true, login.doesProfileExist("Two"));
	}
	
	//the login menu isn't what actually has searchForProfile function, bank utils does so I'm gonna move this there
	/*@Test
	public void searchTellerTest() {
		Bank bank = new Bank();
		Teller testTeller1 = new Teller("One","Pass1");
		Teller testTeller2 = new Teller("Two","Pass2");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		tellers.add(testTeller1);
		tellers.add(testTeller2);
		
		LoginMenu login = new LoginMenu(users, tellers, bank);
		assertEquals(null, login.searchForTeller("Three"));
		assertEquals(null, login.searchForTeller("one"));
		assertEquals(testTeller1, login.searchForTeller("One"));
		assertEquals(testTeller2, login.searchForTeller("Two"));
	}*/
	
	@Test
	public void passwordTest() {
		Bank bank = new Bank();
		User testUser1 = new User("One","Pass1");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		Teller testTeller1 = new Teller("Teller1", "Password1");
		users.add(testUser1);
		LoginMenu login = new LoginMenu(users,tellers, bank);
		assertEquals(false, login.checkPassword(testUser1, "PassWrong"));
		assertEquals(true, login.checkPassword(testUser1, "Pass1"));
		assertEquals(false, login.checkPassword(testUser1, "pass1"));
		assertEquals(false, login.checkPassword(testTeller1, "PassWrong"));
		assertEquals(true, login.checkPassword(testTeller1, "Password1"));
		assertEquals(false, login.checkPassword(testTeller1, "password1"));
		
		
		
	}
	
	
}