package net.hermeto.android.test;

import net.hermeto.android.main.XMPPClient;

import org.jivesoftware.smack.XMPPException;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import junit.framework.TestCase;

public class XMPPGtalkTestCaseImpl extends TestCase {

	private XMPPClient client;		
	
	@SmallTest
	public void testGtalkServer() {

		String msg = "Test message;";

		try {
			this.client.login("[USER]@gmail.com", "[PASS]@gmail.com", "talk.google.com", 5222);
		} catch (XMPPException e) {
			Log.e("hermeto","XMPP Authentication Failed");
			e.printStackTrace();
		}

		try {
			this.client.sendMessage(msg, "[USER-DEST]@gmail.com");
		} catch (XMPPException e) {
			Log.e("hermeto","XMPP Message Sending Failed");			
			e.printStackTrace();
		}

	}		
	
	
	@Override
	protected void tearDown() throws Exception {
		this.client.disconnect();
		super.tearDown();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.client = new XMPPClient();
	}	
	
}
