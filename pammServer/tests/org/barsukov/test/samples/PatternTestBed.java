package org.barsukov.test.samples;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.server.Pair;

public class PatternTestBed {

	@Test
	public void test() {
		String text = "<td colspan=\"7\" align=\"center\">               <b>[1-50]</b>                 [<a href=\"/my/pamm2_investor/details/116000/?rep_order=&amp;rep_direct=&amp;rep_page=2\" onclick=\"document.forms['rep'].elements['rep_page'].value='2'; document.forms['rep'].submit(); return false;\">51-100</a>]                 [<a href=\"/my/pamm2_investor/details/116000/?rep_order=&amp;rep_direct=&amp;rep_page=3\" onclick=\"document.forms['rep'].elements['rep_page'].value='3'; document.forms['rep'].submit(); return false;\">101-150</a>]                 [<a href=\"/my/pamm2_investor/details/116000/?rep_order=&amp;rep_direct=&amp;rep_page=4\" onclick=\"document.forms['rep'].elements['rep_page'].value='4'; document.forms['rep'].submit(); return false;\">151-200</a>]                 [<a href=\"/my/pamm2_investor/details/116000/?rep_order=&amp;rep_direct=&amp;rep_page=5\" onclick=\"document.forms['rep'].elements['rep_page'].value='5'; document.forms['rep'].submit(); return false;\">201-229</a>]         <br>     Total: 229 </td>";
		Pair<Integer, String> pair = getLinkAndLastPageFromSpecialCell(text);
		print(pair.getLast());
		print(Integer.toString(pair.getFirst()));
	}

	private static final String NUMBER_IN_LINK_REGEX = "=[0-9]{1,}";
	private static final Pattern NUMBER_IN_LINK_PATTERN = Pattern
			.compile(NUMBER_IN_LINK_REGEX);

	private static int getOfferListLastPageID(String link) {
		int result = 0;
		Matcher match = NUMBER_IN_LINK_PATTERN.matcher(link);
		if (match.find()) {
			result = Integer.parseInt(link.substring(match.start() + 1,
					match.end()));
		}

		return result;
	}

	private static final String FX_TREND_SITE = "https://fx-trend.com";

	private String getFullUrlFromHref(String hrefLink) {
		return FX_TREND_SITE + hrefLink;
	}

	private static final String ANCHOR_REGEX = "\\<a href=\\\"[\\s\\S]{1,}rep_page=[0-9]{1,}\\\"";
	private static final Pattern ANCHOR_PATTERN = Pattern.compile(ANCHOR_REGEX);
	private static final String SPLITTER_REGEX = "\\][\\s]{1,}\\[";
	private static final Pattern SPLIT_PATTERN = Pattern
			.compile(SPLITTER_REGEX);

	private Pair<Integer, String> getLinkAndLastPageFromSpecialCell(
			String textContents) {
		Pair<Integer, String> pair = new Pair<Integer, String>();
		String temp = null;
		String arr[] = SPLIT_PATTERN.split(textContents);
		Matcher match = ANCHOR_PATTERN.matcher(temp = arr[arr.length - 1]);
		if (match.find()) {
			temp = temp.substring(match.start() + 9, match.end() - 1);
			pair.setLast(getFullUrlFromHref(temp));
			pair.setFirst(getOfferListLastPageID(temp));
		}
		return pair;
	}

	private void print(String val) {
		System.out.println(val);

	}
}
