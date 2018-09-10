package org.server.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.parser.fxtrend.parser.FxTrendParser;
import org.parser.fxtrend.parser.IFxtData;
import org.shared.api.ver2.dto.FxTDealsDTO;
import org.shared.api.ver2.dto.FxTOfferDTO;
import org.shared.api.ver2.dto.FxTPeriodProfitDTO;
import org.shared.api.ver2.dto.FxtAccountsMinimalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxTDataGetFromDB {
	private static final String GENERAL_PROFIT = "GENERAL_PROFIT";
	private static final String CURRENCY_PAIR = "CURRENCY_PAIR";
	private static final String CLOSED_DEAL_DATE = "CLOSED_DEAL_DATE";
	private static final String CAPACITY = "CAPACITY";
	private static final String ADMIN_CAPITAL = "ADMIN_CAPITAL";
	private static final String PERIOD = "PERIOD";
	private static final String Offer_ID = "OFFER_ID";
	private static final String MIN_SUMM_ADD_AND_LEFT = "MIN_SUMM_ADD_AND_LEFT";
	private static final String COMMISSION = "COMMISSION";
	private static final String ADMIN_REWARD = "ADMIN_REWARD";
	private static final String SELECT_IN_FXT_DEAL_HISTORY = "SELECT * FROM FXT_DEAL_HISTORY WHERE ACCOUNT_ID = ? AND CLOSED_DEAL_DATE > ?";
	private static final String WEEK = "WEEK";
	private static final String SELECT_PROFIT_PER_MONTH_BY_ACCOUNT_ID = "SELECT * FROM FXT_PROFIT_PER_MOUNTH WHERE ACCOUNT_ID = ? AND MOUNTH > ?";
	private static final String SELECT_PROFIT_PER_WEEK_BY_ACCOUNT_ID = "SELECT * FROM FXT_PROFIT_PER_WEEK WHERE ACCOUNT_ID = ? AND WEEK > ?";
	private static final String PROFIT_ON_PERCENT = "PROFIT_ON_PERCENT";
	private static final String PROFIT_ON_DOLLAR = "PROFIT_ON_DOLLAR";
	private static final String PROFIT_OF_INVESTOR_ON_PERCENT = "PROFIT_OF_INVESTOR_ON_PERCENT";
	private static final String MOUNTH = "MOUNTH";
	private static final String SELECT_ALL_FROM_FXT_OFFERS = "SELECT * FROM FXT_OFFERS";
	private static final int MINUTES_IN_HOUR = 60;
	private static final int MILISECONDS_IN_SECOND = 1000;
	private static final int SECONDS_IN_MINUTE = 60;
	private static final String PREPARED_OFFER_BY_ID_SELECT = "SELECT * FROM FXT_OFFERS WHERE OFFER_ID = ?";
	private static final String INVALID_SQL = "Invalid SQL: {}";
	private static final String STRATEGY = "STRATEGY";
	private static final String STOPOUT_BY_CA = "STOPOUT_BY_CA";
	private static final String RESPONSIBILITY = "RESPONSIBILITY";
	private static final String DATE_NEX_ROLLOVER = "DATE_NEXT_ROLLOVER";
	private static final String REPORT_LEVEL = "REPORT_LEVEL";
	private static final String MAXIMAL_DROPDOWN = "MAXIMAL_DROPDOWN";
	private static final String DATE_REG = "DATE_REG";
	private static final String CONNECTION_METHOD = "CONNECTION_METHOD";
	private static final String AGENTS_FROM_PROFIT = "AGENTS_FROM_PROFIT";
	private static final String AGENTS_FROM_CALLING = "AGENTS_FROM_CALLING";
	private static final String TYPE = "TYPE";
	private static final String ID_LAST_OFFER = "ID_LAST_OFFER";
	private static final String STATE = "STATE";
	private static final String START_CAPITAL = "START_CAPITAL";
	private static final String NUM_OPEN_DEALS = "NUM_OPEN_DEALS";
	private static final String INVESTOR_CAPITAL = "INVESTOR_CAPITAL";
	private static final String ADMIN_ID = "ADMIN_ID";
	private static final String CURRENT_CAPITAL = "CURRENT_CAPITAL";
	private static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	private static final String AVGRADE_DROPDOWN = "AVGRAGE_DROPDOWN";
	private static final Double NOT_SET = -1.0;
	private static final String SELECT_ACCOUNT_WITH_LAST_OFFERS = "SELECT * FROM FXT_ACCOUNTS LEFT OUTER JOIN FXT_OFFERS ON FXT_ACCOUNTS.ID_LAST_OFFER=FXT_OFFERS.OFFER_ID";
	public static final int ONE_HOUR = MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILISECONDS_IN_SECOND;
	private static Logger log = LoggerFactory.getLogger(FxTDataGetFromDB.class);

	private FxTDataGetFromDB() {
		// For close ability create objects of this class
	}

	public static List<FxtAccountsMinimalDTO> getAccounts() {
		DataManager manager = DataManager.getInstance();
		Statement statement = manager.getStatement();
		ArrayList<FxtAccountsMinimalDTO> list = new ArrayList<FxtAccountsMinimalDTO>();
		try {
			ResultSet set = statement.executeQuery(SELECT_ACCOUNT_WITH_LAST_OFFERS);
			while (set.next()) {
				FxtAccountsMinimalDTO data = new FxtAccountsMinimalDTO();
				data.setAccountNumber(set.getInt(ACCOUNT_NUMBER));
				data.setAvgrageDropdown(set.getDouble(AVGRADE_DROPDOWN));
				data.setCurrentCapital(set.getDouble(CURRENT_CAPITAL));
				data.setIdAdmin(set.getString(ADMIN_ID));
				data.setInvestitions(set.getDouble(INVESTOR_CAPITAL));
				data.setNumOfOpenDeals(set.getInt(NUM_OPEN_DEALS));
				data.setStartCapital(set.getDouble(START_CAPITAL));
				data.setState(set.getString(STATE));
				data.setDateReg(set.getDate(DATE_REG));
				data.setMaxDropdown(set.getDouble(MAXIMAL_DROPDOWN));
				data.setNextRollover(set.getDate(DATE_NEX_ROLLOVER));
				int idLastOffer = set.getInt(ID_LAST_OFFER);
				data.setIdLastOffer(idLastOffer);
				if (accountHasOffer(idLastOffer)) {
					data.setType(set.getInt(TYPE));
					data.setAdminReward(set.getDouble(ADMIN_REWARD));
					data.setAgentsFromCalling(set.getDouble(AGENTS_FROM_CALLING));
					data.setAgentsFromReward(set.getDouble(AGENTS_FROM_PROFIT));
					data.setCommission(set.getDouble(COMMISSION));
					data.setConnecitonMethod(set.getString(CONNECTION_METHOD));
					data.setLevelReport(set.getString(REPORT_LEVEL));
					data.setMinSummAndLeft(set.getDouble(MIN_SUMM_ADD_AND_LEFT));
					data.setPeriod(set.getInt(PERIOD));
					data.setResponsibility(set.getDouble(RESPONSIBILITY));
					data.setStopoutByCA(set.getDouble(STOPOUT_BY_CA));
					data.setStrategy(set.getString(STRATEGY));
				}
				list.add(data);
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return list;
	}

	public static FxTOfferDTO getOfferByOfferId(int OfferID) {
		FxTOfferDTO result = null;
		IData db = DataManager.getInstance();
		PreparedStatement statement = db.getPreperedStatement(PREPARED_OFFER_BY_ID_SELECT);
		try {
			statement.setInt(1, OfferID);
			ResultSet resultSet = statement.executeQuery();
			FxTOfferDTO data = null;
			if (resultSet.first()) {
				data = new FxTOfferDTO();
				int accoutNumber = ((IFxtData) db).getFxtGetAccountNumberByAccountID(resultSet.getInt("ACCOUNT_ID"));
				data.setAccoutNumber(accoutNumber);
				data.setAdminReward(resultSet.getDouble(ADMIN_REWARD));
				data.setAgentsFromCalling(resultSet.getDouble(AGENTS_FROM_CALLING));
				data.setAgentsFromProfit(resultSet.getDouble(AGENTS_FROM_PROFIT));
				data.setCommission(resultSet.getDouble(COMMISSION));
				data.setMinSummAddAndLeft(resultSet.getDouble(MIN_SUMM_ADD_AND_LEFT));
				data.setOfferId(resultSet.getInt(Offer_ID));
				data.setPeriod(resultSet.getInt(PERIOD));
				data.setReportLevel(resultSet.getString(REPORT_LEVEL));
				data.setResponsibility(resultSet.getDouble(RESPONSIBILITY));
				data.setStopoutByCA(resultSet.getDouble(STOPOUT_BY_CA));
				data.setType(resultSet.getInt(TYPE));
			}

			result = data;

		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return result;
	}

	public static List<FxTOfferDTO> getOffers() {
		ArrayList<FxTOfferDTO> result = new ArrayList<FxTOfferDTO>();

		DataManager db = DataManager.getInstance();
		Statement stm = db.getStatement();
		try {
			ResultSet resultSet = stm.executeQuery(SELECT_ALL_FROM_FXT_OFFERS);
			while (resultSet.next()) {
				FxTOfferDTO data = new FxTOfferDTO();
				int accoutNumber = db.getFxtGetAccountNumberByAccountID(resultSet.getInt("ACCOUNT_ID"));
				data.setAccoutNumber(accoutNumber);
				data.setAdminReward(resultSet.getDouble(ADMIN_REWARD));
				data.setAgentsFromCalling(resultSet.getDouble(AGENTS_FROM_CALLING));
				data.setAgentsFromProfit(resultSet.getDouble(AGENTS_FROM_PROFIT));
				data.setCommission(resultSet.getDouble(COMMISSION));
				data.setMinSummAddAndLeft(resultSet.getDouble(MIN_SUMM_ADD_AND_LEFT));
				data.setOfferId(resultSet.getInt(Offer_ID));
				data.setPeriod(resultSet.getInt(PERIOD));
				data.setReportLevel(resultSet.getString(REPORT_LEVEL));
				data.setResponsibility(resultSet.getDouble(RESPONSIBILITY));
				data.setStopoutByCA(resultSet.getDouble(STOPOUT_BY_CA));
				data.setType(resultSet.getInt(TYPE));
				result.add(data);
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e);
		}

		return result;
	}

	public static List<FxTPeriodProfitDTO> getMounthProfit(int accountNumber, Date lastDate) {
		List<FxTPeriodProfitDTO> result = new ArrayList<FxTPeriodProfitDTO>();
		DataManager db = DataManager.getInstance();
		int accountID = db.getFxtAccountID(FxTrendParser.TRADER_ID, accountNumber);
		PreparedStatement pStatement = db.getPreperedStatement(SELECT_PROFIT_PER_MONTH_BY_ACCOUNT_ID);
		try {
			pStatement.setInt(1, accountID);
			pStatement.setDate(2, lastDate);
			ResultSet set = pStatement.executeQuery();
			while (set.next()) {
				FxTPeriodProfitDTO data = new FxTPeriodProfitDTO();
				data.setAccountNumber(accountNumber);
				data.setDate(set.getDate(MOUNTH));
				data.setProfitOfInvestorOnPercent(set.getDouble(PROFIT_OF_INVESTOR_ON_PERCENT));
				data.setProfitOnDollar(set.getDouble(PROFIT_ON_DOLLAR));
				data.setProfitOnPercent(set.getDouble(PROFIT_ON_PERCENT));
				result.add(data);
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return result;
	}

	public static List<FxTPeriodProfitDTO> getWeekPofit(int accountNumber, Date lastDate) {
		List<FxTPeriodProfitDTO> result = new ArrayList<FxTPeriodProfitDTO>();
		DataManager db = DataManager.getInstance();
		int accountID = db.getFxtAccountID(FxTrendParser.TRADER_ID, accountNumber);
		PreparedStatement pStatement = db.getPreperedStatement(SELECT_PROFIT_PER_WEEK_BY_ACCOUNT_ID);
		try {
			pStatement.setInt(1, accountID);
			pStatement.setDate(2, lastDate);
			ResultSet set = pStatement.executeQuery();
			while (set.next()) {
				FxTPeriodProfitDTO data = new FxTPeriodProfitDTO();
				data.setAccountNumber(accountNumber);
				data.setDate(set.getDate(WEEK));
				data.setProfitOfInvestorOnPercent(set.getDouble(PROFIT_OF_INVESTOR_ON_PERCENT));
				data.setProfitOnDollar(set.getDouble(PROFIT_ON_DOLLAR));
				data.setProfitOnPercent(set.getDouble(PROFIT_ON_PERCENT));
				result.add(data);
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return result;
	}

	public static List<FxTDealsDTO> getDealsPofit(int accountNumber, Date lastDate) {
		List<FxTDealsDTO> result = new ArrayList<FxTDealsDTO>();
		DataManager db = DataManager.getInstance();
		int accountID = db.getFxtAccountID(FxTrendParser.TRADER_ID, accountNumber);
		PreparedStatement pStatement = db.getPreperedStatement(SELECT_IN_FXT_DEAL_HISTORY);
		try {
			pStatement.setInt(1, accountID);
			pStatement.setDate(2, lastDate);
			ResultSet set = pStatement.executeQuery();
			while (set.next()) {
				FxTDealsDTO data = new FxTDealsDTO();
				data.setAccountNumber(accountNumber);
				data.setAdminCapital(set.getDouble(ADMIN_CAPITAL));
				data.setCapacity(set.getDouble(CAPACITY));
				data.setClosedDealDate(set.getDate(CLOSED_DEAL_DATE));
				data.setCurrencyPair(set.getString(CURRENCY_PAIR));
				data.setGeneralProfit(set.getDouble(GENERAL_PROFIT));
				result.add(data);
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return result;
	}

	private static boolean accountHasOffer(int idLastOffer) {
		return idLastOffer != NOT_SET;
	}
}