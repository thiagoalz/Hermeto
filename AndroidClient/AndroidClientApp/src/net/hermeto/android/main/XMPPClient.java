package net.hermeto.android.main;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;
/**
 * Class responsible for managing XMPP connection and messaging
 * @author Paulo Augusto Dacach Bichara
 *
 */
public class XMPPClient implements MessageListener {
	
	/* XMPP server port */
	protected int port;

	/* user destination of all messages */	
	protected String messagesDestination;
	
	/* XMPP server address */	
	protected String serverAddress;

	/* name from local user */	
	protected String username;
	
	/* password from local user */	
	protected String password;
	
	/* XMPP connection */
	protected XMPPConnection connection;

	/* XMPP chat */
	protected Chat chat;	
	
	/**
	 * Constructor for XMPPClient that requires a service name to be set in connection
	 * 
	 * @param port
	 * @param messagesDestination
	 * @param serverAddress
	 * @param username
	 * @param password
	 * @throws XMPPException
	 */
	public XMPPClient(int port, String messagesDestination,	String serverAddress, String username, String password, String serviceName) throws XMPPException {
		super();
		this.port = port;
		this.messagesDestination = messagesDestination;
		this.serverAddress = serverAddress;
		this.username = username;
		this.password = password;
		this.login(this.username, this.password, this.serverAddress, this.port, serviceName);
	}
	
	/**
	 * Constructor for XMPPClient that doesn't requires or supports a service name to be set in connection 
	 * 
	 * @param port
	 * @param messagesDestination
	 * @param serverAddress
	 * @param username
	 * @param password
	 * @param userAlias
	 * @throws XMPPException
	 */
	public XMPPClient(int port, String messagesDestination,
			String serverAddress, String username, String password) throws XMPPException {
		super();
		this.port = port;
		this.messagesDestination = messagesDestination;
		this.serverAddress = serverAddress;
		this.username = username;
		this.password = password;
		this.login(this.username, this.password, this.serverAddress, this.port);
	}	
	
	/**
	 * Login method when not using service name 
	 * 
	 * @param userName
	 * @param password
	 * @param serverAddress
	 * @param port
	 * @throws XMPPException
	 */
	protected synchronized void login(String userName, String password, String serverAddress, int port) throws XMPPException
	{
		ConnectionConfiguration config = new ConnectionConfiguration(serverAddress, port);
		this.connect(config, userName, password);		
	}
	
	/**
	 * Login method when service name is required
	 * 
	 * @param userName
	 * @param password
	 * @param serverAddress
	 * @param port
	 * @param serviceName
	 * @throws XMPPException
	 */
	protected synchronized void login(String userName, String password, String serverAddress, int port, String serviceName) throws XMPPException
	{
		ConnectionConfiguration config = new ConnectionConfiguration(serverAddress, port, serviceName);
		this.connect(config, userName, password);
	}	

	/**
	 * Method that establishes a connection to the XMPP server.
	 * 
	 * @param config
	 * @param userName
	 * @param password
	 * @throws XMPPException
	 */
	protected void connect(ConnectionConfiguration config, String userName, String password) throws XMPPException {
		connection = new XMPPConnection(config);
		connection.connect();
		connection.login(userName, password);		
	}
	
	/**
	 * Method that sends a message to another user from the XMPP service. 
	 * Before send the message to the destination user, it appends user's 
	 * Hermeto nickname to message body.
	 * 
	 * @param message
	 * @throws XMPPException
	 */
	public void sendMessage(String messageTx) throws XMPPException
	{
		if(connection!=null && connection.isConnected()){
			Message OMessage = new Message();
			OMessage.setTo(messagesDestination);
			OMessage.setSubject("Hermeto");
			OMessage.setBody(messageTx);
			OMessage.setType(Message.Type.normal);
		    connection.sendPacket(OMessage);
		}else{
			throw new IllegalStateException("You need to connect first!");
		}
	}
	
	public Message checkMessage(){
		// Accept only messages from server
	    PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class), new FromContainsFilter(messagesDestination));
	    // Collect these messages
	    PacketCollector collector = connection.createPacketCollector(filter);
	    
	    //Timeout 1sec
	    Packet pk= collector.nextResult(1000);
	    
	    Log.d("XMPP","Packet received?: "+pk);
	    Message ret=null;
	    if (pk instanceof Message) {
	    	ret=(Message)pk;
	    }
	    
		return ret;
	}

	/**
	 * Method that ends the connection.
	 */
	public void disconnect()
	{
		connection.disconnect();
	}

	public Chat startChat(MessageListener listener){
		this.chat = connection.getChatManager().createChat(this.messagesDestination, listener);
		
		return chat;
	}	
	
	/**
	 * This method will be invoked whenever a message arrives for local user.
	 */
	@Override
	public void processMessage(Chat chat, Message message)
	{
		if(message.getType() == Message.Type.chat)
			System.out.println(chat.getParticipant() + " says: " + message.getBody());
	}
	
	

}
