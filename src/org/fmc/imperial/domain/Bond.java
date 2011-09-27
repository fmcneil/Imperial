package org.fmc.imperial.domain;

public class Bond {

	private Country country;
	private Player owner;
	private int id;
	
	public Bond(int i, Country c) {
		this.id=i;
		this.country = c;
		this.owner=null;
	}
	
	public int getId() { return id; }
	public Country getCountry() { return country;}
	public Player getOwner() { return owner; }
	public int getValue() { 
		int res = 0;
		switch (id) {
		case 1:
			res = 2;
			break;
		case 2:
			res = 4;
			break;
		case 3:
			res = 6;
			break;
		case 4:
			res = 9;
			break;
		case 5:
			res = 12;
			break;
		case 6:
			res = 16;
			break;
		case 7:
			res = 20;
			break;
		case 8:
			res = 25;
			break;
		case 9:
			res = 30;
			break;
		default:
			res = -1;
			break;
		}
		return res; 
	}
	
	public void setOwner(Player p) { owner = p; }
}
