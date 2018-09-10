package org.parser.fxtrend.parser;

import java.sql.Date;

import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtOfferData;
import org.parser.fxtrend.exception.TableWasNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class FxTPammTwoTraderAndOfertParser extends FxTUtilsForParsers {

	private static final int STRATEGY_ROW = 13;
	private static final int STOPOUT_ROW = 7;
	private static final int RESPONSIBILITY_ROW = 5;
	private static final int PERIOD_ROW = 2;
	private static final int OFFER_ID_ROW = 1;
	private static final int MINN_SUMM_AND_LEFT_ROW = 6;
	private static final int LEVEL_REPORT_ROW = 10;
	private static final int CONNECTION_METHOD_ROW = 12;
	private static final int COMMISSION_ROW = 4;
	private static final int AGENTS_REWARD_ROW = 9;
	private static final int AGENTS_CALLING_ROW = 8;
	private static final int ADMIN_REWARD_ROW = 3;
	private static final int NOT_EXIST = -1;
	private static final int NEXT_ROLLOVER_ROW = 12;
	private static final int STATE_ROW = 1;
	private static final int MAXDROPDOWN_ROW = 8;
	private static final int START_CAPITAL_ROW = 2;
	private static final int OPEN_DEALS_ROW = 10;
	private static final int INVESTITIONS_ROW = 4;
	private static final int CURRENT_CAPITAL_ROW = 3;
	private static final int AVGDROPDOWN_ROW = 7;
	private static final int AVGDROPDOWN_WITHOUT_OFFER_ROW = AVGDROPDOWN_ROW - 2;
	private static final int ACCOUNT_NUMBER_ROW = 0;
	private static final int DATA_COL = 1;
	
	private static final int PAMM2 = 2;
	
	private static final String EPIC_FAIL_ERROR = "Table with impotant base accounts data not found ({}) ";
	private static final String OFFER_TABLE_NOT_FOUND = "FATAL ERROR! Offer table not found but Offer was open ({})";
	
	private static final String XPATH_OFFER_TABLE = "//*[@id=\"mb_center\"]/table[2]";
	private static final String FULL_PATH_TO_OFFER_ID = "//*[@id=\"mb_center\"]/table[2]/tbody/tr[2]/td[2]";
	private static final String PAMM_TWO_TABLE_XPATH = "//*[@id=\"mb_center\"]/table[1]/tbody/tr[1]/td/table";
	private static Logger log = LoggerFactory.getLogger(FxTPammTwoTraderAndOfertParser.class);

	public FxtAccountsData parseBaseAccountData(HtmlPage page) {
		FxtAccountsData data = null;
		try {
			HtmlTable table = getTableByXPath(PAMM_TWO_TABLE_XPATH, page);
			data = new FxtAccountsData();
			data.setAccountNumber(getInt(ACCOUNT_NUMBER_ROW, DATA_COL, table));
			data.setCurrentCapital(getDouble(CURRENT_CAPITAL_ROW, DATA_COL, table));
			data.setInvestitions(getDouble(INVESTITIONS_ROW, DATA_COL, table));
			data.setStartCapital(getDouble(START_CAPITAL_ROW, DATA_COL, table));

			String state = getDataFromTable(STATE_ROW, DATA_COL, table);
			state = parseState(state);
			data.setState(state);

			if (offerIsOpen(page)) {
				data.setIdLastOffer(getOfferId(page));
				data.setAvgrageDropdown(getDouble(AVGDROPDOWN_ROW, DATA_COL, table));
				data.setNumOfOpenDeals(getInt(OPEN_DEALS_ROW, DATA_COL, table));
				data.setMaxDropdown(getDouble(MAXDROPDOWN_ROW, DATA_COL, table));
				String date = getDataFromTable(NEXT_ROLLOVER_ROW, DATA_COL, table);
				data.setNextRollover(getNextOfferDate(date));
			} else {
				data.setAvgrageDropdown(getDouble(AVGDROPDOWN_WITHOUT_OFFER_ROW, DATA_COL, table));
				data.setNumOfOpenDeals(getInt(OPEN_DEALS_ROW - 2, DATA_COL, table));
				data.setMaxDropdown(getDouble(MAXDROPDOWN_ROW - 2, DATA_COL, table));
			}
		} catch (TableWasNotFound e) {
			log.error(EPIC_FAIL_ERROR, page);
		}
		return data;
	}

	private int getOfferId(HtmlPage page) {
		String rez = getDataByXpath(FULL_PATH_TO_OFFER_ID, page);
		if (rez != null)
			return Integer.parseInt(rez);
		else
			return NOT_EXIST;
	}

	private Date getNextOfferDate(String val) {
		val = val.substring(0, 10);
		return parseDateWithoutTime(val);
	}

	public FxtOfferData parseOfferData(HtmlPage page) {
		FxtOfferData data = null;
		if (offerIsOpen(page)) {
			try {
				data = new FxtOfferData();
				HtmlTable table = getTableByXPath(XPATH_OFFER_TABLE, page);
				data.setType(PAMM2);
				data.setAccountNumber(NOT_EXIST);

				data.setAdminReward(getDouble(ADMIN_REWARD_ROW, DATA_COL, table));
				data.setAgentsFromCalling(getDouble(AGENTS_CALLING_ROW, DATA_COL, table));
				data.setAgentsFromReward(getDouble(AGENTS_REWARD_ROW, DATA_COL, table));
				data.setCommission(getDouble(COMMISSION_ROW, DATA_COL, table));
				data.setConnecitonMethod(getDataFromTable(CONNECTION_METHOD_ROW, DATA_COL, table));
				data.setLevelReport(getDataFromTable(LEVEL_REPORT_ROW, DATA_COL, table));
				data.setMinSummAndLeft(getDouble(MINN_SUMM_AND_LEFT_ROW, DATA_COL, table));
				data.setOfferID(getInt(OFFER_ID_ROW, DATA_COL, table));
				data.setPeriod(getInt(PERIOD_ROW, DATA_COL, table));
				data.setResponsibility(getDouble(RESPONSIBILITY_ROW, DATA_COL, table));
				data.setStopoutByCA(getDouble(STOPOUT_ROW, DATA_COL, table));
				data.setStrategy(getDataFromTable(STRATEGY_ROW, DATA_COL, table));

			} catch (TableWasNotFound e) {
				log.error(OFFER_TABLE_NOT_FOUND, page);
			}
		}
		return data;
	}

}
