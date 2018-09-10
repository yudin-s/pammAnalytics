package org.parser.fxtrend.beans;

import java.sql.Date;

import org.parser.fxtrend.parser.FxTType;

public class FxtAccountsData {
	private static final String NEXT_ROLLOVER = "nextRollover: ";
	private static final String NUM_OF_OPEN_DEALS = "numOfOpenDeals: ";
	private static final String ID_LAST_OFFER = "idLastOffer: ";
	private static final String MAX_DROPDOWN = "maxDropdown: ";
	private static final String AVGRAGE_DROPDOWN = "avgrageDropdown: ";
	private static final String INVESTITIONS2 = "investitions: ";
	private static final String CURRENT_CAPITAL = "currentCapital: ";
	private static final String START_CAPITAL = "startCapital: ";
	private static final String DATE_REG = "dateReg: ";
	private static final String ACCOUNT_NUMBER = "Account number: ";
	private static final String ID_ADMIN = "ID admin: ";
	private static final String ENDL = "\n";
	private static final String NULL_STATE = "NULL";
	private static final int NOT_SET = -1;
	private static final Date NOT_SET_DATE = new Date(0);

	private FxTType type;
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

	public FxtAccountsData() {
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

	}

	@Override
	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append(ID_ADMIN).append(idAdmin).append(ENDL);
		buffer.append(ACCOUNT_NUMBER).append(accountNumber).append(ENDL);
		buffer.append(DATE_REG).append(dateReg).append(ENDL);
		buffer.append(START_CAPITAL).append(startCapital).append(ENDL);
		buffer.append(CURRENT_CAPITAL).append(currentCapital).append(ENDL);
		buffer.append(INVESTITIONS2).append(investitions).append(ENDL);
		buffer.append(AVGRAGE_DROPDOWN).append(avgrageDropdown).append(ENDL);
		buffer.append(MAX_DROPDOWN).append(maxDropdown).append(ENDL);
		buffer.append(ID_LAST_OFFER).append(idLastOffer).append(ENDL);
		buffer.append(NUM_OF_OPEN_DEALS).append(numOfOpenDeals).append(ENDL);
		buffer.append(NEXT_ROLLOVER).append(nextRollover).append(ENDL);

		return buffer.toString();
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

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
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

	public FxTType getType() {
		return type;
	}

	public void setType(FxTType type) {
		this.type = type;
	}

}