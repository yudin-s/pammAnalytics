package org.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.parser.fxtrend.parser.IFxtData;
import org.parser.parser.WebClientFactory;
import org.server.data.DataManager;
import org.server.rmi.RMIImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainServer {

	private static final String FALSE = "false";
	private static final String PROXY_SET = "proxySet";
	private static final String PROXY_PORT = "proxyPort";
	private static final String PROXY_HOST = "proxyHost";
	private static final int PORT = 12345;
	private static final String PAMM_SERVER = "pammServer";
	private static final String BOUND_ERROR = "Already class has been bound {}";
	private static final String REMOTE_EXCEPTION = "Remote exception {}";
	private static final String TIME_PROPERTY = "fx-trendTime";
	private static final String PASSWORD_PROPERTY = "fx-trendPassword";
	private static final String LOGIN_PROPERTY = "fx-trendLogin";
	private static final String I_O_ERROR_DETAIL = "I/O Error. Detail: {}";
	private static final String UNEXPECTED_ERROR_MESSAGE_STACKTRACE = "Unexpected error. Message: {}\nStacktrace: {}";
	private static final String ONE_HOUR_STRING = "3600000";
	private static final String PROPERTY_FILE = "server.properties";
	private static Logger log = LoggerFactory.getLogger(MainServer.class);
	private static FxTrendParserThread fxTrendParserThread = null;

	public static void main(String[] args) {
		try {
			loadProperties();
			initRMI();
			initFxTrendParser();
			startFxTrendParserThread();
		} catch (Throwable t) {
			log.error(UNEXPECTED_ERROR_MESSAGE_STACKTRACE, t.getMessage(), t.getStackTrace());
		}
	}

	public static void loadProperties() {
		try {
			PropertyManager.loadProperty(new FileInputStream(PROPERTY_FILE));
		} catch (IOException ex) {
			log.error(I_O_ERROR_DETAIL, ex.getMessage());
		}
	}

	private static void initRMI() {
		try {
			RMIImplementation rmiServer = new RMIImplementation();
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.bind(PAMM_SERVER, rmiServer);
		} catch (RemoteException e) {
			log.error(REMOTE_EXCEPTION, e.getStackTrace());
		} catch (AlreadyBoundException e) {
			log.error(BOUND_ERROR, e.getStackTrace());
		}
	}

	public static void initFxTrendParser() {
		String login = PropertyManager.getProperty(LOGIN_PROPERTY);
		String password = PropertyManager.getProperty(PASSWORD_PROPERTY);

		long delayTime = Long.parseLong(PropertyManager.getProperty(TIME_PROPERTY, ONE_HOUR_STRING));
		IFxtData targetDataBase = (IFxtData) DataManager.getInstance();

		WebClientFactory.setProxyHost(PropertyManager.getProperty(PROXY_HOST));
		WebClientFactory.setProxyPort(Integer.parseInt(PropertyManager.getProperty(PROXY_PORT)));
		WebClientFactory.setProxySet(Boolean.parseBoolean(PropertyManager.getProperty(PROXY_SET, FALSE)));

		FxTrendParserThread.setDatabase(targetDataBase);
		FxTrendParserThread.setLogin(login);
		FxTrendParserThread.setPass(password);
		FxTrendParserThread.setWaitTime(delayTime);
		
	}

	private static void startFxTrendParserThread() {
		fxTrendParserThread = FxTrendParserThread.getInstace();
		if (fxTrendParserCanStart()) {
			fxTrendParserThread.start();
		}
	}

	private static boolean fxTrendParserCanStart() {
		return fxTrendParserThread != null && !fxTrendParserThread.isAlive();
	}
}
