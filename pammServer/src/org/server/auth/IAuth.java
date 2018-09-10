package org.server.auth;

import org.shared.api.ver2.PammException;
import org.shared.api.ver2.dto.ClientDTO;

public interface IAuth {
	public boolean authClient(ClientDTO client) throws PammException;
}
