package net.hermeto.android.test;

import org.jivesoftware.smack.XMPPException;

import net.hermeto.android.main.XMPPClient;

public class XMPPGtalkTestCaseImpl extends AbstractXMPPTestCase {

	@Override
	protected XMPPClient constructXMPPClient() {
		try {
			return new XMPPClient(5222, "[DESTINATION]@gmail.com", "talk.google.com", "[USERNAME]@gmail.com", "[PASSWORD]", "Hermeto");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
