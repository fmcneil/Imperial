package org.fmc.imperial.domain;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.fmc.imperial.XMLReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Country {
	
	// 2030 small initial bonds
	// Russia --> Europe
	// Europe --> India
	// India  --> Brazil
	// Brazil --> China
	// China  --> USA
	// USA    --> Russia
	
	private String name = null;
	private static Map<String, Country> countries = new HashMap<String, Country>();
	private List<Territory> territories = new ArrayList<Territory>();
	Player owner = null;
	int wheel_position;
	
	Hashtable<Integer, Bond> bonds = new Hashtable<Integer, Bond>();
	
	int power = 0; // 25 = wins
	int cash = 0;  // in millions

	private Country(String name){
		this.name = name;
		owner = null;
		wheel_position = -1;
		for (int i=1; i<=9; i++) {
			bonds.put(i, new Bond(i, this));
		}
	}
	
	public int getWheelPosition() { return wheel_position; }
	public void setWheelPosition(int i) { wheel_position = i; }
	
	public Hashtable<Integer,Bond> getBonds() { return bonds; }
	
	public Bond getBondByValue(int v) {
		Bond res = null;
		Enumeration<Bond> bs = bonds.elements();
		while (bs.hasMoreElements() && res == null) {
			Bond b = bs.nextElement();
			if (b.getValue() == v) {
				res = b;
			}
		}
		return res;
	}
	
	public boolean assignBondToFor(int bondId, Player buyer, int amountPaid) {
		boolean success = false;
		Bond b = bonds.get(bondId);
		if (b != null) {
			if (b.getOwner() != null) {
				// it's not free
				System.out.println("[Country.assignBondTo] bond is owned already. Bond:"+bondId);
			} else {
				if (amountPaid < b.getValue()) {
					// It's an upgrade
					int value_to_return = b.getValue() - amountPaid;
					Bond toReturn = getBondByValue(value_to_return);
					if (toReturn.getOwner() == buyer) {
						toReturn.setOwner(null);
						incCash(amountPaid);
						b.setOwner(buyer);
						success = true;
					} else {
						System.out.println("[Country.assignBondTo] "+buyer.getName()+" is trying to upgrade a bond to #"+bondId+" by paying "+amountPaid+" but bond #"+toReturn.getId()+" is not his...");
					}
				} else {
					// Regular purchase
					incCash(amountPaid);
					b.setOwner(buyer);
					success = true;
				}
				
			}
		} else {
			System.out.println("[Country.assignBondToFor] invalid bond id:"+bondId);
		}
		return success;
	}
	
	public Hashtable<Integer,Bond> getAvailableBonds(boolean available) {
		Hashtable<Integer, Bond> res = new Hashtable<Integer,Bond>();
		
		for (int i=1; i<=9; i++) {
			Bond b = bonds.get(i);
			if (b.getOwner()==null && available) {
				// Return all available
				res.put(i, b);
			}
			if (b.getOwner()!=null && !available) {
				// Return all owned
				res.put(i, b);
			}
		}
		
		return res;
	}
	
	public int getPower() { return power; }
	public int getCoeff() { return power % 5; }
	public void incPower(int i) { power += i; }
	public void incCash(int i) { cash += i; }
	
	public String getName(){return this.name;}	
	public void setName(String name){this.name = name;}
	
	public void setOwner(Player p) { 
		owner = p; 
	}
	public Player getOwner() { return owner; }
	
	public void takeControl(Territory territory){
		if(territory.getOwner() != null)
			territory.getOwner().looseControl(territory);
		
		territories.add(territory);
		territory.owner = this;
	}
	
	public void looseControl(Territory territory){
		territories.remove(territory);
		territory.owner = null;
	}
	
	public List<Territory> getTerritories(){
		return territories;
	}
		
	public static synchronized Country getCountry(String name){
		if(countries.get(name) == null){
			Element e = XMLReader.getInstance().getCountry(name);
			if(e == null){
				System.out.println("Unfound country : " + name);
				return null;
			}
			
			Country country = new Country(e.getAttribute("name"));
			countries.put(e.getAttribute("name"), country);
			
			NodeList nodeLst = e.getChildNodes();
			
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element node = (Element) fstNode;
					if("Homeland".equalsIgnoreCase(node.getNodeName())){
						Territory territory = Territory.getTerritory(node.getAttribute("name"));
						country.takeControl(territory);
						territory.homeland = true;
					}
				}
			}
//			System.out.println("[Country] Loaded : <"+country.getName()+">");

		}
		
		return countries.get(name);
	}
}
