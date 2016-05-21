package hu.unideb.inf.server.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import hu.unideb.inf.server.Main;
import hu.unideb.inf.server.model.Draw;
import hu.unideb.inf.server.model.Ticket;
import hu.unideb.inf.server.model.User;

public class Drawer {

	private int lastDrawID;
	private List<Ticket> newTickets;
	private List<Ticket> oldTickets;
	private List<User> winners;
	
	public Drawer() {
		newTickets = new ArrayList<Ticket>();
		winners = new ArrayList<User>();
		try {
			lastDrawID = Main.dataService.getLastDrawID();
		} catch(NullPointerException e) {
			lastDrawID = 0;
		}
	}
	
	public User addTicket(User user, List<Integer> fields, Integer type) {
		Ticket ticket = new Ticket();
		ticket.setDate(LocalDateTime.now());
		ticket.setDrawID(lastDrawID+1);
		ticket.setFields(fields);
		ticket.setPlayer(user);
		ticket.setTypeID(type);
		if(type < 3)
			user.setBalance(user.getBalance()-225);
		else
			user.setBalance(user.getBalance()-175);
		newTickets.add(ticket);
		return user;
	}
	
	public void draw() {
		lastDrawID++;
		oldTickets = newTickets;
		newTickets.clear();
		
		drawOTOS();
		drawHATOS();
		drawSKANDINAV();
	}
	
	public Integer hits(Draw draw, Ticket ticket) {
		int db = 0;
		for(Integer i : draw.getFields()) {
			if(ticket.getFields().contains(i))
				db++;
		}
		ticket.setHits(db);
		return db;
	}
	
	private void drawSKANDINAV() {
		int fullBalance = Main.dataService.getFullBalance(1);
		Draw draw = new Draw();
		draw.setId(lastDrawID+1);
		draw.setTicketType(2);
		
		List<Integer> list = new ArrayList<>();
		Random rand = new Random();
		while(list.size() < 7) {
			int number = 1 + rand.nextInt(35);
			if(!list.contains(number))
				list.add(number);
		}
		
		draw.setFields(list);
		draw.setDate(LocalDateTime.now());
		List<Ticket> tickets = oldTickets.stream()
				.filter(t -> t.getTypeID() == 1)
				.collect(Collectors.toList());
		pay(draw, tickets, fullBalance);
	}

	private void drawHATOS() {
		int fullBalance = Main.dataService.getFullBalance(1);
		Draw draw = new Draw();
		draw.setId(lastDrawID+1);
		draw.setTicketType(1);
		
		List<Integer> list = new ArrayList<>();
		Random rand = new Random();
		while(list.size() < 6) {
			int number = 1 + rand.nextInt(45);
			if(!list.contains(number))
				list.add(number);
		}
		draw.setFields(list);
		draw.setDate(LocalDateTime.now());
		
		List<Ticket> tickets = oldTickets.stream()
				.filter(t -> t.getTypeID() == 1)
				.collect(Collectors.toList());
		pay(draw, tickets, fullBalance);
	}

	private void drawOTOS() {
		int fullBalance = Main.dataService.getFullBalance(0);
		Draw draw = new Draw();
		draw.setId(lastDrawID+1);
		draw.setTicketType(0);
		
		List<Integer> list = new ArrayList<>();
		Random rand = new Random();
		while(list.size() < 5) {
			int number = 1 + rand.nextInt(90);
			if(!list.contains(number))
				list.add(number);
		}
		
		draw.setFields(list);
		draw.setDate(LocalDateTime.now());
		
		List<Ticket> tickets = oldTickets.stream()
				.filter(t -> t.getTypeID() == 0)
				.collect(Collectors.toList());
		pay(draw, tickets, fullBalance);
	}
	
	public void pay(Draw draw, List<Ticket> tickets, Integer fullBalance) {
		int[] db = {0, 0, 0, 0};
		int[] minHits = {2, 3, 4};
		double[][] odds = {{0.35, 0.18, 0.17, 0.30}, {0.35, 0.10, 0.10, 0.45}, {0.41, 0.14, 0.14, 0.31}};
		for(Ticket ticket : tickets) {
			ticket.setHits(hits(draw, ticket));
			if(ticket.getHits() >= minHits[ticket.getTypeID()])
				db[ticket.getHits()-minHits[ticket.getTypeID()]]++;
		}
		for(Ticket ticket : tickets) {
			if(ticket.getHits() >= minHits[ticket.getTypeID()]){
				int balance = ticket.getPlayer().getBalance();
				balance += ((fullBalance * odds[ticket.getTypeID()][ticket.getHits() - minHits[ticket.getTypeID()]])
						/ db[ticket.getHits() - minHits[ticket.getTypeID()]]);
				ticket.getPlayer().setBalance(balance);
			} else
				continue;
			if(!winners.contains(ticket.getPlayer()))
				winners.add(ticket.getPlayer());
		}
	}
	
}
