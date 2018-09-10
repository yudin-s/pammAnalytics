package org.barsukov.test.samples;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.parser.parser.WebClientFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitTestBed {

	@Test
	public void test() {
		try {
			WebClient webClient = WebClientFactory.createDefaultWebClient();
			HtmlPage page = webClient.getPage("http://bash.im/random");
			print(page.getUrl().toExternalForm());
			print(page.getUrl().getHost());
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(String val) {
		System.out.println(val);

	}
}
