package org.shared.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import org.shared.api.ver2.dto.ClientDTO;

public interface IFxTrendRMI extends Remote {
	public List<?> getFxTAccounts(ClientDTO client) throws RemoteException;

	public List<?> getFxTOffers(ClientDTO client) throws RemoteException;

	public List<?> getFxTNewMounthProfit(Date lastDate, int accountNumber,
			ClientDTO client) throws RemoteException;

	public List<?> getFxtNewWeekProfit(Date lastDate, int accountNumber,
			ClientDTO client) throws RemoteException;

	public List<?> getFxTNewDealsProfit(Date lastDate, int accountNumber,
			ClientDTO client) throws RemoteException;

	public List<?> getFxTNewCapitalAdminAndInvestorProfit(Date lastDate,
			int accountNumber, ClientDTO client) throws RemoteException;

	public Serializable getFxTOfferById(int OfferId, ClientDTO client)
			throws RemoteException;
}
