package org.parser.fxtrend.beans;

import java.sql.Date;

public class FxtProfitData {
	private Date startWeek;
	private double profitOnDollar;
	private double profitOnPercent;
	private double profitOfInvestorInPercent;

	private static final int NOT_SET = -1;
	private static final Date NOT_SET_DATE = new Date(0);

	public FxtProfitData() {
		startWeek = NOT_SET_DATE;
		profitOfInvestorInPercent = NOT_SET;
		profitOnDollar = NOT_SET;
		profitOnPercent = NOT_SET;
	}

	public double getProfitOfInvestorInPercent() {
		return profitOfInvestorInPercent;
	}

	public void setProfitOfInvestorInPercent(double profitOfInvestorInPercent) {
		this.profitOfInvestorInPercent = profitOfInvestorInPercent;
	}

	public double getProfitOnPercent() {
		return profitOnPercent;
	}

	public void setProfitOnPercent(double profitOnPercent) {
		this.profitOnPercent = profitOnPercent;
	}

	public double getProfitOnDollar() {
		return profitOnDollar;
	}

	public void setProfitOnDollar(double profitOnDollar) {
		this.profitOnDollar = profitOnDollar;
	}

	public Date getDate() {
		return startWeek;
	}

	public void setDate(Date startWeek) {
		this.startWeek = startWeek;
	}
}