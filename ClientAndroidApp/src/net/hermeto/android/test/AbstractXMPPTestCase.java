package net.hermeto.android.test;

import org.jivesoftware.smack.XMPPException;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import dalvik.annotation.TestTargetClass;
import net.hermeto.android.main.XMPPClient;
import junit.framework.TestCase;

@TestTargetClass(value = XMPPClient.class)
public abstract class AbstractXMPPTestCase extends TestCase {
	
	private XMPPClient client;	
	
	@Override
	protected void tearDown() throws Exception {
		this.client.disconnect();
		super.tearDown();
	}
	
	@SmallTest
	public void testXMPPServer() {

		String msg = "Test message;";

		try {
			this.client.sendMessage(msg);
		} catch (XMPPException e) {
			Log.e("hermeto","XMPP Message Sending Failed");			
			e.printStackTrace();
		}

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.client = this.constructXMPPClient();
	}
	
	protected abstract XMPPClient constructXMPPClient();
}
