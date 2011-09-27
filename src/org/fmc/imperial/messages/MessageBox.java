package org.fmc.imperial.messages;

import java.util.Vector;

public class MessageBox {

    static final int MAXQUEUE = 1;	// Consumer must consume before getting another one
    private Vector<Message> messages = new Vector<Message>(); 

    public synchronized void putMessage(Message m) 
        throws InterruptedException { 
        
        while ( messages.size() == MAXQUEUE ) {
            wait(); 
        }
        messages.addElement( m ); 
        notify(); 
    } 
 
    // Called by Consumer 
    public synchronized Message getMessage() 
        throws InterruptedException { 
        notify(); 
        while ( messages.size() == 0 ) {
            wait(); 
        }
        Message message = messages.firstElement(); 
        messages.removeElement( message ); 
        return message; 
    }

	public void clear() {
		messages.clear();
	}

	public int size() {
		return messages.size();
	} 
} 
