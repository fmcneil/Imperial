package org.fmc.imperial;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.fmc.imperial.domain.Country;
import org.fmc.imperial.domain.Player;
import org.fmc.imperial.domain.Territory;
import org.fmc.imperial.engine.Engine;
import org.fmc.imperial.ui.Board;

public class Game {

	// TODO : extend from a generic game
	public static final int RUSSIA = 0;
	public static final int CHINA  = 1;
	public static final int INDIA  = 2;
	public static final int BRAZIL = 3;
	public static final int USA    = 4;
	public static final int EUROPE = 5;
	
	Hashtable<Integer, Player> players;
	Hashtable<Integer, Country> countries;
	
	int investor = CHINA;
	int current_country = RUSSIA;
	boolean gameOver = false;
	
	Board board;
	Engine engine;
	boolean headless;
	
	public Game(Hashtable<Integer, Country> countries, boolean headless) {
		this.headless = headless;
		engine = new Engine(this);		
		this.countries = countries;
		createNPCsAndAssignBonds();
	}
	
	public void setBoard(Board b) { board = b; }
	public Board getBoard() { return board; }
	
	public Engine getEngine() { return engine; }
	public int getCurrentCountry() { return current_country; }
	public Country getCountryAt(int idx) { return countries.get(idx);}
	public Collection<Country> getCountries() { return countries.values(); }
	
	public void displayText(String s) {
		if (!headless) {
			getBoard().addText(s);
		} else {
			System.out.println("[headless] "+s);
		}
	}
	
	public int incCountry() {
		current_country += 1;
		if (current_country > EUROPE) {
			current_country = RUSSIA;
		}
		return current_country;
	}
	
	public int passInvestor() {
		investor += 1;
		if (investor > EUROPE) {
			investor = RUSSIA;
		}
		return investor;
	}
	
	public int getInvestor() { return investor; }
	
	public void setPlayer(Player p) {
		players.put(p.getId(), p);
	}
	
	public Player getPlayerAt(int idx) {
		return players.get(idx);
	}
	
	private void createNPCsAndAssignBonds() {
		players = new Hashtable<Integer,Player>();
		Country c = null;
		
		// create 6 NPCs
		for (int i=RUSSIA; i<=EUROPE; i++) {
			c = countries.get(i);
						
			Player p = new Player(i, false);
			if (c.assignBondToFor(4, p, 9)) {
				p.incCash(-9);
				Country c2 = null;
				switch (i) {
					case RUSSIA:
						c2 = countries.get(EUROPE);
						break;
					case CHINA:
						c2 = countries.get(USA);
						break;
					case INDIA:
						c2 = countries.get(BRAZIL);
						break;
					case BRAZIL:
						c2 = countries.get(CHINA);
						break;
					case USA:
						c2 = countries.get(RUSSIA);
						break;
					case EUROPE:
						c2 = countries.get(INDIA);
						break;
					default :
						System.out.println("[GAME] invalid second country to invest it :"+i);
						break;
				}
				if (c2.assignBondToFor(1, p, 2)) {
					p.incCash(-2);
				} else {
					System.out.println("[Game] failed to assign secondary bond to "+c.getName());
					System.exit(1);
				}
			} else {
				System.out.println("[Game] failed to assign primary bond to "+c.getName());
				System.exit(1);
			}
			p.addCountry(c);
			players.put(i, p);
//			System.out.println("[Game] "+p.getName()+" received "+c.getName());
		}
	}
	
	public boolean isGameOver() { return gameOver; }
	
	public int start() { 
		int winner = -1;
		
		while (!gameOver) {
			Country c = countries.get(current_country);
			Player p = c.getOwner();
			System.out.println("[Game] Turn : "+p.getName()+" for "+c.getName());
			
			gameOver=true;
		}
		
		return winner;
	}

	public void setGameOver(boolean b) { 
		gameOver = b;
		for (int i=0; i<players.size();i++) {
			players.get(i).setGameOver(b);
		}
	}
	public boolean getGameOver() { return gameOver;	}
}
