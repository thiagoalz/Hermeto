package net.hermeto.android.test;

import junit.framework.TestCase;
import net.hermeto.android.main.XMPPClient;

import org.jivesoftware.smack.XMPPException;

import dalvik.annotation.TestTargetClass;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

@TestTargetClass(value = XMPPClient.class)
public class XMPPLilabTestCaseImpl extends TestCase {
	
	private XMPPClient client;	

	public XMPPLilabTestCaseImpl(String name) {
		super(name);
	}
	
	@SmallTest
	public void testLilabServer() {

		String msg = "Test message;";

		try {
			this.client.login("a@lilab.info", "123456", "lilab.info", 5222);
		} catch (XMPPException e) {
			Log.e("hermeto","XMPP Authentication Failed");
			e.printStackTrace();
		}

		try {
			this.client.sendMessage(msg, "b@lilab.info");
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
