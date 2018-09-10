package org.parser.fxtrend.beans;

public class FxTDataBeen {
	private FxtAccountsData mainData = null;
	private FxtOfferData offerData = null;

	private FxtProfitData[] weekProfitData = null;
	private FxtProfitData[] mounthProfitData = null;
	private FxtDealHistoryData[] dealsProfitData = null;

	public FxtAccountsData getMainData() {
		return mainData;
	}

	public void setMainData(FxtAccountsData mainData) {
		this.mainData = mainData;
	}

	public FxtOfferData getOfferData() {
		return offerData;
	}

	public void setOfferData(FxtOfferData OfferData) {
		this.offerData = OfferData;
	}

	public FxtProfitData[] getWeekProfitData() {
		return weekProfitData;
	}

	public void setWeekProfitData(FxtProfitData[] weekProfitData) {
		this.weekProfitData = weekProfitData;
	}

	public FxtProfitData[] getMonthProfitData() {
		return mounthProfitData;
	}

	public void setMounthProfitData(FxtProfitData[] mounthProfitData) {
		this.mounthProfitData = mounthProfitData;
	}

	public FxtDealHistoryData[] getDealsProfitData() {
		return dealsProfitData;
	}

	public void setDealsProfitData(FxtDealHistoryData[] dealsProfitData) {
		this.dealsProfitData = dealsProfitData;
	}

}