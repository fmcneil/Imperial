package org.fmc.imperial.messages;

import java.util.Vector;

public class MessageProducer extends Thread {

    static final int MAXQUEUE = 1;	// Consumer must consume before getting another one
    private Vector<Message> messages = new Vector<Message>(); 

    public void run() { 
        try { 
            while ( true ) { 
                putMessage(); 
                sleep( 1000 ); 
            } 
        }  
        catch( InterruptedException e ) { } 
    } 
 
    private synchronized void putMessage() 
        throws InterruptedException { 
        
        while ( messages.size() == MAXQUEUE ) {
            wait(); 
        }
        messages.addElement( new Message(0,0,"Default message") ); 
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
} 
