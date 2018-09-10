package org.shared.api.ver1;

import java.io.Serializable;

public class OfertsDTO implements Serializable{
	private static final long serialVersionUID = 4562689224389163482L;
	private int ID;
	private int OID;
	private String period;
	private double profit;
	private double referral;
	private double onus;
	private double minsumm;
	private double stopout;
	private double agentsByCalling;
	private double agentsByReward;
	private short report;
	private String sconnection;
	private String strategy;
	private double maxinvest;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getReferral() {
		return referral;
	}

	public void setReferral(double referral) {
		this.referral = referral;
	}

	public double getOnus() {
		return onus;
	}

	public void setOnus(double onus) {
		this.onus = onus;
	}

	public double getMinsumm() {
		return minsumm;
	}

	public void setMinsumm(double minsumm) {
		this.minsumm = minsumm;
	}

	public double getStopout() {
		return stopout;
	}

	public void setStopout(double stopout) {
		this.stopout = stopout;
	}

	public double getAgentsByCalling() {
		return agentsByCalling;
	}

	public void setAgentsByCalling(double agentsByCalling) {
		this.agentsByCalling = agentsByCalling;
	}

	public double getAgentsByReward() {
		return agentsByReward;
	}

	public void setAgentsByReward(double agentsByReward) {
		this.agentsByReward = agentsByReward;
	}

	public short getReport() {
		return report;
	}

	public void setReport(short report) {
		this.report = report;
	}

	public String getSconnection() {
		return sconnection;
	}

	public void setSconnection(String sconnection) {
		this.sconnection = sconnection;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public double getMaxinvest() {
		return maxinvest;
	}

	public void setMaxinvest(double maxinvest) {
		this.maxinvest = maxinvest;
	}

	public int getOID() {
		return OID;
	}

	public void setOID(int oID) {
		OID = oID;
	}

}
