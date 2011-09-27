package org.fmc.imperial.messages;


public interface MessagerI {

	MessageBox msgBox = new MessageBox();
	MessageConsumer msgConsumer = new MessageConsumer(msgBox, null);

	public void receiveMessage(Message m);
	public void sendMessage(MessagerI ms, Message m);
}
