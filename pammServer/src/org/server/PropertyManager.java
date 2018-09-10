package org.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyManager {
	private static Logger log = LoggerFactory.getLogger(PropertyManager.class);
	static private Properties prop = new Properties();

	public static String getProperty(String name) {
		String result = null;
		if (prop != null) {
			result = prop.getProperty(name);
		}
		return result;
	}

	public static String getProperty(String name, String defaultValue) {
		String result = null;
		if (prop != null) {
			result = prop.getProperty(name, defaultValue);
		}
		return result;
	}

	public static boolean loadProperty(InputStream file) {
		try {
			prop.load(file);
		} catch (IOException e) {
			log.error("I/O Exepction when read file properties. Details: {}", e.getStackTrace());
			return false;
		}
		return true;
	}

}
