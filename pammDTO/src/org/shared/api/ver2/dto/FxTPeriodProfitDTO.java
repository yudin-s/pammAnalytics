package org.shared.api.ver2.dto;

import java.io.Serializable;
import java.sql.Date;

public class FxTPeriodProfitDTO implements Serializable {

	private static final long serialVersionUID = 7413261158196555472L;

	private int accountNumber;
	private Date date;
	private double profitOnDollar;
	private double profitOnPercent;
	private double profitOfInvestorOnPercent;

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getProfitOnDollar() {
		return profitOnDollar;
	}

	public void setProfitOnDollar(double profitOnDollar) {
		this.profitOnDollar = profitOnDollar;
	}

	public double getProfitOnPercent() {
		return profitOnPercent;
	}

	public void setProfitOnPercent(double profitOnPercent) {
		this.profitOnPercent = profitOnPercent;
	}

	public double getProfitOfInvestorOnPercent() {
		return profitOfInvestorOnPercent;
	}

	public void setProfitOfInvestorOnPercent(double profitOfInvestorOnPercent) {
		this.profitOfInvestorOnPercent = profitOfInvestorOnPercent;
	}
}
