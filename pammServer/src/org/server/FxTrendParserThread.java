package org.server;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.parser.fxtrend.beans.FxTDataBeen;
import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtDealHistoryData;
import org.parser.fxtrend.beans.FxtOfferData;
import org.parser.fxtrend.beans.FxtProfitData;
import org.parser.fxtrend.exception.AccountsDataNotAddedToDatabase;
import org.parser.fxtrend.exception.DealsHistoryDataHotFounded;
import org.parser.fxtrend.exception.ParseListPageError;
import org.parser.fxtrend.exception.ParserNotAuth;
import org.parser.fxtrend.parser.FxTType;
import org.parser.fxtrend.parser.FxTrendParser;
import org.parser.fxtrend.parser.IFxtData;
import org.parser.parser.NewWebClient;
import org.parser.parser.NewWebClientFactory;
import org.parser.parser.WebClientFactory;
import org.server.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class FxTrendParserThread extends Thread {
	// Track messages
	private static final String PAMM2_LIST_PAGES_PARSE_START = "PAMM2 list pages parse start";
	private static final String PAMM_LIST_PAGES_PARSE_START = "PAMM list pages parse start";
	private static final String UPDATE_WAS_SUCCESS = "fx-trend: update was success";
	private static final String FIFTY_ACCOUNTS_ADDED_TO_DB = "fx-trend.com: 50 accounts added to DB";
	private static final String ACCOUNT_TABLES_HAS_BEEN_PARSED = "fx-trend.com: account tables has been parsed";
	// Errors
	private static final String ACCOUNTS_DATA_NOT_ADD = "Accounts data not add \nID: {}\nDataBeans contains:{}";
	private static final String HTTP_FAIL_IN_PAMM_TWO = "HTTP Fail in PAMM TWO page getter. Status code: {}";
	private static final String THREAD_NOT_SLEEP = "Thread was insomnia M:{}\nST:{}";
	private static final String UNEXPECTED_ERROR = "Unexpected error. Message: {}\nStacktrace: {}";
	private static final String FX_TREND_THREAD_NAME = "fx-trend.com parser thread";
	private static final String DEAL_HISTORY_DATA_NOT_ACCESSED = "DealHistory data not accessed {}";
	private static final String INVALID_DATE = "Invalid date: {}";
	private static final String ERROR_INT_PAMM_LIST_TABLE = "May be we found parse error in pamm list table. Path to page: {}";
	private static final String PARSER_NOT_AUTH_ERROR_LOGIN_PASSWORD = "Parser not auth error login and password:{}.\nStacktrace: {}";
	private static final String WAIT_TIME_NOT_SET = "Wait time for parser not set";
	private static final String FX_TREND_COM_PASSWORD_NOT_SET = "fx-trend.com password not set";
	private static final String FX_TREND_AUTHENTIFICATION_LOGIN_NOT_SET = "fx-trend authentification login not set";
	private static final String DATABASE_NOT_SET = "fx-trend.com parser. Database not set!";
	// Links (URLs)
	private static final String HTTP_FX_TREND_COM = "http://fx-trend.com";
	private static final String PATH_TO_DETAIL_PAGE = HTTP_FX_TREND_COM + "/pamm2/";
	private static final String PAMM_TABLE = "https://fx-trend.com/my/pamm_investor/accounts/?rep_page=";
	private static final String LINK_TO_DETAIL_HISTORY = "http://fx-trend.com/my/pamm_investor/details/";
	private static final String PAMM2_TABLE = "https://fx-trend.com/my/pamm2_investor/accounts/?rep_pamm2_0_order=&rep_pamm2_0_direct=&rep_pamm2_0_page=";
	// XPATHs
	private static final String PAMM_TABLE_BODY = "//*[@id=\"mb_center\"]/form/div/div[2]/div[4]/table[2]/tbody";
	private static final String XPATH_OFFER_OPEN_DETECTOR = "//*[@id=\"mb_center\"]/table[2]/tbody/tr[2]/td[2]";
	// Patterns
	private static final String DD_MM_YYYY = "dd.MM.yyyy";
	private static final String MM_YYYY = "MM.yyyy";
	private static final String NOT_NUMBER_REGEX = "[^0-9\\.]{1,}";
	// Other constants
	private static final String EMPTY = "";
	private static final String STRING_ZERO = "0";
	private static final String[] RUSSIAN_MOUNTH = { "Нулябрь", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
			"Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
	private static final Logger log = LoggerFactory.getLogger(FxTrendParserThread.class);
	public static final int ONE_MINUTE = 60 * 1000;
	private static final int CONRTOL_BARIER = 50;
	public static final int TRADER_ID = 1;
	// Statics
	private static NewWebClient newWebClient;
	private static IFxtData database = null;
	private static Long waitTime = null;
	private static String login = null;
	private static String password = null;
	private static FxTrendParser parser = null;
	private static FxTrendParserThread instance = null;

	public static FxTrendParserThread getInstace() {
		if (objectNonExist(instance)) {
			List<Pair<Object, String>> testParams = new ArrayList<Pair<Object, String>>();
			testParams.add(new Pair<Object, String>(getDatabase(), DATABASE_NOT_SET));
			testParams.add(new Pair<Object, String>(getWaitTime(), WAIT_TIME_NOT_SET));
			testParams.add(new Pair<Object, String>(getLogin(), FX_TREND_AUTHENTIFICATION_LOGIN_NOT_SET));
			testParams.add(new Pair<Object, String>(getPass(), FX_TREND_COM_PASSWORD_NOT_SET));
			if (checkParams(testParams)) {
				instance = new FxTrendParserThread(getDatabase(), waitTime, getLogin(), getPass());
				parser = new FxTrendParser(getDatabase(), getLogin(), getPass());
			}
		}
		return instance;
	}

	private FxTrendParserThread(IFxtData data, long waitTime, String login, String pass) {
		super(FX_TREND_THREAD_NAME);
		WebClient webClient = WebClientFactory.createDefaultWebClient();
		newWebClient = NewWebClientFactory.createClient(webClient);
		database = data;
		setWaitTime(waitTime);
		FxTrendParserThread.login = login;
		password = pass;
	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					updateData();
				} catch (Throwable t) {
					log.error(UNEXPECTED_ERROR, t.getMessage(), t.getStackTrace());
				}
				sleep(waitTime);
			}
		} catch (InterruptedException e) {
			log.error(THREAD_NOT_SLEEP, e.getMessage(), e.getStackTrace());
		}
		super.run();
	}

	public boolean updateData() {
		try {
			if (!isAuth()) {
				reAuth();
			}
			HashMap<Integer, FxtAccountsData> accountsList = parseAccountsGrid();

			log.info(ACCOUNT_TABLES_HAS_BEEN_PARSED);
			int count = 1;
			for (Entry<Integer, FxtAccountsData> entry : accountsList.entrySet()) {
				int id = entry.getKey();
				try {
					count = controleConsoleMessage(count);
					FxtAccountsData alreagyReady = entry.getValue();
					int maskData = FxTrendParser.MAIN_DATA | FxTrendParser.OFFER_DATA | FxTrendParser.MONTH_DATA
							| FxTrendParser.WEEK_DATA;
					FxTDataBeen pack = parser.parseOneDetailPage(id, alreagyReady.getType(), maskData);
					FxtAccountsData mainData = pack.getMainData();
					FxtOfferData offerData = pack.getOfferData();
					FxtProfitData[] profitByWeek = pack.getWeekProfitData();
					FxtProfitData[] profitByMounth = pack.getMonthProfitData();
					FxtDealHistoryData[] dealsHistory = pack.getDealsProfitData();

					copyAccountNameAndIDAndDateOfReg(alreagyReady, mainData);

					if (offerDataExist(offerData)) {
						offerData.setAccountNumber(alreagyReady.getAccountNumber());
					}

					if (!database.setFxtAccountsData(TRADER_ID, mainData))
						throw new AccountsDataNotAddedToDatabase(mainData);

					if (offerDataExist(offerData)) {
						database.setFxtAddOffer(TRADER_ID, offerData);
						if (maskCheck(maskData, FxTrendParser.DEALS_DATA)) {
							if (historyIsEmpty(dealsHistory))
								throw new DealsHistoryDataHotFounded();
							database.setFxtDealHistory(TRADER_ID, dealsHistory, alreagyReady.getAccountNumber());
						}
					}

					database.setFxtProfitByWeek(TRADER_ID, profitByWeek, alreagyReady.getAccountNumber());
					database.setFxtProfitByMounth(TRADER_ID, alreagyReady.getAccountNumber(), profitByMounth);

				} catch (AccountsDataNotAddedToDatabase e) {

					log.error(ACCOUNTS_DATA_NOT_ADD, id, e.getNotAddedData().toString());

				} catch (DealsHistoryDataHotFounded e) {

					log.error(DEAL_HISTORY_DATA_NOT_ACCESSED, PATH_TO_DETAIL_PAGE + id);
				}
			}
			log.info(UPDATE_WAS_SUCCESS);
			return true;
		} catch (ParserNotAuth e1) {
			log.error(PARSER_NOT_AUTH_ERROR_LOGIN_PASSWORD, login + " " + password, new Exception());
			return false;
		}
	}

	private boolean maskCheck(int val, int maskVal) {
		return ((val | maskVal) == val);
	}

	private void copyAccountNameAndIDAndDateOfReg(FxtAccountsData source, FxtAccountsData target) {
		target.setAccountNumber(source.getAccountNumber());
		target.setIdAdmin(source.getIdAdmin());
		target.setDateReg(source.getDateReg());
	}

	private boolean historyIsEmpty(Object[] data) {
		if (data != null) {
			return data.length < 1;
		} else {
			return true;
		}
	}

	private boolean offerDataExist(FxtOfferData ofertData) {
		return ofertData != null;
	}

	private int controleConsoleMessage(int count) {
		if (count % CONRTOL_BARIER == 0)
			log.info(FIFTY_ACCOUNTS_ADDED_TO_DB);
		return count + 1;
	}

	public HtmlPage getFirstPageOfOfferHistory(String dealHistoryID) {
		HtmlPage result = getPage(LINK_TO_DETAIL_HISTORY + dealHistoryID);
		return result;
	}

	private HashMap<Integer, FxtAccountsData> parseAccountsGrid() {
		HashMap<Integer, FxtAccountsData> result = new HashMap<Integer, FxtAccountsData>();
		refreshPAMMList(result);
		refreshPAMMTwoList(result);
		return result;
	}

	private boolean itsDataLine(HtmlTableRow row) {
		return row.getCells().size() > 1;
	}

	protected boolean offerIsOpen(HtmlPage page) {
		List<?> itNotBeenEmptyOfferIsBeenOpen = page.getByXPath(XPATH_OFFER_OPEN_DETECTOR);
		return !(itNotBeenEmptyOfferIsBeenOpen.isEmpty());
	}

	private void refreshPAMMList(HashMap<Integer, FxtAccountsData> result) {
		log.info(PAMM_LIST_PAGES_PARSE_START);
		for (int i = 1; i < 100; i++) {
			HtmlPage page = getPage(PAMM_TABLE + i);
			try {
				parseListPage(page, FxTType.PAMM, result);
			} catch (ParseListPageError e) {
				log.error(ERROR_INT_PAMM_LIST_TABLE, page.getUrl());
			}
		}
	}

	private void refreshPAMMTwoList(HashMap<Integer, FxtAccountsData> result) {
		log.info(PAMM2_LIST_PAGES_PARSE_START);
		HtmlPage page = null;
		for (int i = 1; i < 100; i++) {
			try {
				page = getPage(PAMM2_TABLE + i);
				parseListPage(page, FxTType.PAMM2, result);
			} catch (FailingHttpStatusCodeException e) {
				log.error(HTTP_FAIL_IN_PAMM_TWO, e.getStatusCode());
			} catch (ParseListPageError e) {
				log.error("Table not parsed! May be data not founded. Page path:{}", page.getUrl());
			}
		}
	}

	private void parseListPage(HtmlPage page, FxTType type, HashMap<Integer, FxtAccountsData> target)
			throws ParseListPageError {
		List<?> lTableList = page.getByXPath(PAMM_TABLE_BODY);
		if (lTableList.size() != 0) {
			HtmlTableBody tableBody = (HtmlTableBody) lTableList.get(0);
			if (tableBodyHasData(tableBody)) {
				List<HtmlTableRow> rows = tableBody.getRows().subList(2, tableBody.getRows().size() - 1);
				for (HtmlTableRow row : rows) {
					if (itsDataLine(row)) {
						FxtAccountsData data = new FxtAccountsData();
						data.setIdAdmin(row.getCell(0).getTextContent());
						data.setAccountNumber(Integer.parseInt(onlyNumbers(row.getCell(1).getTextContent())));
						data.setDateReg(parseDateWithoutTime(row.getCell(2).getTextContent()));
						data.setType(type);
						target.put(data.getAccountNumber(), data);
					}
				}
			}
		} else
			throw new ParseListPageError(page.getUrl().getPath().toString());
	}

	private boolean tableBodyHasData(HtmlTableBody tableBody) {
		return tableBody.getRows().size() > 2;
	}

	private String onlyNumbers(String source) {
		String clearString = source.replaceAll(NOT_NUMBER_REGEX, EMPTY);
		if (clearString.isEmpty()) {
			return STRING_ZERO;
		} else {
			return clearString;
		}
	}

	private Date parseDateWithoutTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		try {
			return new java.sql.Date(format.parse(date).getTime());
		} catch (ParseException e) {
			log.error(INVALID_DATE, e.getMessage());
			return new java.sql.Date(System.currentTimeMillis());
		}
	}

	private HtmlPage getPage(String URL) {
		return newWebClient.getPage(URL);
	}

	public void auth(String login, String pass) {
		FxTrendParserThread.login = login;
		FxTrendParserThread.password = pass;
		newWebClient.fxtAuth(login, pass);
	}

	public boolean isAuth() {
		return newWebClient.isFxTAuth();
	}

	private void reAuth() throws ParserNotAuth {
		auth(login, password);
		if (!isAuth()) {
			throw new ParserNotAuth();
		}
	}

	private static int parseRussianMounth(String mounth) {
		for (int i = 1; i < 13; i++) {
			if (mouthNumberIs(mounth, i)) {
				return i;
			}
		}
		return 0;
	}

	private static boolean mouthNumberIs(String mounth, int number) {
		return mounth.equalsIgnoreCase(RUSSIAN_MOUNTH[number]);
	}

	public static Date parseNotNumericDate(String val) {
		Date date = null;
		Scanner scan = new Scanner(val);
		String ruName = scan.next();
		String year = scan.next();
		StringBuffer mounth = new StringBuffer(parseRussianMounth(ruName) + ".");
		date = parseDateWithoutDayAndTime(mounth.append(year).toString());
		return date;
	}

	private static Date parseDateWithoutDayAndTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat(MM_YYYY);
		try {
			return new java.sql.Date(format.parse(date).getTime());
		} catch (ParseException e) {
			return new java.sql.Date(System.currentTimeMillis());
		}
	}

	private static boolean checkParam(Object param, String errorMsg) {
		if (objectNonExist(param)) {
			log.error(errorMsg);
			return false;
		}
		return true;
	}

	private static boolean checkParams(List<Pair<Object, String>> params) {
		for (Pair<Object, String> pair : params) {
			if (!checkParam(pair.getFirst(), pair.getLast())) {
				return false;
			}
		}
		return true;
	}

	private static boolean objectExist(Object obj) {
		return obj != null;
	}

	private static boolean objectNonExist(Object obj) {
		return !objectExist(obj);
	}

	public static IFxtData getDatabase() {
		return database;
	}

	public static void setDatabase(IFxtData database_i) {
		FxTrendParserThread.database = database_i;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login_i) {
		FxTrendParserThread.login = login_i;
	}

	public static String getPass() {
		return password;
	}

	public static void setPass(String pass_i) {
		FxTrendParserThread.password = pass_i;
	}

	public static void setWaitTime(long waitTime) {
		FxTrendParserThread.waitTime = waitTime;
	}

	public static long getWaitTime() {
		return waitTime;
	}

}