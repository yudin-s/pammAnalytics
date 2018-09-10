package org.parser.fxtrend.parser;

import java.sql.Date;

import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtOfferData;
import org.parser.fxtrend.exception.TableWasNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class FxTPammOneTraderAndOfertParser extends FxTUtilsForParsers {

	private static final String EPIC_ERROR = "Page {} has been changed. Table with trader's data isn't found.";
	private static final String PAGE_NOT_PARSED = "Page not parsed! {}";
	private static final String OFFER_TABLE_GET_ERROR = "Table with Offer detail not found, but Offer is open ({})";
	
	private static final String FULL_XPATH_TO_OFFER_ID = "//*[@id=\"mb_center\"]/table[2]/tbody/tr[2]/td[2]";
	private static final String XPATH_TO_BASE_ACCOUNT_DATA = "//*[@id=\"mb_center\"]/table[1]/tbody/tr[1]/td/table";
	private static final String OFFER_TABLE = "//*[@id=\"mb_center\"]/table[2]";
	
	private static final int ACCOUNT_NUMBER_ROW = 0;
	private static final int STRATEGY_ROW = 12;
	private static final int STOPOUT_BY_CA_ROW = 7;
	private static final int TRADE_PERIOD_ROW = 2;
	private static final int OFFER_ID_ROW = 1;
	private static final int MIN_SUMM_AND_LEFT_ROW = 6;
	private static final int CONNECTION_METHOD_ROW = 11;
	private static final int LEVEL_REPORT_ROW = 10;
	private static final int COMMISSION_ROW = 4;
	private static final int AGENTS_FROM_REWARD_ROW = 9;
	private static final int AGENTS_FROM_CALLING_ROW = 8;
	private static final int ADMIN_REWARD_ROW = 3;
	private static final int PAMM_ONE = 1;
	private static final int NOT_EXIST = -1;
	private static final int ROLLOVER_ROW = 10;
	private static final int OPEN_DEALS_ROW = 8;
	private static final int INVESTITIONS_ROW = 4;
	private static final int STATE_ROW = 1;
	private static final int START_CAPITAL_ROW = 2;
	private static final int MAX_DRDOWN_ROW = 6;
	private static final int CURRENT_CAPITAL_ROW = 3;
	private static final int AVG_DRDOWN_ROW = 5;
	private static final int DATA = 1;
	private static final String CLOSED_ACCOUNT_BY_CA = "Закрытие счета по стоп-ауту КУ:";
	
	private static Logger log = LoggerFactory.getLogger(FxTPammOneTraderAndOfertParser.class);

	public FxtAccountsData parseBaseAccountData(HtmlPage page) {
		try {
			HtmlTable table = getTableByXPath(XPATH_TO_BASE_ACCOUNT_DATA, page);

			FxtAccountsData mainData = new FxtAccountsData();
			mainData.setDateReg(new Date(0));
			mainData.setIdAdmin(null);
			mainData.setAccountNumber(getInt(ACCOUNT_NUMBER_ROW, DATA, table));
			mainData.setCurrentCapital(getDouble(CURRENT_CAPITAL_ROW, DATA, table));
			mainData.setStartCapital(getDouble(START_CAPITAL_ROW, DATA, table));
			String state = getDataFromTable(STATE_ROW, DATA, table);
			state = parseState(state);
			mainData.setState(state);

			if (isAccountClosedByStopout(table)) {

				mainData.setNumOfOpenDeals(getInt(OPEN_DEALS_ROW, DATA, table));
				mainData.setMaxDropdown(getDouble(MAX_DRDOWN_ROW, DATA, table));
				mainData.setInvestitions(getDouble(INVESTITIONS_ROW, DATA, table));
				mainData.setAvgrageDropdown(getDouble(AVG_DRDOWN_ROW, DATA, table));

				if (offerIsOpen(page)) {
					mainData.setIdLastOffer(getOfferId(page));
					String date = getDataFromTable(ROLLOVER_ROW, DATA, table);
					mainData.setNextRollover(getNextOffer(date));
				}
			} else {

				mainData.setNumOfOpenDeals(getInt(OPEN_DEALS_ROW + 1, DATA, table));

				mainData.setMaxDropdown(getDouble(MAX_DRDOWN_ROW + 1, DATA, table));
				mainData.setInvestitions(getDouble(INVESTITIONS_ROW + 1, DATA, table));
				mainData.setAvgrageDropdown(getDouble(AVG_DRDOWN_ROW + 1, DATA, table));

				if (offerIsOpen(page)) {

					mainData.setIdLastOffer(getOfferId(page));
					String date = getDataFromTable(ROLLOVER_ROW + 1, DATA, table);
					mainData.setNextRollover(getNextOffer(date));

				}

			}
			return mainData;
		} catch (TableWasNotFound e) {
			log.error(EPIC_ERROR, page.toString());
			return null;
		} catch (Throwable e) {
			log.error(PAGE_NOT_PARSED, page);
		}
		return null;
	}

	private boolean isAccountClosedByStopout(HtmlTable table) {
		return !getDataFromTable(4, 0, table).equals(CLOSED_ACCOUNT_BY_CA);
	}

	private int getOfferId(HtmlPage page) {
		String rez = getDataByXpath(FULL_XPATH_TO_OFFER_ID, page);
		if (rez != null)
			return Integer.parseInt(rez);
		else
			return NOT_EXIST;
	}

	private Date getNextOffer(String val) {
		val = val.substring(0, 10);
		return parseDateWithoutTime(val);
	}

	public FxtOfferData parseOfferData(HtmlPage page) {
		FxtOfferData data = null;
		if (offerIsOpen(page)) {
			try {
				HtmlTable table = getTableByXPath(OFFER_TABLE, page);
				data = new FxtOfferData();

				data.setType(PAMM_ONE);
				data.setResponsibility(NOT_EXIST);
				data.setAdminReward(getDouble(ADMIN_REWARD_ROW, DATA, table));
				data.setAgentsFromCalling(getDouble(AGENTS_FROM_CALLING_ROW, DATA, table));
				data.setAgentsFromReward(getDouble(AGENTS_FROM_REWARD_ROW, DATA, table));
				data.setCommission(getDouble(COMMISSION_ROW, DATA, table));
				data.setLevelReport(getDataFromTable(LEVEL_REPORT_ROW, DATA, table));
				data.setConnecitonMethod(getDataFromTable(CONNECTION_METHOD_ROW, DATA, table));
				data.setMinSummAndLeft(getDouble(MIN_SUMM_AND_LEFT_ROW, DATA, table));
				data.setOfferID(getInt(OFFER_ID_ROW, DATA, table));
				data.setPeriod(getInt(TRADE_PERIOD_ROW, DATA, table));
				data.setStopoutByCA(getInt(STOPOUT_BY_CA_ROW, DATA, table));
				data.setStrategy(getDataFromTable(STRATEGY_ROW, DATA, table));
			} catch (TableWasNotFound e) {
				log.error(OFFER_TABLE_GET_ERROR, page);
			}

		}
		return data;
	}

}
