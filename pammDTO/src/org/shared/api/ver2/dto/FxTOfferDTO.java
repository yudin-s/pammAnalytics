package org.shared.api.ver2.dto;

import java.io.Serializable;

public class FxTOfferDTO implements Serializable {
	private static final long serialVersionUID = 5354641536661500622L;
	private int accoutNumber;
	private int offerId;
	private int period;
	private double adminReward;
	private double commission;
	private int type;
	private double responsibility;
	private double minSummAddAndLeft;
	private double stopoutByCA;
	private double agentsFromCalling;
	private double agentsFromProfit;
	private String reportLevel;
	private String connectionMethod;

	public int getAccoutNumber() {
		return accoutNumber;
	}

	public void setAccoutNumber(int accoutNumber) {
		this.accoutNumber = accoutNumber;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int OfferId) {
		this.offerId = OfferId;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getAdminReward() {
		return adminReward;
	}

	public void setAdminReward(double adminReward) {
		this.adminReward = adminReward;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(double responsibility) {
		this.responsibility = responsibility;
	}

	public double getMinSummAddAndLeft() {
		return minSummAddAndLeft;
	}

	public void setMinSummAddAndLeft(double minSummAddAndLeft) {
		this.minSummAddAndLeft = minSummAddAndLeft;
	}

	public double getStopoutByCA() {
		return stopoutByCA;
	}

	public void setStopoutByCA(double stopoutByCA) {
		this.stopoutByCA = stopoutByCA;
	}

	public double getAgentsFromCalling() {
		return agentsFromCalling;
	}

	public void setAgentsFromCalling(double agentsFromCalling) {
		this.agentsFromCalling = agentsFromCalling;
	}

	public double getAgentsFromProfit() {
		return agentsFromProfit;
	}

	public void setAgentsFromProfit(double agentsFromProfit) {
		this.agentsFromProfit = agentsFromProfit;
	}

	public String getReportLevel() {
		return reportLevel;
	}

	public void setReportLevel(String reportLevel) {
		this.reportLevel = reportLevel;
	}

	public String getConnectionMethod() {
		return connectionMethod;
	}

	public void setConnectionMethod(String connectionMethod) {
		this.connectionMethod = connectionMethod;
	}
}
