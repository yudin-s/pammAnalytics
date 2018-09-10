package org.barsukov.test.samples;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.server.FxTrendParserThread;
import org.server.MainServer;
import org.server.data.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumOfOpenDealsBiggestZero {
	Logger log = LoggerFactory.getLogger(NumOfOpenDealsBiggestZero.class);

	// @Test
	public void queryTest() {
		StringBuffer buffer = new StringBuffer();
		DataManager manager = DataManager.getInstance();
		Statement stm = manager.getStatement();
		String sql = "SELECT ACCOUNT_NUMBER, ADMIN_ID, NUM_OPEN_DEALS FROM FXT_ACCOUNTS WHERE NUM_OPEN_DEALS > 0";
		try {
			ResultSet set = stm.executeQuery(sql);
			while (set.next()) {
				buffer.append(set.getString("ADMIN_ID") + " " + set.getInt("ACCOUNT_NUMBER") + " openDeals: "
						+ set.getInt("NUM_OPEN_DEALS") + "\n");
			}
			// printLine(buffer.toString());
			log.error("Account with open ofert:\n{}", buffer.toString());
		} catch (SQLException e) {
			fail("SQLException error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		MainServer.loadProperties();
		MainServer.initFxTrendParser();
		FxTrendParserThread thread = FxTrendParserThread.getInstace();
		if (thread.updateData()) {
			queryTest();
		}

	}

	void printLine(String line) {
		System.out.println(line);
	}
}
