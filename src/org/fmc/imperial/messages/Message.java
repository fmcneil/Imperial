package org.fmc.imperial.messages;

public abstract class Message {

	public final static int NEW_GAME     = 0;
	public final static int SELECT_WHEEL = 1;
	
	private long id;
	private String note;
	
	public Message(int i, String n) {
		this.id = i;
		this.note = n;
	}
	
	public abstract void doAction(MessagerI dad);
	
	public String toString() {
		return "msg #"+id+" : "+note;
	}
}
