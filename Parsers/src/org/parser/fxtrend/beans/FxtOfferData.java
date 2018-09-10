package org.parser.fxtrend.beans;

public class FxtOfferData {

	private static final String NULL_STATE = "NULL";
	private static final int NOT_SET = -1;

	private int accountNumber;
	private int offerID;
	private int period;
	private double adminReward;
	private double commission;
	private int type;
	private double responsibility;
	private double minSummAndLeft;
	private double stopoutByCA;
	private double agentsFromCalling;
	private double agentsFromReward;
	private String levelReport;
	private String connecitonMethod;
	private String strategy;

	public FxtOfferData() {
		accountNumber = NOT_SET;
		offerID = NOT_SET;
		period = NOT_SET;
		adminReward = NOT_SET;
		commission = NOT_SET;
		type = NOT_SET;
		responsibility = NOT_SET;
		minSummAndLeft = NOT_SET;
		stopoutByCA = NOT_SET;
		agentsFromCalling = NOT_SET;
		agentsFromReward = NOT_SET;
		levelReport = NULL_STATE;
		connecitonMethod = NULL_STATE;
		strategy = NULL_STATE;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountID) {
		this.accountNumber = accountID;
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

	public double getMinSummAndLeft() {
		return minSummAndLeft;
	}

	public void setMinSummAndLeft(double minSummAndLeft) {
		this.minSummAndLeft = minSummAndLeft;
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
		return agentsFromReward;
	}

	public void setAgentsFromReward(double agentsFromReward) {
		this.agentsFromReward = agentsFromReward;
	}

	public String getLevelReport() {
		return levelReport;
	}

	public void setLevelReport(String levelReport) {
		this.levelReport = levelReport;
	}

	public String getConnecitonMethod() {
		return connecitonMethod;
	}

	public void setConnecitonMethod(String connecitonMethod) {
		this.connecitonMethod = connecitonMethod;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public int getOfferID() {
		return offerID;
	}

	public void setOfferID(int OfferID) {
		this.offerID = OfferID;
	}

}