package org.shared.api.ver1;

import java.io.Serializable;
import java.util.ArrayDeque;

public class Api1Object implements Serializable{
	private static final long serialVersionUID = 2341494571583993546L;
	private ArrayDeque<MainDataDTO> mainData;
	private ArrayDeque<AdvancedDataDTO> advancedData;
	private ArrayDeque<OfertsDTO> OffersData;
	private ArrayDeque<ProfitPerPeriodDTO> mounthProfitData;
	private ArrayDeque<ProfitPerPeriodDTO> weekProfitData;

	public ArrayDeque<MainDataDTO> getMainData() {
		return mainData;
	}

	public void setMainData(ArrayDeque<MainDataDTO> mainData) {
		this.mainData = mainData;
	}

	public ArrayDeque<AdvancedDataDTO> getAdvancedData() {
		return advancedData;
	}

	public void setAdvancedData(ArrayDeque<AdvancedDataDTO> advancedData) {
		this.advancedData = advancedData;
	}

	public ArrayDeque<OfertsDTO> getOffersData() {
		return OffersData;
	}

	public void setOffersData(ArrayDeque<OfertsDTO> OffersData) {
		this.OffersData = OffersData;
	}

	public ArrayDeque<ProfitPerPeriodDTO> getMounthProfitData() {
		return mounthProfitData;
	}

	public void setMounthProfitData(ArrayDeque<ProfitPerPeriodDTO> mounthProfitData) {
		this.mounthProfitData = mounthProfitData;
	}

	public ArrayDeque<ProfitPerPeriodDTO> getWeekProfitData() {
		return weekProfitData;
	}

	public void setWeekProfitData(ArrayDeque<ProfitPerPeriodDTO> weekProfitData) {
		this.weekProfitData = weekProfitData;
	}
	
}
