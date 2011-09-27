package org.fmc.imperial.messages;

public class MessageConsumer extends Thread { 
    MessageBox mBox; 
    MessagerI dad;
    boolean loop = true;
    
    public MessageConsumer(MessageBox p, MessagerI d) { 
        mBox = p; 
        dad = d;
    } 
  
    public void shutdown() { 
    	System.out.println("["+dad.getClass().getName()+"-MessageConsumer] shutdown with "+mBox.size()+" messages left.");
    	
    	if (mBox.size() > 0) {
    		try {
				System.out.println("["+dad.getClass().getName()+"-MessageConsumer] "+mBox.getMessage().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	loop = false; 
    }
    
    public void run() { 
        try { 
            while ( loop ) { 
                Message message = mBox.getMessage(); 
                message.doAction(dad);
                //mBox.clear();
                sleep( 100 ); 
            } 
        }  
        catch( InterruptedException e ) { } 
    } 

} 
