package org.parser.parser;

import com.gargoylesoftware.htmlunit.WebClient;

public class NewWebClientFactory {
	public static NewWebClient createClient(WebClient baseClient) {
		return new NewWebClient(baseClient);
	}
}
