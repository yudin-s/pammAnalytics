package org.server.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtDealHistoryData;
import org.parser.fxtrend.beans.FxtOfferData;
import org.parser.fxtrend.beans.FxtProfitData;
import org.parser.fxtrend.parser.IFxtData;
import org.server.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataManager implements IData, IFxtData {

	private static final String DATA_NOT_FOUND = "Data not found";
	private static final String FXT_DEAL_HISTORY = "FXT_DEAL_HISTORY";
	private static final String FXT_PROFIT_PER_MOUNTH = "FXT_PROFIT_PER_MOUNTH";
	private static final String FXT_PROFIT_PER_WEEK = "FXT_PROFIT_PER_WEEK";
	private static final String DATE_FIELD_IN_CA_AND_I_TABLE = "UPDATE_DATE";
	private static final String FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR_TABLENAME = "FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR";
	private static final String CLOSED_DEAL_DATE = "CLOSED_DEAL_DATE";
	private static final String MOUNTH = "MOUNTH";
	private static final String WEEK = "WEEK";

	private static final String SELECT_MAX = "SELECT MAX(";
	private static final String FROM = ") FROM ";
	private static final String WHERE_ACCOUNT_ID = " WHERE ACCOUNT_ID = ";

	private static final String ACCOUNT_SAVE_ERROR_MESSAGE = "ACCOUNT DATA NOT SAVED! {}. \nMessage: {}";
	private static final String UNEXPECTED_SQL_EXCEPTION = "Unexpected SQL exception {}";
	private static final String INVALID_SQL = "Invalid SQL: {} ";
	private static final String ACCOUNT_ID_GET_FAIL = "Account ID get FAIL! {}";
	private static final String DATABASE_NOT_CREATE_ERROR = "Database not create! Reason: {}\nDetail: {}";

	private static final String ACCOUNT_NUMBER_BY_ID = "SELECT ACCOUNT_NUMBER FROM FXT_ACCOUNTS WHERE ID=?";
	private static final String INSERT_ADMINISTRATOR_AND_INVESTOR_CAPITAL = "INSERT INTO FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR ("
			+ "ACCOUNT_ID, "
			+ "UPDATE_DATE,"
			+ "CAPITAL_OF_ADMINISTRATOR,"
			+ "CAPITAL_OF_INVESTOR"
			+ ") VALUES (?,?,?,?)";
	private static final String CAPITAL_OF_ADMINISTRATOR = "CAPITAL_OF_ADMINISTRATOR";
	private static final String CAPITAL_OF_INVESTOR = "CAPITAL_OF_INVESTOR";
	private static final String GET_CAPITAL_PAIR_PREPARED = "SELECT CAPITAL_OF_INVESTOR, CAPITAL_OF_ADMINISTRATOR FROM FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR WHERE ACCOUNT_ID = ? AND UPDATE_DATE = ?";
	private static final String GET_ID = "SELECT ID FROM FXT_ACCOUNTS WHERE TRADER_ID = ? AND ACCOUNT_NUMBER = ? LIMIT 1";
	private static final String ACCOUNTS_UPDATE_EXIST_RECORS = "UPDATE FXT_ACCOUNTS SET  ID_LAST_OFFER = ?, STATE = ?, CURRENT_CAPITAL = ?, INVESTOR_CAPITAL = ?, AVGRAGE_DROPDOWN = ?, MAXIMAL_DROPDOWN = ?, NUM_OPEN_DEALS = ?, DATE_NEXT_ROLLOVER = ?  "
			+ "WHERE ACCOUNT_NUMBER= ? AND TRADER_ID = ?  ";
	private static final String ACCOUNTS_INSERT_RECORD = "INSERT INTO FXT_ACCOUNTS ( ACCOUNT_NUMBER,  ADMIN_ID, TRADER_ID, DATE_REG, ID_LAST_OFFER, STATE, START_CAPITAL, CURRENT_CAPITAL,  INVESTOR_CAPITAL,  AVGRAGE_DROPDOWN,  MAXIMAL_DROPDOWN,  NUM_OPEN_DEALS,  DATE_NEXT_ROLLOVER)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	private static final String FXT_OFFERS_UPDATE_EXIST_RECORD = "UPDATE FXT_OfferS SET " + "ADMIN_REWARD = ? , "
			+ "PERIOD = ?," + "COMMISSION = ?, " + "RESPONSIBILITY = ?, " + "MIN_SUMM_ADD_AND_LEFT = ?, "
			+ "STOPOUT_BY_CA =  ? ," + "AGENTS_FROM_CALLING = ? ," + "AGENTS_FROM_PROFIT = ?," + "REPORT_LEVEL = ?, "
			+ "CONNECTION_METHOD = ?," + "STRATEGY = ?" + "WHERE ACCOUNT_ID = ?";
	private static final String FXT_OFFERS_INSERT_INTO = "INSERT INTO FXT_OFFERS (" + "ACCOUNT_ID," + "OFFER_ID,"
			+ "PERIOD," + "ADMIN_REWARD," + "COMMISSION," + "TYPE," + "RESPONSIBILITY," + "MIN_SUMM_ADD_AND_LEFT,"
			+ "STOPOUT_BY_CA," + "AGENTS_FROM_CALLING," + "AGENTS_FROM_PROFIT," + "REPORT_LEVEL,"
			+ "CONNECTION_METHOD," + "STRATEGY) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String INSERT_FXT_PROFIT_PER_WEEK = "INSERT INTO FXT_PROFIT_PER_WEEK (" + "ACCOUNT_ID, "
			+ "WEEK, " + "PROFIT_ON_DOLLAR, " + "PROFIT_ON_PERCENT, " + "PROFIT_OF_INVESTOR_ON_PERCENT)"
			+ " VALUES (?,?,?,?,?)";
	private static final String INSERT_FXT_PROFIT_PER_MOUNTH = "INSERT INTO FXT_PROFIT_PER_MOUNTH (" + "ACCOUNT_ID, "
			+ "MOUNTH, " + "PROFIT_ON_DOLLAR, " + "PROFIT_ON_PERCENT, " + "PROFIT_OF_INVESTOR_ON_PERCENT)"
			+ " VALUES (?,?,?,?,?)";

	private static final String INSERT_FXT_DEAL_HISTORY = "INSERT INTO FXT_DEAL_HISTORY (" + "ACCOUNT_ID,"
			+ "CLOSED_DEAL_DATE," + "CURRENCY_PAIR," + "CAPACITY," + "GENERAL_PROFIT," + "ADMIN_CAPITAL"
			+ ") VALUES (?,?,?,?,?,?)";

	private static final int ERROR_RETURN = -1;

	Database database;
	private Logger log = LoggerFactory.getLogger(DataManager.class);
	private static DataManager instance = null;

	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}

	private DataManager() {
		try {
			database = new Database();
		} catch (SQLException e) {
			log.error(DATABASE_NOT_CREATE_ERROR, e.getStackTrace(), e.getMessage());
		}

	}

	@Override
	public boolean setFxtAccountsData(int idBroker, FxtAccountsData data) {
		PreparedStatement insertStatement = getPreperedStatement(ACCOUNTS_UPDATE_EXIST_RECORS);
		try {

			insertStatement.setInt(1, data.getIdLastOffer());
			insertStatement.setString(2, data.getState());
			insertStatement.setDouble(3, data.getCurrentCapital());
			insertStatement.setDouble(4, data.getInvestitions());
			insertStatement.setDouble(5, data.getAvgrageDropdown());
			insertStatement.setDouble(6, data.getMaxDropdown());
			insertStatement.setInt(7, data.getNumOfOpenDeals());
			insertStatement.setDate(8, data.getNextRollover());
			insertStatement.setInt(9, idBroker);
			insertStatement.setInt(10, data.getAccountNumber());
			if (dataNotUpdated(insertStatement)) {
				insertStatement = getPreperedStatement(ACCOUNTS_INSERT_RECORD);
				insertStatement.setInt(1, data.getAccountNumber());
				insertStatement.setString(2, data.getIdAdmin());
				insertStatement.setInt(3, idBroker);
				insertStatement.setDate(4, data.getDateReg());
				insertStatement.setInt(5, data.getIdLastOffer());
				insertStatement.setString(6, data.getState());
				insertStatement.setDouble(7, data.getStartCapital());
				insertStatement.setDouble(8, data.getCurrentCapital());
				insertStatement.setDouble(9, data.getInvestitions());
				insertStatement.setDouble(10, data.getAvgrageDropdown());
				insertStatement.setDouble(11, data.getMaxDropdown());
				insertStatement.setInt(12, data.getNumOfOpenDeals());
				insertStatement.setDate(13, data.getNextRollover());
				insertStatement.execute();
			}

			int accountID = getAccountId(idBroker, data.getAccountNumber());
			Pair<Double, Double> capitalOfAdminAndInvestor = getFxtLastCapitalAdministratorAndInvestor(idBroker,
					accountID);
			if (dataWasChanged(data, capitalOfAdminAndInvestor)) {
				insertStatement = getPreperedStatement(INSERT_ADMINISTRATOR_AND_INVESTOR_CAPITAL);
				insertStatement.setInt(1, accountID);
				insertStatement.setDate(2, new Date(System.currentTimeMillis()));
				insertStatement.setDouble(3, data.getCurrentCapital());
				insertStatement.setDouble(4, data.getInvestitions());
				insertStatement.execute();
			}
			return true;
		} catch (SQLException e) {
			log.error(ACCOUNT_SAVE_ERROR_MESSAGE, e.getStackTrace(), e.getMessage());
		}

		return false;
	}

	private boolean dataNotUpdated(PreparedStatement insertStatement) throws SQLException {
		return insertStatement.executeUpdate() == 0;
	}

	private boolean dataWasChanged(FxtAccountsData data, Pair<Double, Double> capitalOfAdminAndInvestor) {
		return capitalOfAdminAndInvestor != null
				&& data != null
				&& (capitalOfAdminAndInvestor.getFirst() != data.getCurrentCapital() || capitalOfAdminAndInvestor
						.getLast() != data.getInvestitions());
	}

	@Override
	public Statement getStatement() {
		return database.getStatement();
	}

	@Override
	public PreparedStatement getPreperedStatement(String stm) {
		return database.getPreperedStatement(stm);
	}

	private int getAccountId(int brokerId, int accountNumber) {
		PreparedStatement statement = getPreperedStatement(GET_ID);
		try {
			statement.setInt(1, brokerId);
			statement.setInt(2, accountNumber);
			ResultSet set = statement.executeQuery();
			set.next();
			return set.getInt(1);
		} catch (SQLException e) {
			log.error(ACCOUNT_ID_GET_FAIL, Integer.toString(accountNumber) + e.getMessage(), new Exception(
					DATA_NOT_FOUND));
		}
		return ERROR_RETURN;
	};

	@Override
	public boolean setFxtAddOffer(int brokerId, FxtOfferData data) {
		PreparedStatement insertStatement = getPreperedStatement(FXT_OFFERS_UPDATE_EXIST_RECORD);
		try {
			int accountID = getAccountId(brokerId, data.getAccountNumber());
			insertStatement.setDouble(1, data.getAdminReward());
			insertStatement.setInt(2, data.getPeriod());
			insertStatement.setDouble(3, data.getCommission());
			insertStatement.setDouble(4, data.getResponsibility());
			insertStatement.setDouble(5, data.getMinSummAndLeft());
			insertStatement.setDouble(6, data.getStopoutByCA());
			insertStatement.setDouble(7, data.getAgentsFromCalling());
			insertStatement.setDouble(8, data.getAgentsFromProfit());
			insertStatement.setString(9, data.getLevelReport());
			insertStatement.setString(10, data.getConnecitonMethod());
			insertStatement.setString(11, data.getStrategy());
			insertStatement.setInt(12, accountID);
			if (dataNotUpdated(insertStatement)) {
				insertStatement = getPreperedStatement(FXT_OFFERS_INSERT_INTO);
				insertStatement.setInt(1, accountID);
				insertStatement.setInt(2, data.getOfferID());
				insertStatement.setInt(3, data.getPeriod());
				insertStatement.setDouble(4, data.getAdminReward());
				insertStatement.setDouble(5, data.getCommission());
				insertStatement.setInt(6, data.getType());
				insertStatement.setDouble(7, data.getResponsibility());
				insertStatement.setDouble(8, data.getMinSummAndLeft());
				insertStatement.setDouble(9, data.getStopoutByCA());
				insertStatement.setDouble(10, data.getAgentsFromCalling());
				insertStatement.setDouble(11, data.getAgentsFromProfit());
				insertStatement.setString(12, data.getLevelReport());
				insertStatement.setString(13, data.getConnecitonMethod());
				insertStatement.setString(14, data.getStrategy());
				insertStatement.execute();

			}
			return true;
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return false;
	}

	@Override
	public boolean setFxtProfitByWeek(int brokerId, Object array[], int accountNumber) {
		if (weCanAddProfitByWeek(array)) {
			PreparedStatement preparedStatement;
			try {
				int accountID = getAccountId(brokerId, accountNumber);
				Date minDate = getFxtLastDateByWeekProfit(brokerId, accountID);
				for (int i = 0; i < array.length; i++) {
					FxtProfitData data = (FxtProfitData) array[i];
					if (dateBeenAfterMinDate(minDate, data.getDate())) {
						preparedStatement = getPreperedStatement(INSERT_FXT_PROFIT_PER_WEEK);
						preparedStatement.setInt(1, accountID);
						preparedStatement.setDate(2, data.getDate());
						preparedStatement.setDouble(3, data.getProfitOnDollar());
						preparedStatement.setDouble(4, data.getProfitOnPercent());
						preparedStatement.setDouble(5, data.getProfitOfInvestorInPercent());
						preparedStatement.execute();
					}
				}
				return true;
			} catch (SQLException e) {
				log.error(INVALID_SQL, e.getMessage());
			}
		}
		return false;
	}

	private boolean dateBeenAfterMinDate(Date minDate, Date date) {
		return date.compareTo(minDate) > 0;
	}

	private boolean weCanAddProfitByWeek(Object[] array) {
		return array.length > 0 && array[0] instanceof FxtProfitData;
	}

	@Override
	public boolean setFxtProfitByMounth(int brokerID, int accountNumber, Object[] array) {
		PreparedStatement preparedStatement;
		try {
			if (weCanAddProfitByWeek(array)) {
				int accountID = getAccountId(brokerID, accountNumber);
				Date minDate = getFxtLastDateByMounthProfit(brokerID, accountID);
				for (int i = 0; i < array.length; i++) {
					FxtProfitData data = (FxtProfitData) array[i];
					if (dateBeenAfterMinDate(minDate, data.getDate())) {
						preparedStatement = getPreperedStatement(INSERT_FXT_PROFIT_PER_MOUNTH);
						preparedStatement.setInt(1, accountID);
						preparedStatement.setDate(2, data.getDate());
						preparedStatement.setDouble(3, data.getProfitOnDollar());
						preparedStatement.setDouble(4, data.getProfitOnPercent());
						preparedStatement.setDouble(5, data.getProfitOfInvestorInPercent());
						preparedStatement.execute();
					}
				}
				return true;
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return false;
	}

	protected PreparedStatement chargePreparedStatementForDealHistory(int accountID, FxtDealHistoryData data) {
		try {
			PreparedStatement preparedStatement = getPreperedStatement(INSERT_FXT_DEAL_HISTORY);
			preparedStatement.setInt(1, accountID);
			preparedStatement.setDate(2, data.getDateOfClose());
			preparedStatement.setString(3, data.getCurrencyPair());
			preparedStatement.setDouble(4, data.getCapacity());
			preparedStatement.setDouble(5, data.getGeneralProfit());
			preparedStatement.setDouble(6, data.getAdminCapital());
			return preparedStatement;
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return null;
	}

	@Override
	public boolean setFxtDealHistory(int brokerID, Object array[], int accountNumber) {
		try {
			int accountID = getAccountId(brokerID, accountNumber);
			Date minDate = getFxtLastDateByMounthProfit(brokerID, accountID);
			for (int i = 0; i < array.length; i++) {
				FxtDealHistoryData data = (FxtDealHistoryData) array[i];
				if (dateBeenAfterMinDate(minDate, data.getDateOfClose())) {
					chargePreparedStatementForDealHistory(accountID, data).execute();
				}
			}
			return true;
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getSQLState());
		}
		return false;
	}

	public ResultSet executeQuery(String getDataFromDb) {
		try {
			return database.getStatement().executeQuery(getDataFromDb);
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
			return null;
		}
	}

	private Date getLastDateFromTable(String table, String field, int accountId) {
		String sql = createMaxValueInFieldByTableWhereAccountIDQuery(table, field, accountId);
		Statement statement = getStatement();
		Date date;
		try {
			statement.execute(sql);
			ResultSet resultSet = statement.getResultSet();
			resultSet.first();
			date = resultSet.getDate(1);
			if (dateNotFounded(date)) {
				date = new Date(0);
			}
			return date;
		} catch (SQLException e) {
			log.info(UNEXPECTED_SQL_EXCEPTION, e.getMessage());
		}
		return new Date(0);

	}

	private boolean dateNotFounded(Date date) {
		return date == null;
	}

	private String createMaxValueInFieldByTableWhereAccountIDQuery(String table, String field, int accountId) {
		return new StringBuffer(SELECT_MAX).append(field).append(FROM).append(table).append(WHERE_ACCOUNT_ID)
				.append(accountId).toString();
	}

	private Pair<Double, Double> getFxtLastCapitalAdministratorAndInvestor(int brokerID, int accountId) {
		Pair<Double, Double> result = null;
		PreparedStatement statement = getPreperedStatement(GET_CAPITAL_PAIR_PREPARED);
		try {
			statement.setInt(1, accountId);
			statement.setDate(2, getFxtLastDateByCapitalAdministratorAndInvestor(brokerID, accountId));
			statement.execute();
			ResultSet set = statement.getResultSet();
			if (set.first()) {
				result = new Pair<Double, Double>();
				result.setFirst(set.getDouble(CAPITAL_OF_ADMINISTRATOR));
				result.setLast(set.getDouble(CAPITAL_OF_INVESTOR));
			}
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return result;

	}

	private Date getFxtLastDateByCapitalAdministratorAndInvestor(int brokerId, int accountID) {
		return getLastDateFromTable(FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR_TABLENAME, DATE_FIELD_IN_CA_AND_I_TABLE,
				accountID);

	}

	@Override
	public Date getFxtLastDateByWeekProfit(int brokerID, int accountId) {
		return getLastDateFromTable(FXT_PROFIT_PER_WEEK, WEEK, accountId);
	}

	@Override
	public Date getFxtLastDateByMounthProfit(int brokerID, int accountId) {
		return getLastDateFromTable(FXT_PROFIT_PER_MOUNTH, MOUNTH, accountId);
	}

	@Override
	public Date getFxtLastDateByDeals(int brokerID, int accountId) {
		return getLastDateFromTable(FXT_DEAL_HISTORY, CLOSED_DEAL_DATE, accountId);
	}

	@Override
	public int getFxtAccountID(int idBroker, int accountNumber) {
		return getAccountId(idBroker, accountNumber);
	}

	@Override
	public int getFxtGetAccountNumberByAccountID(int accountID) {
		PreparedStatement preparedStatement = getPreperedStatement(ACCOUNT_NUMBER_BY_ID);
		try {
			preparedStatement.setInt(1, accountID);
			ResultSet set = preparedStatement.executeQuery();
			set.first();
			return set.getInt(1);
		} catch (SQLException e) {
			log.error(INVALID_SQL, e.getMessage());
		}
		return ERROR_RETURN;
	}

}
