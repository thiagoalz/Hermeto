package net.hermeto.android.test;

import net.hermeto.android.main.XMPPClient;

public class XMPPGtalkTestCaseImpl extends AbstractXMPPTestCase {

	@Override
	protected XMPPClient constructXMPPClient() {
		return new XMPPClient(5222, "[DESTINATION]@gmail.com", "talk.google.com", "[USERNAME]@gmail.com", "[PASSWORD]", "Hermeto");
	}

	
}
