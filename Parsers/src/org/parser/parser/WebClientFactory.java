package org.parser.parser;

import com.gargoylesoftware.htmlunit.*;

public class WebClientFactory {

	private static String proxyHost;
	private static boolean proxySet;
	private static int proxyPort;

	public static WebClient createDefaultWebClient() {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		webClient.setCssEnabled(false);
		webClient.setJavaScriptEnabled(false);
		if (proxyIsSet()) {
			ProxyConfig proxyConfig = new ProxyConfig();
			proxyConfig.setProxyHost(proxyHost);
			proxyConfig.setProxyPort(proxyPort);
			webClient.setProxyConfig(proxyConfig);
		}
		return webClient;
	}

	private static boolean proxyIsSet() {
		return proxySet;
	}

	public static String getProxyHost() {
		return proxyHost;
	}

	public static void setProxyHost(String proxyHost) {
		WebClientFactory.proxyHost = proxyHost;
	}

	public static boolean isProxySet() {
		return proxySet;
	}

	public static void setProxySet(boolean proxySet) {
		WebClientFactory.proxySet = proxySet;
	}

	public static int getProxyPort() {
		return proxyPort;
	}

	public static void setProxyPort(int proxyPort) {
		WebClientFactory.proxyPort = proxyPort;
	}
}
