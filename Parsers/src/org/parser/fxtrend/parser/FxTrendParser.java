package org.parser.fxtrend.parser;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parser.fxtrend.beans.FxTDataBeen;
import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtDealHistoryData;
import org.parser.fxtrend.beans.FxtOfferData;
import org.parser.fxtrend.beans.FxtProfitData;
import org.parser.fxtrend.exception.ParserNotAuth;
import org.parser.parser.NewWebClient;
import org.parser.parser.NewWebClientFactory;
import org.parser.parser.WebClientFactory;
import org.parser.parser.TimePeriod;
import org.parser.utilites.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class FxTrendParser {
	private static final int ONE_MINUTE = 60 * 1000;
	private static final String NOT_FOUND = "NOT_FOUND";
	private static final String INVALID_DATE = "Invalid date: {}";
	private static final String BUTTON_NOT_PUSHED_ERROR = "Button to accept Offer not pushed";
	// Links (URLs)
	private static final String HTTP_FX_TREND_COM = "http://fx-trend.com";
	private static final String PATH_TO_DETAIL_PAGE = HTTP_FX_TREND_COM
			+ "/pamm2/";
	private static final String LINK_TO_DETAIL_HISTORY = "http://fx-trend.com/my/pamm_investor/details/";

	// XPATHs
	private static final String ANCHOR_WITH_LINK = "//*[@id=\"apply_button\"]/a";
	private static final String XPATH_TO_ACCEPT_BUTTON = "//*[@id=\"apply_button\"]/input";
	private static final String PATH_TO_WEEK_PROFIT_TABLE = "//*[@id=\"mb_center\"]/table[1]/tbody/tr[2]/td[2]/table";
	private static final String XPATH_OFFER_OPEN_DETECTOR = "//*[@id=\"mb_center\"]/table[2]/tbody/tr[2]/td[2]";
	private static final String XPATH_TO_MOUNTH_TABLE = "//*[@id=\"mb_center\"]/table/tbody/tr[2]/td[1]/table";
	// Patterns
	private static final String DD_MM_YYYY = "dd.MM.yyyy";
	private static final String MM_YYYY = "MM.yyyy";
	private static final String NOT_NUMBER_REGEX = "[^0-9\\.]{1,}";
	private static final String NUMERIC_INT_REGEXP = "[0-9]{1,}/$";
	// Parse one page mask values
	public static final int MAIN_DATA = 2;
	public static final int OFFER_DATA = 4;
	public static final int MONTH_DATA = 8;
	public static final int WEEK_DATA = 16;
	public static final int DEALS_DATA = 32;
	// Other constants
	private static final String MINUS = "-";
	private static final String EMPTY = "";
	private static final String STRING_ZERO = "0";

	private static final String[] RUSSIAN_MOUNTH = { "Нулябрь", "Январь",
			"Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
			"Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
	private static final Logger log = LoggerFactory
			.getLogger(FxTrendParser.class);
	public static final int TRADER_ID = 1;
	// Statics

	private static IFxtData database = null;
	private String login = null;
	private String password = null;
	private static FxTPammOneTraderAndOfertParser pammOne;
	private static FxTPammTwoTraderAndOfertParser pammTwo;
	private static int getPageTryin = 3;
	private static int numberOfTryReAuth = 3;
	private NewWebClient newWebClient;

	public FxTrendParser(IFxtData data, String login, String pass) {
		database = data;
		pammOne = new FxTPammOneTraderAndOfertParser();
		pammTwo = new FxTPammTwoTraderAndOfertParser();
		WebClient baseClient = WebClientFactory.createDefaultWebClient();
		newWebClient = NewWebClientFactory.createClient(baseClient);
		this.login = login;
		this.password = pass;
	}

	public long authPeriodMiliseconds = ONE_MINUTE;
	private boolean lastAuthStatus = false;
	public long lastAuthStatusUpdateTimeStamp;

	private boolean isAuth() {
		if (!lastAuthStatus
				|| System.currentTimeMillis() - lastAuthStatusUpdateTimeStamp > authPeriodMiliseconds) {
			lastAuthStatus = newWebClient.isFxTAuth();
		}
		return lastAuthStatus;
	}

	public FxTDataBeen parseOneDetailPage(int id, FxTType type, int mask)
			throws ParserNotAuth {
		if (!isAuth()) {
			newWebClient.fxtAuth(login, password);
		}
		if (!isAuth()) {
			throw new ParserNotAuth();
		}
		FxTDataBeen result = new FxTDataBeen();
		HtmlPage detailPage = getPage(PATH_TO_DETAIL_PAGE + id);
		log.trace(detailPage.toString());
		if (maskCheck(mask, MAIN_DATA)) {
			result.setMainData(mainDataParse(detailPage, type));
		}
		if (maskCheck(mask, OFFER_DATA)) {
			result.setOfferData(offerDataParse(detailPage, type));
		}
		if (maskCheck(mask, WEEK_DATA)) {
			result.setWeekProfitData(parseWeekTable(detailPage, id));
		}
		if (maskCheck(mask, MONTH_DATA)) {
			result.setMounthProfitData(parseMounthTable(detailPage, id));
		}
		if (maskCheck(mask, DEALS_DATA)) {
			result.setDealsProfitData(parseDealsHistoryPage(detailPage, id));
		}
		return result;
	}

	private boolean maskCheck(int val, int maskVal) {
		return ((val | maskVal) == val);
	}

	private FxtOfferData offerDataParse(HtmlPage detailPage, FxTType type) {
		FxtOfferData offerData = null;
		switch (type) {
		case PAMM:
			offerData = pammOne.parseOfferData(detailPage);
			break;
		case PAMM2:
			offerData = pammTwo.parseOfferData(detailPage);
			break;
		}
		return offerData;
	}

	private FxtAccountsData mainDataParse(HtmlPage detailPage, FxTType type) {
		FxtAccountsData mainData = null;
		switch (type) {
		case PAMM:
			mainData = pammOne.parseBaseAccountData(detailPage);
			break;
		case PAMM2:
			mainData = pammTwo.parseBaseAccountData(detailPage);
			break;
		}
		return mainData;
	}

	private static final Pattern NUMERIC_PARRENT = Pattern
			.compile(NUMERIC_INT_REGEXP);

	public String parseManagedAccountID(String url) {
		Matcher math = NUMERIC_PARRENT.matcher(url);
		if (math.find()) {
			return url.substring(math.start());
		} else
			return NOT_FOUND;
	}

	private boolean offerNotAccept(HtmlPage page) {
		boolean res = page.getByXPath(XPATH_TO_ACCEPT_BUTTON).size() != 0;
		return res;
	}

	public HtmlPage getFirstPageOfOfferHistory(String dealHistoryID) {
		HtmlPage result = getPage(LINK_TO_DETAIL_HISTORY + dealHistoryID);
		return result;
	}

	private FxtProfitData[] parseWeekTable(HtmlPage page, int id) {
		List<?> lTableList = page.getByXPath(PATH_TO_WEEK_PROFIT_TABLE);
		ArrayList<FxtProfitData> list = new ArrayList<FxtProfitData>();
		HtmlTable ht = getTableFromList(lTableList);
		Date minDate = database.getFxtLastDateByWeekProfit(TRADER_ID, id);
		if (tableExist(ht)) {
			List<HtmlTableRow> rows = deleteHeaderAndFooter(ht);
			for (HtmlTableRow row : rows) {
				if (itsDataLine(row)) {
					TimePeriod week = parseWeekDate(getCell(0, row));
					if (week.getStart().compareTo(minDate) > 0) {
						FxtProfitData data = new FxtProfitData();
						data.setDate(week.getStart());
						data.setProfitOnDollar(getDoubleCell(1, row));
						data.setProfitOnPercent(getDoubleCell(2, row));
						data.setProfitOfInvestorInPercent(0.0);
						list.add(data);
					}
				}
			}
		}
		return list.toArray(new FxtProfitData[0]);
	}

	private List<HtmlTableRow> deleteHeaderAndFooter(HtmlTable ht) {
		return ht.getRows().subList(2, ht.getRowCount() - 1);
	}

	private boolean itsDataLine(HtmlTableRow row) {
		return row.getCells().size() > 1;
	}

	private FxtProfitData[] parseMounthTable(HtmlPage page, int id) {
		List<?> lTableList = page.getByXPath(XPATH_TO_MOUNTH_TABLE);
		ArrayList<FxtProfitData> list = new ArrayList<FxtProfitData>();

		HtmlTable ht = getTableFromList(lTableList);
		Date minDate = database.getFxtLastDateByMounthProfit(TRADER_ID, id);
		if (tableExist(ht)) {
			List<HtmlTableRow> rows = deleteHeaderAndFooter(ht);
			for (HtmlTableRow row : rows) {
				if (itsDataLine(row)) {
					Date mounth = parseNotNumericDate(getCell(0, row));
					if (mounth.compareTo(minDate) > 0) {
						FxtProfitData data = new FxtProfitData();
						data.setDate(mounth);
						data.setProfitOnDollar(getDoubleCell(1, row));
						data.setProfitOnPercent(getDoubleCell(2, row));
						data.setProfitOfInvestorInPercent(0.0);
						list.add(data);
					}
				}
			}
		}
		return list.toArray(new FxtProfitData[0]);
	}

	private boolean tableExist(HtmlTable ht) {
		return ht != null;
	}

	private HtmlTable getTableFromList(List<?> lTableList) {
		return (lTableList.isEmpty()) ? null : (HtmlTable) lTableList.get(0);
	}

	private FxtDealHistoryData[] parseDealsHistoryPage(HtmlPage page,
			int accountNumber) {
		ArrayList<FxtDealHistoryData> list = new ArrayList<FxtDealHistoryData>();
		if (offerIsOpen(page)) {
			String link = page.getUrl().toExternalForm();
			if (offerNotAccept(page)) {
				page = acceptOffer(page, link);
			}
			List<?> ancorList = page.getByXPath(ANCHOR_WITH_LINK);
			if (existOtherHistoryPages(ancorList)) {
				HtmlAnchor anchor = (HtmlAnchor) ancorList.get(0);

				String URL = parseManagedAccountID(anchor.getHrefAttribute());
				HtmlPage offerHistory = getFirstPageOfOfferHistory(URL);

				Date maxDate = database.getFxtLastDateByDeals(TRADER_ID,
						database.getFxtAccountID(TRADER_ID, accountNumber));
				String textContents = FxTDealHistoryTraderAndOfertParser
						.getChanger(offerHistory);
				Pair<Integer, String> lastPageAndLink;

				if (textContents != null) {
					lastPageAndLink = FxTDealHistoryTraderAndOfertParser
							.getLinkAndLastPageFromSpecialCell(textContents);
					Integer lastPageID = lastPageAndLink.getFirst();
					String url = lastPageAndLink.getLast();
					try {
						for (int i = 0; i <= lastPageID; i++) {
							HtmlPage historyPage = getPage(url + i);
							List<HtmlTableRow> rows = FxTDealHistoryTraderAndOfertParser
									.getHistoryTableRows(historyPage);

							FxTDealHistoryTraderAndOfertParser.parseTable(rows,
									maxDate, list);
						}
					} catch (Throwable t) {
						// STOP!
					}
				} else {
					List<HtmlTableRow> rows = FxTDealHistoryTraderAndOfertParser
							.getHistoryTableRows(offerHistory);
					try {
						FxTDealHistoryTraderAndOfertParser.parseTable(rows,
								maxDate, list);
					} catch (Exception e) {
						// Do nothing
					}
				}
			}
		}
		return list.toArray(new FxtDealHistoryData[0]);
	}

	private boolean existOtherHistoryPages(List<?> ancorList) {
		return ancorList.size() > 0;
	}

	private HtmlPage acceptOffer(HtmlPage page, String link) {
		List<?> byXPath = page.getByXPath(XPATH_TO_ACCEPT_BUTTON);
		HtmlSubmitInput submit = (HtmlSubmitInput) byXPath.get(0);
		try {
			submit.click();
		} catch (IOException e) {
			log.info(BUTTON_NOT_PUSHED_ERROR);
		}
		page = getPage(link);
		return page;
	}

	protected boolean offerIsOpen(HtmlPage page) {
		List<?> itNotBeenEmptyOfferIsBeenOpen = page
				.getByXPath(XPATH_OFFER_OPEN_DETECTOR);
		return !(itNotBeenEmptyOfferIsBeenOpen.isEmpty());
	}

	private Double getDoubleCell(int index, HtmlTableRow row) {
		return Double.parseDouble(onlyNumbers(getCell(1, row)));
	}

	private String getCell(int index, HtmlTableRow row) {
		return row.getCell(index).getTextContent();
	}

	private String onlyNumbers(String source) {
		String clearString = source.replaceAll(NOT_NUMBER_REGEX, EMPTY);
		if (clearString.isEmpty()) {
			return STRING_ZERO;
		} else {
			return clearString;
		}
	}

	private TimePeriod parseWeekDate(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line, MINUS);
		TimePeriod week = null;
		if (tokenizer.hasMoreTokens()) {
			String stringDate = tokenizer.nextToken();
			Date start = parseDateWithoutTime(stringDate);
			stringDate = tokenizer.nextToken();
			Date end = parseDateWithoutTime(stringDate);
			week = new TimePeriod(start, end);

		}
		return week;
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

	private HtmlPage getPage(String url) {
		return newWebClient.getPage(url);
	}

	public static IFxtData getDatabase() {
		return database;
	}

	public static void setDatabase(IFxtData database_i) {
		FxTrendParser.database = database_i;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login_i) {
		login = login_i;
	}

	public String getPass() {
		return password;
	}

	public void setPass(String pass_i) {
		this.password = pass_i;
	}

	public int getGetPageTryin() {
		return getPageTryin;
	}

	public void setGetPageTryin(int getPageTryin) {
		FxTrendParser.getPageTryin = getPageTryin;
	}

	public int getNumberOfTryReAuth() {
		return numberOfTryReAuth;
	}

	public void setNumberOfTryReAuth(int numberOfTryReAuth) {
		FxTrendParser.numberOfTryReAuth = numberOfTryReAuth;
	}

}
