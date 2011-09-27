package org.fmc.imperial.engine;

import java.util.Collection;
import java.util.Iterator;

import org.fmc.imperial.Game;
import org.fmc.imperial.domain.*;
import org.fmc.imperial.messages.*;

// TODO : Engine must be a Runnable state-machine. It must be able to wait until
// it receives the expected reply from the Player it's waiting after.

public class Engine implements MessagerI, Runnable {

	public final static int STATE_NEW_GAME = 0;
	public final static int STATE_RUNNING  = 1;
	public final static int STATE_WAITING  = 2;
	
	private int state;
	private Game papa;
	private MessageBox msgBox;
	private MessageConsumer msgConsumer;
	private Thread gameLoop;
	
	public Engine(Game dad) { 
		papa = dad;
		state = STATE_NEW_GAME;
		msgBox = new MessageBox();
		msgConsumer = new MessageConsumer(msgBox, this);
		msgConsumer.start();
	}
	
	public void start() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void launchGame() {
		// Launch the game
		papa.setGameOver(false);
		
		for (int i=Game.RUSSIA; i<=Game.EUROPE; i++) {
			Country c = papa.getCountryAt(i);
			Player p = c.getOwner();
			
			sendMessage(p, new MsgNewGame(i, c));
		}
		
		if (state == STATE_NEW_GAME) {
			state = STATE_RUNNING;
		}		
	}
	
	public void shutdown() {
		msgConsumer.shutdown();
		gameLoop = null;
	}
	
	public void calculatePlayersFortune() {
		// For each country
		Collection<Country> countries = papa.getCountries();
		Iterator<Country> ite = countries.iterator();
		while (ite.hasNext()) {
			Country c = ite.next();
			// Get all bonds
			Collection<Bond> bonds = c.getAvailableBonds(false).values();
			Iterator<Bond> iteb = bonds.iterator();
			// For each bond
			while (iteb.hasNext()) {
				Bond b = iteb.next();
				// Add it's value to it's owner
				Player p = b.getOwner();
				p.incCash(b.getId() * c.getCoeff());
			}
		}
		
		for (int i=Game.RUSSIA; i<=Game.EUROPE; i++) {
			Player p = papa.getPlayerAt(i);
			papa.displayText("[Engine.calculateFortunes] "+p.getName()+" : $"+p.getCash()+" millions.");
		}
	}

	@Override
	public void receiveMessage(Message m) {
		try {
			msgBox.putMessage(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(MessagerI ms, Message m) {
		ms.receiveMessage(m);
		
	}

	@Override
	public void run() {
		int turnNb = 0;

		// Launch the new game
		launchGame();
		
		while (gameLoop != null) {
			// loop
			try {
				while (!papa.getGameOver()) {
//					turnNb++;
//					papa.displayText(" ### Turn : "+turnNb);
/*  TODO : put in handling methods.
					// Ask the wheel selection
					if (c.getWheelPosition() == -1) {
						// First move
						MsgWheelSelection msg = new MsgWheelSelection(i, c);
						sendMessage(p, msg);
						state = STATE_WAITING;
					} else {
						// Regular move
					}
					// Process with selection
*/

					if (state == STATE_RUNNING) {
						Country c = papa.getCountryAt(papa.getCurrentCountry());
						Player p = c.getOwner();
						papa.displayText("[Engine] TestTurn : "+p.getName()+" for "+c.getName());
	
						// TODO : remove this hardcoded test
						MsgWheelSelection msg = new MsgWheelSelection(papa.getCurrentCountry(), c);
						sendMessage(p, msg);
						state = STATE_WAITING;				
						
						if (c.getPower() == 25) {
							// Did a country reach the limit ?
							papa.setGameOver(true);
						} else {		
							// Otherwise keep playing
							papa.incCountry();
						}
	
						// TODO : remove this hardcoded protection
						if (turnNb >= 5) {
							papa.displayText("[Engine] (after "+turnNb+" turns: Forcing END_OF_GAME.");
							this.shutdown();
							papa.setGameOver(true);
						}
					}
					
					Thread.sleep(100);
				}
				
				// Calculate every player's fortune
				calculatePlayersFortune();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
