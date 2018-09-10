package org.shared.api.ver1;

import java.io.Serializable;

public class MainDataDTO implements Serializable{
	private static final long serialVersionUID = 8978817276029183444L;
	private int ID;
	private String login;
	private String dateReg;
	private char type;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDateReg() {
		return dateReg;
	}

	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

}
