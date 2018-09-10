package org.barsukov.test.samples;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.parser.fxtrend.parser.FxTDealHistoryTraderAndOfertParser;
import org.parser.fxtrend.parser.FxTrendParser;
import org.parser.parser.WebClientFactory;
import org.server.data.DataManager;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class OfferTestBed {

	@Test
	public void test() {
//		Этот кусок кода нуждается в доступе к закрытым методам. Перед его запуском эти методы нужно открыть
//		FxTrendParser parser = new FxTrendParser(DataManager.getInstance(), 0,
//				"popov_denis", "azrail666");
//		String val = parser
//				.getURLToOfferHistory("http://fx-trend.com/my/pamm2_investor/accounts/details/12248/");
//		HtmlPage OfferHistoryPage = parser.getFirstPageOfOfferHistory(val);
//		Object[] objArr = DealHistoryParser.parseDealsHistoryPage(
//				OfferHistoryPage, 0, 0);
//		System.out.print(objArr.length);
//		assertTrue(objArr.length > 0);
		
	}
}
