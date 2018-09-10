package org.barsukov.test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;
import org.parser.fxtrend.beans.FxtAccountsData;
import org.parser.fxtrend.beans.FxtDealHistoryData;
import org.parser.fxtrend.beans.FxtOfferData;
import org.parser.fxtrend.beans.FxtProfitData;
import org.server.data.DataManager;

public class DataManagerTest {

	private static final String NONE = "NONE";

	@Test
	public void testSetMainData() {
		DataManager manager = DataManager.getInstance();
		FxtAccountsData data = new FxtAccountsData();
		data.setAccountNumber(10);
		data.setAvgrageDropdown(0);
		data.setCurrentCapital(0);
		data.setDateReg(nowDate());
		data.setIdAdmin(NONE);
		data.setIdLastOffer(0);
		data.setInvestitions(0);
		data.setMaxDropdown(0);
		data.setAvgrageDropdown(0);
		data.setNextRollover(nowDate());
		data.setNumOfOpenDeals(0);
		data.setStartCapital(0);
		data.setState("NULL");
		manager.setFxtAccountsData(1, data);
	}

	@Test
	public void testFxtAddOffer() {
		DataManager dataManager = DataManager.getInstance();
		FxtOfferData data = new FxtOfferData();
		data.setAccountNumber(0);
		data.setAdminReward(0);
		data.setAgentsFromCalling(0);
		data.setCommission(0);
		data.setConnecitonMethod(NONE);
		data.setLevelReport(NONE);
		data.setMinSummAndLeft(0);
		data.setOfferID(0);
		data.setPeriod(0);
		data.setResponsibility(0);
		data.setStopoutByCA(0);
		data.setStopoutByCA(0);
		data.setStrategy(NONE);
		data.setType(0);
		dataManager.setFxtAddOffer(0, data);
	}

	@Test
	public void testFxtProfitByWeek() {
		DataManager manager = DataManager.getInstance();
		FxtProfitData array[] = new FxtProfitData[10];
		for (int i = 0; i < 10; i++) {
			FxtProfitData data = new FxtProfitData();
			data.setProfitOfInvestorInPercent(0.100);
			data.setProfitOnDollar(0.1);
			data.setProfitOnPercent(0.1);
			data.setDate(nowDate());
			array[i] = data;
		}
		manager.setFxtProfitByWeek(0, array, 1);
	}

	@Test
	public void testFxtProfitByMounth() {
		DataManager manager = DataManager.getInstance();
		FxtProfitData array[] = new FxtProfitData[10];
		for (int i = 0; i < 10; i++) {
			FxtProfitData data = new FxtProfitData();
			data.setProfitOfInvestorInPercent(0.100);
			data.setProfitOnDollar(0.1);
			data.setProfitOnPercent(0.1);
			data.setDate(nowDate());
			array[i] = data;
		}
		manager.setFxtProfitByMounth(0, 1, array);
	}

	public Date nowDate() {
		return new Date(System.currentTimeMillis());
	}

	@Test
	public void testFxtDealHistory() {
		DataManager manager = DataManager.getInstance();
		FxtDealHistoryData array[] = new FxtDealHistoryData[10];
		for (int i = 0; i < 10; i++) {
			array[i] = new FxtDealHistoryData();
			array[i].setAdminCapital(0);
			array[i].setCapacity(0);
			array[i].setCurrencyPair("OLOLO");
			array[i].setDateOfClose(nowDate());
			array[i].setGeneralProfit(0);
		}
		manager.setFxtDealHistory(0, array, 0);
	}

	@Test
	public void testGetDate() {
		DataManager manager = DataManager.getInstance();
		manager.getFxtLastDateByDeals(0, 0);
		manager.getFxtLastDateByMounthProfit(0, 0);
		manager.getFxtLastDateByWeekProfit(0, 0);
	}

}
