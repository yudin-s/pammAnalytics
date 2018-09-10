package org.parser.parser;

import java.io.IOException;
import java.net.MalformedURLException;

import org.parser.fxtrend.parser.FxTAuthUtilites;
import org.parser.fxtrend.parser.FxTrendParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class NewWebClient {

	private static final int STATIC_RETRYES_VAL = 3;
	private static final int DEFAULT_VAL = 3;
	// Errors
	private static final String GET_PAGE_FAIL_MESSAGE = "Parser can't get page for parse. Thread waiting while internet access was recover";
	private static final String INVALID_URL_GET_PAGE = "Invalid URL. Unknown or missing protocol.\nDetail: {}";

	private InternetWaiter waiter = null;
	private WebClient webClient;
	private int getPageTryin = DEFAULT_VAL;

	
	
	NewWebClient(WebClient webClient) {
		this.webClient = webClient;
		waiter = InternetWaiter.getInstance();
	}

	private static final Logger log = LoggerFactory
			.getLogger(FxTrendParser.class);

	public HtmlPage getPage(String URL) {
		int tryesLeft = getGetPageTryin();
		do {
			try {
				return webClient.getPage(URL);
			} catch (FailingHttpStatusCodeException e) {
				log.error(GET_PAGE_FAIL_MESSAGE);
				waiter.httpErrorGiven();
			} catch (MalformedURLException e) {
				log.error(INVALID_URL_GET_PAGE, e.getStackTrace());
				break;
			} catch (IOException e) {
				log.error(GET_PAGE_FAIL_MESSAGE);
				waiter.httpErrorGiven();
			}
			tryesLeft = tryesLeft - 1;
		} while (tryesLeft != 0);
		return null;
	}

	public static HtmlPage getPage(WebClient webClient, String URL) {
		int tryesLeft = STATIC_RETRYES_VAL;
		InternetWaiter waiter = InternetWaiter.getInstance();
		do {
			try {
				return webClient.getPage(URL);
			} catch (FailingHttpStatusCodeException e) {
				log.error(GET_PAGE_FAIL_MESSAGE);
				waiter.httpErrorGiven();
			} catch (MalformedURLException e) {
				log.error(INVALID_URL_GET_PAGE, e.getStackTrace());
				break;
			} catch (IOException e) {
				log.error(GET_PAGE_FAIL_MESSAGE);
				waiter.httpErrorGiven();
			}
			tryesLeft = tryesLeft - 1;
		} while (tryesLeft != 0);
		return null;
	}

	public void fxtAuth(String login, String password) {
		FxTAuthUtilites.auth(login, password, webClient);
	}

	public boolean isFxTAuth() {
		return FxTAuthUtilites.isAuth(webClient);
	}

	public int getGetPageTryin() {
		return getPageTryin;
	}

	public void setGetPageTryin(int getPageTryin) {
		this.getPageTryin = getPageTryin;
	}
}
