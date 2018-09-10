package org.server.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

import org.server.auth.AuthManager;
import org.server.data.FxTDataGetFromDB;
import org.shared.api.ver2.dto.ClientDTO;
import org.shared.rmi.IRMI;

public class RMIImplementation extends UnicastRemoteObject implements IRMI {

	private AuthManager manager;

	public RMIImplementation() throws RemoteException {
		super();
		manager = AuthManager.getInstance();
	}

	private static final long serialVersionUID = 7923191054318348265L;

	@Override
	public List<?> getFxTAccounts(ClientDTO client) throws RemoteException {
		if (client.getApiVer() == 2) {
			manager.authClient(client);
			return FxTDataGetFromDB.getAccounts();
		}
		return null;
	}

	@Override
	public List<?> getFxTOffers(ClientDTO client) throws RemoteException {
		manager.authClient(client);
		List<?> result = null;
		if (client.getApiVer() == 2) {
			result = FxTDataGetFromDB.getOffers();
		}
		return result;
	}

	@Override
	public List<?> getFxTNewMounthProfit(Date lastDate, int accountNumber, ClientDTO client) throws RemoteException {
		manager.authClient(client);
		return FxTDataGetFromDB.getMounthProfit(accountNumber, lastDate);
	}

	@Override
	public List<?> getFxtNewWeekProfit(Date lastDate, int accountNumber, ClientDTO client) throws RemoteException {
		manager.authClient(client);
		return FxTDataGetFromDB.getWeekPofit(accountNumber, lastDate);
	}

	@Override
	public List<?> getFxTNewCapitalAdminAndInvestorProfit(Date lastDate, int accountNumber, ClientDTO client)
			throws RemoteException {
		manager.authClient(client);
		return FxTDataGetFromDB.getDealsPofit(accountNumber, lastDate);
	}

	@Override
	public Serializable getFxTOfferById(int OfferId, ClientDTO client) throws RemoteException {
		return FxTDataGetFromDB.getOfferByOfferId(OfferId);
	}

	@Override
	public List<?> getFxTNewDealsProfit(Date lastDate, int accountNumber, ClientDTO client) throws RemoteException {
		manager.authClient(client);
		return FxTDataGetFromDB.getDealsPofit(accountNumber, lastDate);
	}

}