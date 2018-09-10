package org.shared.api.ver2.dto;

import java.io.Serializable;
import java.sql.Date;

public class FxtAccountsMinimalDTO implements Serializable {
	private static final long serialVersionUID = 8112482491306222568L;
	private static final String NULL_STATE = "NULL";
	private static final int NOT_SET = -1;
	private static final Date NOT_SET_DATE = new Date(0);

	private String idAdmin;
	private int accountNumber;
	private Date dateReg;
	private String state;
	private double startCapital;
	private double currentCapital;
	private double investitions;
	private double avgrageDropdown;
	private double maxDropdown;
	private int idLastOffer;
	private int numOfOpenDeals;
	private Date nextRollover;
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

	public FxtAccountsMinimalDTO() {
		idAdmin = "";
		accountNumber = NOT_SET;
		dateReg = NOT_SET_DATE;
		state = NULL_STATE;
		startCapital = NOT_SET;
		currentCapital = NOT_SET;
		investitions = NOT_SET;
		avgrageDropdown = NOT_SET;
		maxDropdown = NOT_SET;
		idLastOffer = NOT_SET;
		numOfOpenDeals = NOT_SET;
		nextRollover = NOT_SET_DATE;
		accountNumber = NOT_SET;
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

	public Date getNextRollover() {
		return nextRollover;
	}

	public void setNextRollover(Date nextRollover) {
		this.nextRollover = nextRollover;
	}

	public int getNumOfOpenDeals() {
		return numOfOpenDeals;
	}

	public void setNumOfOpenDeals(int numOfOpenDeals) {
		this.numOfOpenDeals = numOfOpenDeals;
	}

	public double getMaxDropdown() {
		return maxDropdown;
	}

	public void setMaxDropdown(double maxDropdown) {
		this.maxDropdown = maxDropdown;
	}

	public double getAvgrageDropdown() {
		return avgrageDropdown;
	}

	public void setAvgrageDropdown(double avgrageDropdown) {
		this.avgrageDropdown = avgrageDropdown;
	}

	public double getInvestitions() {
		return investitions;
	}

	public void setInvestitions(double investitions) {
		this.investitions = investitions;
	}

	public double getCurrentCapital() {
		return currentCapital;
	}

	public void setCurrentCapital(double currentCapital) {
		this.currentCapital = currentCapital;
	}

	public double getStartCapital() {
		return startCapital;
	}

	public void setStartCapital(double startCapital) {
		this.startCapital = startCapital;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getDateReg() {
		return dateReg;
	}

	public void setDateReg(Date dateReg) {
		this.dateReg = dateReg;
	}

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}

	public int getIdLastOffer() {
		return idLastOffer;
	}

	public void setIdLastOffer(int idLastOffer) {
		this.idLastOffer = idLastOffer;
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

}
