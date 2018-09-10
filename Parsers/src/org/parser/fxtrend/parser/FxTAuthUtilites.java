package org.parser.fxtrend.parser;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.parser.parser.NewWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class FxTAuthUtilites {
	// Track messages
	private static final String AUTHENTIFICATION_SUCCESS_MESSAGE = "fx-trend.com Authentification success";
	private static final String AUTHENTIFICATION_START_MESSAGE = "fx-trend.com Authentification start";
	private static final Logger log = LoggerFactory
			.getLogger(FxTAuthUtilites.class);
	private static final String PASS_HTTP_PARAM = "pass";
	private static final String LOGIN_HTTP_PARAM = "login";
	private static final String PAMM_TABLE = "https://fx-trend.com/my/pamm_investor/accounts/?rep_page=";
	// Links (URLs)
	private static final String LOGIN_PAGE = "https://fx-trend.com/login/myaccount";
	private static final String LOGIN_PATH = "/login";
	// Errors
	private static final String AUTH_ERROR_SYSTEM_TRY_AGAIN = "Auth Error. System Try again.";
	private static final String HTTP_ERROR = "Http error code {}. We wait and retry";
	private static final String AUTH_FALUE = "Fx-Trend.com auth falue. Detail:{}\n";

	private static int numberOfTryReAuth = 3;

	public static void auth(String login, String pass, WebClient webClient) {
		try {
			authWhithExeption(login, pass, webClient);
		} catch (IOException e) {
			log.error(AUTH_FALUE, e.getStackTrace());
		} catch (FailingHttpStatusCodeException e) {
			log.error(HTTP_ERROR, e.getStatusCode());
		}
	}

	public static int getNumberOfTryReAuth() {
		return numberOfTryReAuth;
	}

	private static void authWhithExeption(String login, String pass,
			WebClient webClient) throws IOException {
		log.info(AUTHENTIFICATION_START_MESSAGE);
		boolean authFalue = true;
		int authRetriesLeft = getNumberOfTryReAuth();
		do {
			webClient.closeAllWindows();
			WebRequest wrs = new WebRequest(new URL(LOGIN_PAGE),
					HttpMethod.POST);
			LinkedList<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new NameValuePair(LOGIN_HTTP_PARAM, login));
			params.add(new NameValuePair(PASS_HTTP_PARAM, pass));
			wrs.setRequestParameters(params);
			webClient.getPage(wrs);
			authFalue = !isAuth(webClient);
			if (authFalue) {
				log.error(AUTH_ERROR_SYSTEM_TRY_AGAIN);
			}
			authRetriesLeft = authRetriesLeft - 1;
		} while (authFalue && authRetriesLeft != 0);
		if (authFalue) {
			log.error(AUTH_FALUE);
		} else {
			log.info(AUTHENTIFICATION_SUCCESS_MESSAGE);
		}
	}

	public static boolean isAuth(WebClient webClient) {
		HtmlPage testPage;
		testPage = NewWebClient.getPage(webClient, PAMM_TABLE + 1);
		URL currentUrl = testPage.getUrl();
		String pathOfPage = currentUrl.getPath();
		return !pathOfPage.equals(LOGIN_PATH);
	}
}
