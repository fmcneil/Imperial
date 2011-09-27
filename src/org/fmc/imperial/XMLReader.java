package org.fmc.imperial;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

	private Document doc;
	
	private XMLReader() {
		try{
			File file = new File("World.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Element getCountry(String country){
		
		NodeList nodeLst = doc.getElementsByTagName("Country");
		
		for (int s = 0; s < nodeLst.getLength(); s++) {
			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element node = (Element) fstNode;
				if(country.equalsIgnoreCase(node.getAttribute("name"))){
					return node;
				}
			}
		}
		
		return null;
	}
	
	public Element getTerritory(String territory){

		NodeList territoryLst = doc.getElementsByTagName("Territory");
				
		for (int t = 0; t < territoryLst.getLength(); t++) {
			Node node = territoryLst.item(t);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element ter = (Element) node;
				if(territory.equalsIgnoreCase(ter.getAttribute("name"))){
					return ter;
				}
			}
		}
		
		return null;
	}
	
	private static XMLReader instance = new XMLReader();
	
	public static XMLReader getInstance(){
		return instance;
	}
}
