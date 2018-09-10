package org.barsukov.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;
import org.parser.parser.WebClientFactory;
import org.server.PropertyManager;

import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;

public class WebClientFactoryTest {
	private static final String PROXY_SET = "proxySet = true\n";
	private static final String PROXY_PORT = "proxyPort = 7\n";
	private static final String PROXY_HOST = "proxyHost = 8.8.8.8\n";
	WebClient webClient;

	@Before
	public void setUp() {
		String imitatePropertyFileString = PROXY_SET + PROXY_HOST + PROXY_PORT;
		ByteArrayInputStream virtualPropertyFile = new ByteArrayInputStream(
				imitatePropertyFileString.getBytes());
		PropertyManager.loadProperty(virtualPropertyFile);
		webClient = WebClientFactory.createDefaultWebClient();
	}

	@Test
	public void testIsJavaScriptEnabled() {
		assertFalse(webClient.isJavaScriptEnabled());
	}

	@Test
	public void testIsCssEnabled() {
		assertFalse(webClient.isCssEnabled());
	}

	@Test
	public void testGetProxyConfig() {
		ProxyConfig proxy = webClient.getProxyConfig();
		assertNotNull(proxy.getProxyHost());
		assertNotNull(proxy.getProxyPort());
	}

}
