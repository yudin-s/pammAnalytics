package org.parser.fxtrend.parser;

import java.sql.Date;

import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtOfferData;

public interface IFxtData {
	public boolean setFxtAccountsData(int idBroker, FxtAccountsData data);

	public int getFxtAccountID(int idBroker, int accountNumber);

	public int getFxtGetAccountNumberByAccountID(int accountID);

	public boolean setFxtAddOffer(int brokerId, FxtOfferData data);

	public boolean setFxtProfitByWeek(int brokerId, Object[] objects, int accountID);

	public boolean setFxtProfitByMounth(int brokerId, int accountID, Object[] data);

	public boolean setFxtDealHistory(int brokerId, Object[] data, int accountID);

	public abstract Date getFxtLastDateByMounthProfit(int brokerId, int accountNumber);

	public abstract Date getFxtLastDateByWeekProfit(int brokerId, int accountNumber);

	public abstract Date getFxtLastDateByDeals(int brokerId, int accountNumber);

}
