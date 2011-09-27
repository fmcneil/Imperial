package org.fmc.imperial.ai;

public abstract class Ai implements Runnable {
	
	Thread loop;	
	int id;
	String name = "AI";
	double cash = 0;

	public Ai(int i, String n) {
		this.id   = i;
		this.name = n;
		
		loop = new Thread(this);
		loop.start();
	}
	
	public void stop() {
		shutdown(); 	// let the AI handle it's own cleanup.
		loop = null;
	}
	
	public abstract void shutdown();	
	public abstract void run();
	
	public void setId(int i) { id = i;}
	public void setName(String n) { name = n; }
	
	public int getId() { return id; }
	public String getName() { return name; }
}
