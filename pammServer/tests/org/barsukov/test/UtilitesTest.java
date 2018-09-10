package org.barsukov.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.server.PropertyManager;

public class UtilitesTest {
	private String PROPERTY_FILE = "server.properties";
	@Test
	public void testLoadProperties() {
		try {
			assertTrue(PropertyManager.loadProperty(new FileInputStream(PROPERTY_FILE)));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

}
