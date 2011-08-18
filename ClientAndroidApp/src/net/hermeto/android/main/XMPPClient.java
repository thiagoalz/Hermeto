package net.hermeto.android.main;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class XMPPClient implements MessageListener {
	private static final int SERVER_PORT = 5222;
	private static final String DESTINATION = "a@lilab.info";
	private static final String SERVER_ADDRESS = "lilab.info";
	private static final String USERNAME = "b@lilab.info";
	private static final String PASSWORD = "2";
	
	protected XMPPConnection connection;

	public void login(String userName, String password, String serverAddress, int port) throws XMPPException
	{
		ConnectionConfiguration config = new ConnectionConfiguration(serverAddress, port, "Work");
		connection = new XMPPConnection(config);
		connection.connect();
		connection.login(userName, password);
	}

	public void sendMessage(String message) throws XMPPException
	{
		Chat chat = connection.getChatManager().createChat(XMPPClient.DESTINATION, this);
		chat.sendMessage(message);
	}

	public void disconnect()
	{
		connection.disconnect();
	}

	public void processMessage(Chat chat, Message message)
	{
		if(message.getType() == Message.Type.chat)
			System.out.println(chat.getParticipant() + " says: " + message.getBody());
	}

}
