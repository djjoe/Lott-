package hu.unideb.inf.server.test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.unideb.inf.server.controller.Drawer;
import hu.unideb.inf.server.model.Draw;
import hu.unideb.inf.server.model.Ticket;
import hu.unideb.inf.server.model.User;

public class OTOSTest {

	static Drawer drawer;
	static Draw draw;
	static List<Ticket> tickets;
	static User user;
	
	private static Ticket newTicket(User user, List<Integer> fields, Integer drawID, LocalDateTime date, Integer typeID) {
		Ticket ticket = new Ticket();
		ticket.setPlayer(user);
		ticket.setFields(fields);
		ticket.setDrawID(drawID);
		ticket.setDate(date);
		ticket.setTypeID(typeID);
		return ticket;
	}
	
	@Before
	public void setUp() throws Exception {
		user.setBalance(0);
		tickets.clear();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		drawer = new Drawer();
		draw = new Draw();
		draw.setFields(Arrays.asList(3, 6, 19, 30, 64));
		draw.setTicketType(0);
		draw.setDate(LocalDateTime.now());
		
		user = new User();
		user.setUsername("djjoe");
		
		tickets = new ArrayList<>();
		tickets.add(newTicket(user, Arrays.asList(3, 0, 1, 2, 7), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 7, 8, 9), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 20, 21), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 30, 5), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 30, 64), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 30, 64), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 30, 64), 1, LocalDateTime.now(), 0));
	}

	@Test
	public void testHit1() {
		Ticket ticket = newTicket(user, Arrays.asList(3, 0, 1, 2, 7), 1, LocalDateTime.now(), 0);
		drawer.hits(draw, ticket);
		assertEquals((Integer)1, drawer.hits(draw, ticket));
	}
	
	@Test
	public void testHit2() {
		Ticket ticket = newTicket(user, Arrays.asList(3, 6, 7, 8, 9), 1, LocalDateTime.now(), 0);
		drawer.hits(draw, ticket);
		assertEquals((Integer)2, drawer.hits(draw, ticket));
	}
	@Test
	public void testHit3() {
		Ticket ticket = newTicket(user, Arrays.asList(3, 6, 19, 2, 7), 1, LocalDateTime.now(), 0);
		drawer.hits(draw, ticket);
		assertEquals(3, drawer.hits(draw, ticket).intValue());
	}
	@Test
	public void testHit4() {
		Ticket ticket = newTicket(user, Arrays.asList(3, 6, 19, 30, 5), 1, LocalDateTime.now(), 0);
		drawer.hits(draw, ticket);
		assertEquals((Integer)4, drawer.hits(draw, ticket));
	}
	@Test
	public void testHit5() {
		Ticket ticket = newTicket(user, Arrays.asList(3, 6, 19, 30, 64), 1, LocalDateTime.now(), 0);
		drawer.hits(draw, ticket);
		assertEquals((Integer)5, drawer.hits(draw, ticket));
	}
	
	@Test
	public void testPay0() {
		tickets.add(newTicket(user, Arrays.asList(3, 0, 1, 2, 7), 1, LocalDateTime.now(), 0));
		drawer.pay(draw, tickets, 100000);
		assertEquals(0, user.getBalance().intValue());
	}
	
	@Test
	public void testPay2() {
		tickets.add(newTicket(user, Arrays.asList(3, 6, 1, 2, 7), 1, LocalDateTime.now(), 0));
		drawer.pay(draw, tickets, 100000);
		assertEquals(35000, user.getBalance().intValue());
	}

	@Test
	public void testPay2_2() {
		tickets.add(newTicket(user, Arrays.asList(3, 6, 1, 2, 7), 1, LocalDateTime.now(), 0));
		tickets.add(newTicket(user, Arrays.asList(3, 6, 1, 2, 7), 1, LocalDateTime.now(), 0));
		drawer.pay(draw, tickets, 100000);
		assertEquals(35000, user.getBalance().intValue());
	}

	@Test
	public void testPay3() {
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 2, 7), 1, LocalDateTime.now(), 0));
		drawer.pay(draw, tickets, 100000);
		assertEquals(18000, user.getBalance().intValue());
	}
	
	@Test
	public void testPay4() {
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 30, 7), 1, LocalDateTime.now(), 0));
		drawer.pay(draw, tickets, 100000);
		assertEquals(17000, user.getBalance().intValue());
	}
	
	@Test
	public void testPay5() {
		tickets.add(newTicket(user, Arrays.asList(3, 6, 19, 30, 64), 1, LocalDateTime.now(), 0));
		drawer.pay(draw, tickets, 100000);
		assertEquals(30000, user.getBalance().intValue());
	}

}
