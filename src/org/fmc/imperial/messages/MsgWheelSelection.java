package org.fmc.imperial.messages;

import org.fmc.imperial.domain.Country;
import org.fmc.imperial.domain.Player;
import org.fmc.imperial.engine.Engine;

public class MsgWheelSelection extends Message {

	private Country country;
	private int wheel_selection;
		
	public MsgWheelSelection(int i, Country c) {
		super(i, "MsgWheelSelection");
		country = c;
		wheel_selection = -1;
	}

	public void setCountry(Country i) { country = i; }
	public void setWheelSelection(int i) { wheel_selection = i; }
	
	@Override
	public void doAction(MessagerI p) {
		if (p instanceof Player) {
			System.out.println("[MsgWheelSelection.doAction] "+((Player)p).getName()+" must select a move for "+country.getName()+" and reply to the Engine");
			((Player)p).getWheelSelection(country);
		}
		if (p instanceof Engine) { 
			System.out.println("[MsgWheelSelection.doAction] Engine must handle the reply from "+country.getName());
			// TODO: Validate the wheel_selection
				// Return a rejected message OR
				// Move on and ask for the move itself (unless he moved over Investor in which case we must ask the
				// players without country if he must stop at Investor (if allowed and if the wheel_selection isn't INVESTOR already
		}
	}

}
