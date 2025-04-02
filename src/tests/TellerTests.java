package tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import bankapp.Teller;

public class TellerTests{
	
	@Test
	void changeUsernameTest() {
		Teller teller = new Teller("username1", "password1");
		teller.changeUsername("Username2");
		assertEquals("Username2", teller.getUsername());
		teller.changeUsername("Username3");
		assertEquals("Username3", teller.getUsername());
	}
	
	@Test
	void changePasswordTest(){
		Teller teller = new Teller("username1", "password1");
		teller.changePassword("password2");
		assertEquals("password2", teller.getPassword());
		teller.changePassword("password3");
		assertEquals("Username3", teller.getUsername());
	}	
		
}