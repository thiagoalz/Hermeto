package net.hermeto.android.test;

import org.jivesoftware.smack.XMPPException;

import net.hermeto.android.main.XMPPClient;

public class XMPPLilabTestCaseImpl extends AbstractXMPPTestCase {

	@Override
	protected XMPPClient constructXMPPClient() {
		try {
			return new XMPPClient(5222, "b@lilab.info", "lilab.info", "a", "123456", "vovozinha");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
