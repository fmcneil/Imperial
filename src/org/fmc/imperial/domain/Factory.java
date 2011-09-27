package org.fmc.imperial.domain;

public class Factory {

	public static final int INFANTRY = 0;
	public static final int FLEET    = 1;

	private int type;
	
	public Factory(int type) {
		this.type = type;
	}
	
	public int getType() { return type; }
	
	/*
	public Unit Production() { 
		Unit res=null;
		return res;
	}
	*/
}
