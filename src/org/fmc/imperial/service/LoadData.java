package org.fmc.imperial.service;

import java.util.Hashtable;

import org.fmc.imperial.domain.Country;

public class LoadData {

	public static Hashtable<Integer, Country> loadCountriesHardcoded() { 
		Hashtable<Integer, Country> countries = new Hashtable<Integer, Country>();

		countries.put(0, Country.getCountry("Russia"));
		countries.put(1, Country.getCountry("India"));
		countries.put(2, Country.getCountry("China"));
		countries.put(3, Country.getCountry("USA"));
		countries.put(4, Country.getCountry("Brazil"));
		countries.put(5, Country.getCountry("Europe"));
		
		return countries;
	}
}
