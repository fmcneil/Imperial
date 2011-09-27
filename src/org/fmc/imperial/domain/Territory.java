package org.fmc.imperial.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fmc.imperial.XMLReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Territory {

	String name;
	boolean land;
	Country owner;
	boolean homeland;
	Factory factory;
	boolean initialFactory;
	
	List<Territory> adjacentTerritories = new ArrayList<Territory>();
	
	private Territory(String name, boolean land){
		this.name = name;
		this.land = land;
		owner = null;
		homeland = false;
		factory = null;
		initialFactory = false;
	}
	
	private Territory(String name, boolean land, Factory factory, boolean factoryPresent){
		this.name = name;
		this.land = land;
		this.factory = factory;
		this.initialFactory = factoryPresent;
	}
	
	public void createFactory() {
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public Country getOwner(){
		return this.owner;
	}
	
	public Factory getFactory(){
		return this.factory;
	}
	
	public boolean isFactoryPresent(){
		return initialFactory;
	}
	
	public boolean isHomeland(){
		return homeland;
	}
	
	public List<Territory> getAdjacentTerritories(){
		return this.adjacentTerritories;
	}
	
	private static Map<String, Territory> territories = new HashMap<String, Territory>();
	
	public static synchronized Territory getTerritory(String name){
		if(territories.get(name) == null){
			Element e = XMLReader.getInstance().getTerritory(name);
			if(e == null) {
				System.out.println("Unfound territory : " + name);
				return null;
			}
			
			Territory territory = new Territory(
					e.getAttribute("name"), 
					"true".equals(e.getAttribute("land")), 
					"army".equals(e.getAttribute("factory")) ? new Factory(Factory.INFANTRY) : ("navy".equals(e.getAttribute("factory")) ? new Factory(Factory.FLEET) : null), 
					"true".equals(e.getAttribute("mandatory")));
			
			territories.put(name, territory);
			
			NodeList nodeLst = e.getElementsByTagName("Adjacent");
			
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element node = (Element) fstNode;
					territory.getAdjacentTerritories().add(Territory.getTerritory(node.getAttribute("name")));
				}
			}
		}
		
		return territories.get(name);
	}
}