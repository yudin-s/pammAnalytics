package org.server.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.server.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database implements IData {
	// Properties names in PropertyManager
	private static final String DB_PASSWORD = "dbPassword";
	private static final String DB_LOGIN_PROPERTY = "dbLogin";
	private static final String DB_SCHEMA_PROPERTY = "dbSchema";
	private static final String INIT_LINE_PROPERTY = "dbJdbcInitLine";
	// Settings names in JDBC constructor
	private static final String PASSWORD = "password";
	private static final String USER = "user";
	private static final String SCHEMA = "schema";
	// Default parameters
	private static final String DEFAULT_PASSWORD = "helloworld";
	private static final String DEFAULT_LOGIN = "pamm";
	private static final String DEFAULT_SCHEMA = "PUBLIC";
	private static final String DB_IN_MEMORY = "jdbc:h2:mem:~/testo";

	// Messages
	private static final String STATEMENT_GET_ERROR = "Statement get error! {}";
	private static final String SQL_EXEPTION_ERROR = "SQL Exeption! Detail: {}";
	// Database create queries
	private static final String CREATE_FXT_ACCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS FXT_ACCOUNTS ("
			+ "ID INTEGER AUTO_INCREMENT UNIQUE," + " ACCOUNT_NUMBER INTEGER NOT NULL," + "ADMIN_ID VARCHAR(100),"
			+ "TRADER_ID INTEGER NOT NULL," + "DATE_REG DATE," + "ID_LAST_OFFER INT,"
			+ "STATE VARCHAR(255) check (STATE in ('OPEN', 'CLOSED', 'NULL'))," + "START_CAPITAL DECIMAL(25,5),"
			+ "CURRENT_CAPITAL DECIMAL(25,5)," + "INVESTOR_CAPITAL DECIMAL(25,5)," + "AVGRAGE_DROPDOWN DECIMAL(25,5),"
			+ "MAXIMAL_DROPDOWN DECIMAL(25,5)," + "NUM_OPEN_DEALS INTEGER," + "DATE_NEXT_ROLLOVER DATE )";
	private static final String CREATE_FXT_OFFERS_TABLE = "CREATE TABLE IF NOT EXISTS FXT_OFFERS ("
			+ "ID INTEGER AUTO_INCREMENT UNIQUE," + "ACCOUNT_ID INTEGER," + "OFFER_ID INTEGER," + "PERIOD INTEGER,"
			+ "ADMIN_REWARD DECIMAL(5,2)," + "COMMISSION DECIMAL(5,2)," + "TYPE INTEGER," + "RESPONSIBILITY FLOAT,"
			+ "MIN_SUMM_ADD_AND_LEFT DECIMAL(25,5)," + "STOPOUT_BY_CA DECIMAL(25,5),"
			+ "AGENTS_FROM_CALLING DECIMAL(5,2)," + "AGENTS_FROM_PROFIT DECIMAL(5,2)," + "REPORT_LEVEL VARCHAR(50),"
			+ "CONNECTION_METHOD VARCHAR(2048)," + "STRATEGY VARCHAR(2048))";
	private static final String CREATE_FXT_PROFIT_PER_WEEK_TABLE = "CREATE TABLE IF NOT EXISTS FXT_PROFIT_PER_WEEK("
			+ "ID INTEGER AUTO_INCREMENT UNIQUE," + "ACCOUNT_ID INTEGER," + "WEEK DATE,"
			+ "PROFIT_ON_DOLLAR DECIMAL(25,5)," + "PROFIT_ON_PERCENT DECIMAL(25,5),"
			+ "PROFIT_OF_INVESTOR_ON_PERCENT DECIMAL(4,4))";
	private static final String CREATE_FXT_PROFIT_PER_MOUNTH_TABLE = "CREATE TABLE IF NOT EXISTS FXT_PROFIT_PER_MOUNTH("
			+ "ID INTEGER AUTO_INCREMENT UNIQUE,"
			+ "ACCOUNT_ID INTEGER,"
			+ "MOUNTH DATE,"
			+ "PROFIT_ON_DOLLAR DECIMAL(25,5),"
			+ "PROFIT_ON_PERCENT DECIMAL(25,5),"
			+ "PROFIT_OF_INVESTOR_ON_PERCENT DECIMAL(4,4))";
	private static final String CREATE_FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR_TABLE = "CREATE TABLE IF NOT EXISTS FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR("
			+ "ID INTEGER AUTO_INCREMENT UNIQUE,"
			+ "ACCOUNT_ID INTEGER,"
			+ "UPDATE_DATE DATE,"
			+ "CAPITAL_OF_INVESTOR DECIMAL(25,5)," + "CAPITAL_OF_ADMINISTRATOR DECIMAL(25,5))";
	private static final String CREATE_FXT_DEAL_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS FXT_DEAL_HISTORY("
			+ "ID INTEGER AUTO_INCREMENT UNIQUE," + "ACCOUNT_ID INTEGER," + "CLOSED_DEAL_DATE DATE,"
			+ "CURRENCY_PAIR VARCHAR(10)," + "CAPACITY DECIMAL(25,5)," + "GENERAL_PROFIT DECIMAL (25,5),"
			+ "ADMIN_CAPITAL DECIMAL (25,5))";
	private static final String CREATE_TRAIDERS_TABLE = "CREATE TABLE IF NOT EXISTS TRAIDERS(" + "ID INTEGER UNIQUE,"
			+ "NAME VARCHAR(50)," + "SITE VARCHAR(50))";
	private static final String CREATE_FXT_ACCOUNT_TYPES = "CREATE TABLE IF NOT EXISTS FT_ACCOUNT_TYPES("
			+ "ID INTEGER UNIQUE," + "NAME VARCHAR(50))";

	private Logger log = LoggerFactory.getLogger(Database.class);
	private Connection connection;

	public Database() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		DriverManager.registerDriver(new org.h2.Driver());
		Properties connectionProperty = getConnectionSettings();
		String jdbcInitLine = PropertyManager.getProperty(INIT_LINE_PROPERTY, DB_IN_MEMORY);
		connection = DriverManager.getConnection(jdbcInitLine, connectionProperty);
		executeArraySQL(new String[] { CREATE_FXT_ACCOUNTS_TABLE, CREATE_FXT_DEAL_HISTORY_TABLE,
				CREATE_FXT_ACCOUNT_TYPES, CREATE_FXT_OFFERS_TABLE, CREATE_FXT_PROFIT_PER_MOUNTH_TABLE,
				CREATE_FXT_PROFIT_PER_WEEK_TABLE, CREATE_TRAIDERS_TABLE,
				CREATE_FXT_CAPITAL_ADMINISTRATOR_AND_INVESTOR_TABLE });

	}

	private Properties getConnectionSettings() {
		Properties connInfo = new Properties();
		connInfo.put(SCHEMA, PropertyManager.getProperty(DB_SCHEMA_PROPERTY, DEFAULT_SCHEMA));
		connInfo.put(USER, PropertyManager.getProperty(DB_LOGIN_PROPERTY, DEFAULT_LOGIN));
		connInfo.put(PASSWORD, PropertyManager.getProperty(DB_PASSWORD, DEFAULT_PASSWORD));
		return connInfo;
	}

	@Override
	public Statement getStatement() {
		try {
			return connection.createStatement();
		} catch (SQLException ex) {
			log.error(STATEMENT_GET_ERROR, ex.getMessage());
		}
		return null;
	}

	@Override
	public PreparedStatement getPreperedStatement(String stm) {
		try {
			return connection.prepareStatement(stm);
		} catch (SQLException e) {
			log.error(SQL_EXEPTION_ERROR, e.getMessage());
			return null;
		}
	}

	public void executeArraySQL(String SQLArray[]) {
		Statement stm = getStatement();
		for (String sql : SQLArray) {
			try {
				stm.execute(sql);
			} catch (SQLException e) {
				log.error(SQL_EXEPTION_ERROR, e.getMessage());
			}
		}
	}
}
