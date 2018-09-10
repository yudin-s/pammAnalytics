package org.parser.parser;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternetWaiter {
	private static final String INTERNET_RECOVER_MESSAGE = "Wait while internet connection recover fail.\nDetail: {}";

	private InternetWaiter() {

	}

	private static InternetWaiter instance = null;
	private Thread internetWaiterThread;
	private static Logger log = LoggerFactory.getLogger(InternetWaiter.class);

	public synchronized void httpErrorGiven() {
		if (internetWaiterThread == null) {
			internetWaiterThread = new InternetWaiterThread();
			internetWaiterThread.start();
		}
		try {
			wait();
		} catch (InterruptedException e) {
			log.error(INTERNET_RECOVER_MESSAGE, e.getStackTrace());
		}
	}

	public static InternetWaiter getInstance() {
		if (instance == null) {
			instance = new InternetWaiter();
		}
		return instance;
	}

	private synchronized void httpConnectionIsAccessfull() {
		notifyAll();
	}

	class InternetWaiterThread extends Thread {
		private static final String INTERNET_RECOVER = "Internet connection was recover";
		private static final String WWW_GOOGLE_COM = "www.google.com";
		private static final String THREAD_NOT_SLEEP_MESSAGE = "Thread suffers for insomnia. Detail: {}";
		private static final String INTERNET_WAITER_START_MESSAGE = "InternetWaiterThread has been started";

		public void run() {
			log.info(INTERNET_WAITER_START_MESSAGE);
			boolean addressIsNotAccesseble = true;
			do {
				@SuppressWarnings("unused")
				InetAddress address = null;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					log.error(THREAD_NOT_SLEEP_MESSAGE, e1.getMessage());
				}
				try {
					address = InetAddress.getByName(WWW_GOOGLE_COM);
					addressIsNotAccesseble = false;
				} catch (UnknownHostException e) {
					addressIsNotAccesseble = true;
				}

			} while (addressIsNotAccesseble);
			httpConnectionIsAccessfull();
			internetWaiterThread = null;
			log.info(INTERNET_RECOVER);
		}
	}

}
