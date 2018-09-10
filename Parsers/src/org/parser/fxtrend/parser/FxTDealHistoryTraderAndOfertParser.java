package org.parser.fxtrend.parser;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parser.fxtrend.beans.FxtDealHistoryData;
import org.parser.utilites.Pair;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class FxTDealHistoryTraderAndOfertParser extends FxTUtilsForParsers {
	// URLs
	private static final String FX_TREND_SITE = "https://fx-trend.com";
	// Pattern string
	private static final String DD_MM_YYYY_HH_MM = "dd.MM.yyyy HH:mm";
	private static final String NUMBER_IN_LINK_REGEX = "=[0-9]{1,}";
	private static final String ONLY_LINK_REGEX = "[\\S]{1,}=";
	private static final String ANCHOR_REGEX = "\\<a href=\\\"[\\s\\S]{1,}rep_page=[0-9]{1,}";
	private static final String SPLITTER_REGEX = "\\][\\s.]{1,}\\[";
	// XPathes
	private static final String PATH_TO_CHANGER = "//*[@id=\"mb_center\"]/form/table/tbody/tr[1]";
	private static final String XPATH_TO_HISTORY_TABLE = "//*[@id=\"mb_center\"]/form/table";
	// Cell constants
	private static final int DATE_CELL = 0;
	private static final int FULL_PROFIT_CELL = 4;
	private static final int ADMIN_CAPIAL_CELL = 5;
	private static final int CELLS_IN_DIV = 2;
	private static final int FIRST = 0;
	// Pattern classes
	private static final Pattern NUMBER_IN_LINK_PATTERN = Pattern
			.compile(NUMBER_IN_LINK_REGEX);
	private static final Pattern ONLY_LINK_PATTERN = Pattern
			.compile(ONLY_LINK_REGEX);
	private static final Pattern ANCHOR_PATTERN = Pattern.compile(ANCHOR_REGEX);
	private static final Pattern SPLIT_PATTERN = Pattern
			.compile(SPLITTER_REGEX);

	public static List<HtmlTableRow> getHistoryTableRows(HtmlPage historyPage) {
		List<?> list = historyPage.getByXPath(XPATH_TO_HISTORY_TABLE);
		List<HtmlTableRow> result = null;
		if (listNotEmpty(list)) {
			HtmlTable table = (HtmlTable) list.get(FIRST);
			result = table.getRows();
		}
		return result;
	}

	private static boolean listNotEmpty(List<?> list) {
		return list.size() > 0;
	}

	public static void parseTable(List<HtmlTableRow> rows, Date maxDate,
			List<FxtDealHistoryData> result) throws Exception {
		int size = rows.size();
		Date date;
		for (int i = 0; i < size; i++) {
			HtmlTableRow row = (HtmlTableRow) rows.get(i);
			if (itsDataLine(row)) {
				date = parseDateWithTime(getFromCell(DATE_CELL, row));

				if (dateBigestThanCompare(date, maxDate)) {
					try {
						FxtDealHistoryData data = new FxtDealHistoryData();
						data.setDateOfClose(date);
						data.setAdminCapital(getDoubleFromCell(
								ADMIN_CAPIAL_CELL, row));
						data.setGeneralProfit((getDoubleFromCell(
								FULL_PROFIT_CELL, row)));
						result.add(data);
					} catch (NumberFormatException e) {
						// To do nothing. It's header
					}
				} else {
					throw new Exception();
				}
			}
		}
	}

	private static boolean itsDataLine(HtmlTableRow row) {
		return row.getCells().size() > CELLS_IN_DIV;
	}

	private static boolean dateBigestThanCompare(Date date, Date compare) {
		return date.compareTo(compare) > 0;
	}

	private static String getFromCell(int cellIndex, HtmlTableRow row) {
		String result;
		HtmlTableCell cell = row.getCell(cellIndex);
		if (objectExist(cell)) {
			result = cell.getTextContent();
		} else {
			result = null;
		}
		return result;
	}

	private static Double getDoubleFromCell(int cellIndex, HtmlTableRow row) {
		String result;
		HtmlTableCell cell = row.getCell(cellIndex);
		if (objectExist(cell)) {
			result = onlyNumbers(cell.getTextContent());
		} else {
			result = null;
		}
		return Double.parseDouble(result);
	}

	private static boolean objectExist(HtmlTableCell cell) {
		return cell != null;
	}

	private static Date parseDateWithTime(String date) {
		Date result;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY_HH_MM);
		try {
			result = new Date(
					((java.util.Date) dateFormat.parse(date)).getTime());
		} catch (ParseException e) {
			result = new Date(System.currentTimeMillis() + 10);
		}
		return result;
	}

	private static int getOfferListLastPageID(String link) {
		int result = 0;
		Matcher match = NUMBER_IN_LINK_PATTERN.matcher(link);
		if (match.find()) {
			result = Integer.parseInt(link.substring(match.start() + 1,
					match.end()));
		}

		return result;
	}

	private static String getFullUrlFromHref(String hrefLink) {
		Matcher match = ONLY_LINK_PATTERN.matcher(hrefLink);
		if (match.find())
			return FX_TREND_SITE
					+ hrefLink.substring(match.start(), match.end());
		return null;
	}

	public static Pair<Integer, String> getLinkAndLastPageFromSpecialCell(
			String textContents) {
		Pair<Integer, String> pair = new Pair<Integer, String>();
		String temp = null;
		String arr[] = SPLIT_PATTERN.split(textContents);
		Matcher match = ANCHOR_PATTERN.matcher(temp = arr[arr.length - 1]);
		if (match.find()) {
			temp = temp.substring(match.start() + 9, match.end());
			pair.setLast(getFullUrlFromHref(temp));
			pair.setFirst(getOfferListLastPageID(temp));
		}
		return pair;
	}

	public static String getChanger(HtmlPage page) {
		String result;
		List<?> list = page.getByXPath(PATH_TO_CHANGER);
		if (list.size() > 0) {
			HtmlTableRow row = (HtmlTableRow) list.get(0);
			if (itsChanger(row)) {
				result = row.asXml();
			} else
				result = null;
		} else {
			result = null;
		}
		return result;
	}

	private static boolean itsChanger(HtmlTableRow row) {
		return row.getCells().size() < 2;
	}
}
