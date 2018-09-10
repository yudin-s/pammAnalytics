package org.parser.fxtrend.beans;

import java.sql.Date;

public class FxtDealHistoryData {
	private Date dateOfClose;
	private String currencyPair;
	private double capacity;
	private double generalProfit;
	private double adminCapital;

	private static final String NULL_STATE = "NULL";
	private static final int NOT_SET = -1;
	private static final Date NOT_SET_DATE = new Date(0);

	public FxtDealHistoryData() {
		currencyPair = NULL_STATE;
		capacity = NOT_SET;
		generalProfit = NOT_SET;
		adminCapital = NOT_SET;
		dateOfClose = NOT_SET_DATE;
	}

	public Date getDateOfClose() {
		return dateOfClose;
	}

	public void setDateOfClose(Date dateOfClose) {
		this.dateOfClose = dateOfClose;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public double getGeneralProfit() {
		return generalProfit;
	}

	public void setGeneralProfit(double generalProfit) {
		this.generalProfit = generalProfit;
	}

	public double getAdminCapital() {
		return adminCapital;
	}

	public void setAdminCapital(double adminCapital) {
		this.adminCapital = adminCapital;
	}
}