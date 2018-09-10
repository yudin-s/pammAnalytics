package org.barsukov.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogbackTest {
	private org.slf4j.Logger log = LoggerFactory.getLogger(LogbackTest.class);

	@Test
	public void test() {
		Marker marker = MarkerFactory.getMarker("EMAIL_MARKER");
		log.error(marker, "hello",new Exception("Just testing"));
		log.error("hello");
		log.error("hello");
		log.info(marker,"world",new Exception("Just testing"));

		log.debug("test");
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true)
					;
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
