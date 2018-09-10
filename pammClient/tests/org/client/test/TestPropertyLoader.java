package org.client.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.client.datamanager.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPropertyLoader {
	private static final String TEST_FILE_NAME = "test.properties";
	private static final String EQUAL = " = ";
	private static final String ENDL = "\n";
	private static final String CHECK_VALUE = "checkValue";
	private static final String RMI_HOST = "rmiHost";
	private static final Map<String, String> testParams = new HashMap<String, String>();

	@Before
	public void setUp() {
		setParams();
		try {
			File file = new File(TEST_FILE_NAME);
			file.delete();
			file.createNewFile();
			assert file.canWrite() : "File cannot write";

			FileOutputStream os = new FileOutputStream(file);
			for (Map.Entry<String, String> entry : testParams.entrySet()) {
				os.write(createParamsLine(entry.getKey(), entry.getValue())
						.getBytes());
			}
			PropertyManager.loadProperty(new FileInputStream(TEST_FILE_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() {
		File file = new File(TEST_FILE_NAME);
		file.delete();
	}

	@Test
	public void test() {
		for (Map.Entry<String, String> entry : testParams.entrySet()) {
			assertEquals(PropertyManager.getProperty(entry.getKey()),
					entry.getValue());
		}
	}

	private String createParamsLine(String paramName, String value) {
		return paramName + EQUAL + value + ENDL;
	}

	private void setParams() {
		testParams.put(RMI_HOST, CHECK_VALUE);
		testParams.put("TestParam1", "TestVal1");
		testParams.put("TestParam2", "TestVal2");
		testParams.put("TestParam3", "TestVal3");
		testParams.put("TestParam4", "TestVal4");
		testParams.put("TestParam5", "TestVal5");
	}
}
