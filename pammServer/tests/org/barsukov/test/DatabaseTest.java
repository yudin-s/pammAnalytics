package org.barsukov.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.server.PropertyManager;
import org.server.data.Database;

public class DatabaseTest {

	private static final String ANSI_SQL_EXAMPLE_QUERY = "CREATE TABLE Persons (P_Id int, LastName varchar(255), FirstName varchar(255), Address varchar(255), City varchar(255))";
	private static final String DB_SCHEMA = "dbSchema = PUBLIC\n";
	private static final String DB_LOGIN = "dbLogin = login\n";
	private static final String DB_INIT_LINE = "dbJdbcInitLine = jdbc:h2:mem:~/testo\n";
	private static final String DB_PASSWORD = "dbPassword = password\n";

	@Before
	public void setUp() throws Exception {
		String imitatePropertyFileString = DB_LOGIN + DB_PASSWORD
				+ DB_INIT_LINE + DB_SCHEMA;
		ByteArrayInputStream virtualPropertyFile = new ByteArrayInputStream(
				imitatePropertyFileString.getBytes());
		PropertyManager.loadProperty(virtualPropertyFile);
	}

	@Test
	public void testGetStatement() {
		try {
			Database database = new Database();
			assertNotNull(database.getStatement());
		} catch (SQLException e) {
			fail("SQL Exception "+e.getMessage());
		}	
	}
	@Test
	public void testAnsiSqlQuery(){
		try {
			Database database = new Database();
		    Statement stm = database.getStatement();
		    stm.execute(ANSI_SQL_EXAMPLE_QUERY);
		} catch (SQLException e) {
			fail("SQL Exception "+e.getMessage());
		}
	}

}
