package org.shared.api.ver1;

import java.io.Serializable;

public class ProfitPerPeriodDTO implements Serializable{
	private static final long serialVersionUID = -3324381734509168305L;
	private double profit;
	private double percent;
	private String period;
	private int ID;
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
