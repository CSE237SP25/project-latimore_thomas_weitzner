package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import bankapp.Bank;
import bankapp.BankUtils;
import bankapp.CheckingsAccount;
import bankapp.LoginMenu;
import bankapp.Menu;
import bankapp.Teller;
import bankapp.User;

public class BankUtilsTests {
    
    @Test 
	public void searchTest() {
		Bank bank = new Bank();
		BankUtils bankUtils = new BankUtils();
		User testUser1 = new User("One","Pass1");
		User testUser2 = new User("Two","Pass2");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		users.add(testUser1);
		users.add(testUser2);
		LoginMenu login = new LoginMenu(users, tellers, bank);

		assertEquals(null, BankUtils.searchForProfile("Three",users));
		assertEquals(null, BankUtils.searchForProfile("one",users));
		assertEquals(testUser1, BankUtils.searchForProfile("One",users));
		assertEquals(testUser2, BankUtils.searchForProfile("Two",users));
	}

    @Test
	public void searchTellerTest() {
		Bank bank = new Bank();
		BankUtils bankUtils = new BankUtils();
		Teller testTeller1 = new Teller("One","Pass1");
		Teller testTeller2 = new Teller("Two","Pass2");
		List<User> users = new ArrayList<User>();
		List<Teller> tellers = new ArrayList<Teller>();
		tellers.add(testTeller1);
		tellers.add(testTeller2);
		
		assertEquals(null, BankUtils.searchForTeller("Three",tellers));
		assertEquals(null, BankUtils.searchForTeller("one",tellers));
		assertEquals(testTeller1, BankUtils.searchForTeller("One",tellers));
		assertEquals(testTeller2, BankUtils.searchForTeller("Two",tellers));
	}

	@Test
    public void testIsInvalidAccountName() {
		BankUtils bankUtils = new BankUtils();

        assertEquals(BankUtils.InvalidNameReason.EMPTY, BankUtils.isInvalidAccountName("", "OldName"));
        assertEquals(BankUtils.InvalidNameReason.LONG, BankUtils.isInvalidAccountName("ThisNameIsWayTooLongToActuallyWerk", "OldName"));
        assertEquals(BankUtils.InvalidNameReason.SAME_NAME, BankUtils.isInvalidAccountName("OldName", "OldName"));
        assertEquals(BankUtils.InvalidNameReason.SPECIAL_CHARACTERS, BankUtils.isInvalidAccountName("B@@@@d@Name", "OldName"));
        assertEquals(BankUtils.InvalidNameReason.NONE, BankUtils.isInvalidAccountName("GoodName", "OldName"));
    }
}
