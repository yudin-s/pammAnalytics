package org.server.auth;

import org.shared.api.ver2.PammException;
import org.shared.api.ver2.dto.ClientDTO;

public class AuthManager implements IAuth {
	private static AuthManager instance = null;

	public static AuthManager getInstance() {
		if (instance == null) {
			instance = new AuthManager();
		}
		return instance;
	}

	@Override
	public boolean authClient(ClientDTO client) throws PammException {
		return true;
	}

}
