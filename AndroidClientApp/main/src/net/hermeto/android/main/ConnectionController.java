package net.hermeto.android.main;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

public class ConnectionController implements MessageListener {
	protected Main mHostActivity;
	protected XMPPClient chatClient;
	protected String nickname;
	protected String clientID;
	protected Status connectionStatus;
	
	private final String SERVER_LOGIN="a@lilab.info";
	private final String SERVER_ADDRESS="lilab.info";
	private final String CLIENT_LOGIN="b";
	private final String CLIENT_PASSWORD="123456";

	protected enum Status {
		DISCONNECTED, WAITING_RESPONSE, CONNECTED
	}

	public ConnectionController(Main activity) {
		mHostActivity = activity;
		this.connectionStatus = Status.DISCONNECTED;
	}

	protected View findViewById(int id) {
		return mHostActivity.findViewById(id);
	}

	protected Resources getResources() {
		return mHostActivity.getResources();
	}

	public void upClick() {
		if (this.connectionStatus == Status.CONNECTED) {
			try {
				chatClient.sendMessage(clientID + " Up");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.disconnectedMessage();
		}
	}

	public void downClick() {
		if (this.connectionStatus == Status.CONNECTED) {
			try {
				chatClient.sendMessage(clientID + " Down");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.disconnectedMessage();
		}
	}

	public void leftClick() {
		if (this.connectionStatus == Status.CONNECTED) {
			try {
				chatClient.sendMessage(clientID + " Left");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.disconnectedMessage();
		}
	}

	public void rightClick() {
		if (this.connectionStatus == Status.CONNECTED) {
			try {
				chatClient.sendMessage(clientID + " Right");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.disconnectedMessage();
		}
	}

	public void buttonClick() {
		if (this.connectionStatus == Status.CONNECTED) {
			try {
				chatClient.sendMessage(clientID + " Button");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.disconnectedMessage();
		}
	}

	private void disconnectedMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mHostActivity);
		builder.setMessage("You need to Connect first!");
		AlertDialog alert = builder.create();		
		alert.show();		
	}

	public void connect(String nickname) {
		this.nickname = nickname;
		if (chatClient != null) {
			chatClient.disconnect();
			chatClient = null;
			this.connectionStatus = Status.DISCONNECTED;
		}

		try {
			this.chatClient = new XMPPClient(5222, SERVER_LOGIN, SERVER_ADDRESS, CLIENT_LOGIN, CLIENT_PASSWORD);
			Log.d("XMPP", "Conected");
			this.chatClient.startChat(this);
			Log.d("XMPP", "Chat Started");
			this.connectionStatus = Status.WAITING_RESPONSE;
			this.chatClient.sendMessage("HELLO " + nickname);
			Log.d("XMPP", "Hello Message Sent: HELLO "+ nickname);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void processMessage(Chat chat, Message message) {
		Log.d("XMPP", "Message Received");
		if ((this.connectionStatus == Status.WAITING_RESPONSE)
				&& (message.getType() == Message.Type.chat)) {
			
			Log.d("XMPP", "Good Message");
			Log.d("XMPP",chat.getParticipant() + " says: " + message.getBody());
			
			String[] sMessage = message.getBody().split(" ");
			if (chat.getParticipant().equals(SERVER_LOGIN)
					&& sMessage[0].equals("HELLO")
					&& sMessage[1].equals(this.nickname)) {
				this.clientID=sMessage[2];
				this.connectionStatus = Status.CONNECTED;
				Log.d("XMPP", "Connected");
				mHostActivity.toastConnected();
			}
		} else {
			Log.d("XMPP", "Unknown Message("+message.getType()+"): " + message.getBody());
		}
	}

}
