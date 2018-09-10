package org.server.data;

import java.sql.PreparedStatement;
import java.sql.Statement;

public interface IData {

	public Statement getStatement();

	public PreparedStatement getPreperedStatement(String stm);

}