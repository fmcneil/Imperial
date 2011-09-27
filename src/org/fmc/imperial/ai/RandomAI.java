package org.fmc.imperial.ai;

public class RandomAI extends Ai {
	
	long delay = 1000;

	public RandomAI(int i) { 
		super(i, "RandomAI_"+i);
	}

	public void run() {
		
		System.out.println("["+getName()+".run] launched...");
		
		while (loop != null) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				System.out.println("[Ai.run] interrupted exception while sleeping.");
			}
		}
		
		System.out.println("["+getName()+".run] stopped...");
		
		// TODO: if I had a handle on the Player, I could stop the MessageConsumer...
	}
	
	public void shutdown() {
//		System.out.println("["+getName()+".shutdown] ..."); 
	}
	
}
