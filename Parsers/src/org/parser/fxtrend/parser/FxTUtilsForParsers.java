package org.parser.fxtrend.parser;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.parser.fxtrend.exception.TableWasNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public abstract class FxTUtilsForParsers {
	// Messages
	private static final String TABLE_NOT_FOUND = "Table not found: {}";
	private static final String UNEXPECTED_STATE = "Unexpected state: {}";
	private static final String INVALID_DATE = "Invalid date: {}";
	// XPathes
	private static final String XPATH_TO_Offer_OPEN_DETECTOR = "//*[@id=\"mb_center\"]/table[2]/tbody/tr[2]/td[2]";
	// Patterns
	private static final String DD_MM_YYYY = "dd.MM.yyyy";
	private static final String NOT_NUMBER_REGEX = "[^0-9\\.]{1,}";
	// Constants
	private static final String EMPRY = "";
	private static final String STRING_ZERO = "0";
	private static final String NULL = "NULL";
	private static final String CLOSED = "CLOSED";
	private static final String CLOSED_RU = "Закрыт";
	private static final String OPEN = "OPEN";
	private static final String OPEN_RU = "Открыт";

	private static Logger log = LoggerFactory.getLogger(FxTUtilsForParsers.class);

	protected static String onlyNumbers(String source) {
		String temp;
		return (temp = source.replaceAll(NOT_NUMBER_REGEX, EMPRY)).isEmpty() ? STRING_ZERO : temp;
	}

	protected Date parseDateWithoutTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		try {
			return new java.sql.Date(format.parse(date).getTime());
		} catch (ParseException e) {
			log.error(INVALID_DATE, e.getMessage());
			return new java.sql.Date(System.currentTimeMillis());
		}
	}

	protected String parseState(String uncheckedState) {
		if (uncheckedState.equals(OPEN_RU)) {
			return OPEN;
		}
		if (uncheckedState.equals(CLOSED_RU)) {
			return CLOSED;
		} else {
			log.error(UNEXPECTED_STATE, uncheckedState);
			return NULL;
		}
	}

	protected boolean offerIsOpen(HtmlPage page) {
		List<?> itNotBeenEmptyOfferIsBeenOpen = page.getByXPath(XPATH_TO_Offer_OPEN_DETECTOR);
		return !(itNotBeenEmptyOfferIsBeenOpen.isEmpty());
	}

	protected Double getDouble(int row, int col, HtmlTable table) {
		return Double.parseDouble(onlyNumbers(getDataFromTable(row, col, table)));
	}

	protected Integer getInt(int row, int col, HtmlTable table) {
		return Integer.parseInt(onlyNumbers(getDataFromTable(row, col, table)));
	}

	protected static String getDataFromTable(int row, int col, HtmlTable table) {
		try {
			return table.getCellAt(row, col).getTextContent();
		} catch (Throwable t) {
			log.error(TABLE_NOT_FOUND, table);
		}
		return EMPRY;
	}

	protected HtmlTable getTableByXPath(String xpath, HtmlPage page) throws TableWasNotFound {
		List<?> list = page.getByXPath(xpath);
		if (list.isEmpty()) {
			throw new TableWasNotFound();
		} else {
			return (HtmlTable) list.get(0);
		}
	}

	protected static String getDataByXpath(String xpath, HtmlPage page) {
		List<?> list = page.getByXPath(xpath);
		if (list.isEmpty()) {
			return null;
		} else {
			return ((HtmlTableDataCell) list.get(0)).getTextContent();
		}
	}
}
