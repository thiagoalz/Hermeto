package net.hermeto.android.test;

import net.hermeto.android.main.XMPPClient;

public class XMPPLilabTestCaseImpl extends AbstractXMPPTestCase {

	@Override
	protected XMPPClient constructXMPPClient() {
		return new XMPPClient(5222, "b", "lilab.info", "a", "123456");
	}
	


}
