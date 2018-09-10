package org.client.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.client.datamanager.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For security reasons that class collection all custom init code
 * 
 * @author serge
 */

public class CustomInitCode {
	private static final String CLIENT_PROPERTIES = "client.properties";
	private static final String CLIENT_POLICY = "./client.policy";
	private static Logger log = LoggerFactory.getLogger(CustomInitCode.class);

	public static void init() {
		log.info("CUSTOM");
		createPolicyFileIfFileNotExist();

		loadClientProperties();

		setCustomSystemProperty();

	}

	private static void loadClientProperties() {
		try {
		
			PropertyManager
					.loadProperty(new FileInputStream(CLIENT_PROPERTIES));
		} catch (FileNotFoundException e) {
			log.error("File with properties not found! Properties not loaded");
		}
	}

	private static void setCustomSystemProperty() {
		System.setProperty("java.security.policy", CLIENT_POLICY);
		System.setProperty("file.encoding", "UTF-8");
		System.setProperty("client.encoding.override", "UTF-8");
		// RMISecurityManager rmiSecurityManager = new RMISecurityManager();
		// System.setSecurityManager(rmiSecurityManager);
	}

	private static void createPolicyFileIfFileNotExist() {
		File f = new File(CLIENT_POLICY);
		try {
			if (!f.createNewFile()) {
				f.delete();
			}
			f.createNewFile();
			FileOutputStream os = new FileOutputStream(f);
			os.write("grant { \n permission java.security.AllPermission;\n };"
					.getBytes());
			os.flush();
			os.close();
		} catch (FileNotFoundException e1) {
			log.error("Policy file not found \n{}", e1.getStackTrace());
		} catch (IOException e1) {
			log.error("Something wrong I/O exception {}", e1.getMessage());
		}
	}
}
