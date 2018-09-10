package org.shared.api.ver2.dto;

import java.io.Serializable;
import java.sql.Date;

public class FxTDealsDTO implements Serializable {
	private static final long serialVersionUID = 381735603520864103L;
	private int accountNumber;
	private Date closedDealDate;
	private double capacity;
	private String currencyPair;
	private double generalProfit;
	private double adminCapital;

	public double getAdminCapital() {
		return adminCapital;
	}

	public void setAdminCapital(double adminCapital) {
		this.adminCapital = adminCapital;
	}

	public double getGeneralProfit() {
		return generalProfit;
	}

	public void setGeneralProfit(double generalProfit) {
		this.generalProfit = generalProfit;
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

	public Date getClosedDealDate() {
		return closedDealDate;
	}

	public void setClosedDealDate(Date closedDealDate) {
		this.closedDealDate = closedDealDate;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
}
