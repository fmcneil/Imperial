package org.fmc.imperial.domain;

import java.util.ArrayList;
import java.util.List;

import org.fmc.imperial.ai.RandomAI;
import org.fmc.imperial.messages.Message;
import org.fmc.imperial.messages.MessageBox;
import org.fmc.imperial.messages.MessageConsumer;
import org.fmc.imperial.messages.MessagerI;

public class Player implements MessagerI {

	// The Players and the GameEngine will both have a MessageBox and
	// a MessageConsumer to communicate asynchronously with each other.
	MessageBox msgBox;
	MessageConsumer msgConsumer;

	int id;
	String name;
	long cash;
	List<Country> countries;
	boolean isHuman;
	
	RandomAI ai;
	
	public Player(int i, boolean human) {
		id = i;
		isHuman = human;
		setName("Nobody");
		setCash(13L);
		countries = new ArrayList<Country>();
		
		if (!isHuman) { 
			ai = new RandomAI(i);
		}
		msgBox = new MessageBox();
		msgConsumer = new MessageConsumer(msgBox, this);
		msgConsumer.start();
	}
	
	public void setGameOver(boolean f) {
		if (f) {
			if (!isHuman) {
				ai.stop();
			}
			msgConsumer.shutdown();
		}
	}
	
	public boolean isHuman() { return isHuman; }
	
	public void setCash(long c) { cash = c; }
	public long getCash() { return cash; }
	public void setName(String n) { name = n; }
	public String getName() { 
		return (isHuman) ? name : ai.getName(); 
	}
	public int getId() { return id; }
	
	public int getNbCountries() { return countries.size(); }
	
	public boolean addCountry(Country c) { 
		boolean res = false;
		if (!countries.contains(c)) {
			c.setOwner(this);
			countries.add(c);
			res = true;
		}
		return res;
	}
	
	public boolean removeCountry(Country c) {
		boolean res = false;
		if (!countries.contains(c)) {
			c.setOwner(null);
			countries.remove(c);
			res = true;
		}
		return res;		
	}
	
	public long incCash(long c) { 
		setCash(getCash() + c);
		return getCash();
	}

	@Override
	public void sendMessage(MessagerI ms, Message m) {
		ms.receiveMessage(m);
	}

	@Override
	public void receiveMessage(Message m) {
		try {
			msgBox.putMessage(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void assignInitialCountry(Country country) {
		// Add the country
		// Pay the $9M
		
	}

	public void getWheelSelection(Country country) {
		// select the move and send the result to the Engine.
		
	}
}
