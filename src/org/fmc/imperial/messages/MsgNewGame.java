package org.fmc.imperial.messages;

import org.fmc.imperial.domain.Country;
import org.fmc.imperial.domain.Player;

public class MsgNewGame extends Message {

	private Country country;
		
	public MsgNewGame(int i, Country c) {
		super(i, "MsgNewGame");
		country = c;
	}

	public void setCountry(Country i) { country = i; }
	
	@Override
	public void doAction(MessagerI p) {
		if (p instanceof Player) {
			System.out.println("[MsgNewGame.doAction] "+((Player)p).getName()+" received "+country.getName());
			((Player)p).assignInitialCountry(country);
		}
	}

}
