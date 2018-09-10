package org.client.datamanager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.client.beans.FxTGridData;
import org.client.beans.FxTTypes;
import org.shared.api.ver2.dto.ClientDTO;
import org.shared.api.ver2.dto.FxtAccountsMinimalDTO;
import org.shared.rmi.IRMI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DataGetter {
	private static final String DEFAULT_HOST = "rmi://localhost:12345/pammServer";
	private static final int DATA_NOT_SET = 42;
	private static final int MILISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
	private static final int API_VER = 2;
	private static final String RMI_HOST = "rmiHost";
	private static final Logger log = LoggerFactory.getLogger(DataGetter.class);
	private static DataGetter dataGetter = null;
	private ClientDTO client;
	private IRMI server;

	private DataGetter() {
		createServerConnect();
		client = new ClientDTO();
		client.setApiVer(API_VER);
	}

	public static DataGetter getInstance() {
		if (dataGetter == null) {
			log.info("ME");
			dataGetter = new DataGetter();
		}
		return dataGetter;
	}

	private void createServerConnect() {
		String host = getHost();
		try {
			server = (IRMI) Naming.lookup(host);
		} catch (MalformedURLException e) {
			log.error("InvalidURL (Host : {})\nMessage: {}", host,
					e.getMessage());
		} catch (RemoteException e) {
			log.error("RemoteException. Message : {} ", e.getMessage());
		} catch (NotBoundException e) {
			log.error("Not bounded error. M:{}", e.getMessage());
		}
	}

	private String getHost() {
		return PropertyManager.getProperty(RMI_HOST, DEFAULT_HOST);
	}

	public List<FxTGridData> getDataForTable() {
		List<FxTGridData> result = new ArrayList<FxTGridData>();
		if (server != null) {
			try {
				List<FxtAccountsMinimalDTO> list = (List<FxtAccountsMinimalDTO>) server
						.getFxTAccounts(client);
				for (FxtAccountsMinimalDTO rmiData : list) {
					FxTGridData data = new FxTGridData();
					data.setAccountName(rmiData.getIdAdmin());
					data.setAccountNumber(rmiData.getAccountNumber());
					data.setAge((int) calculateAge(rmiData));
					data.setAgentsByCalling(rmiData.getAgentsFromCalling());
					data.setAgentsByProfit(rmiData.getAgentsFromProfit());
					data.setAvgrageDropdown(rmiData.getAvgrageDropdown());
					data.setCICA(calculateCICA(rmiData));
					data.setConnectionMethod(rmiData.getConnecitonMethod());
					data.setCurrentCapital(rmiData.getCurrentCapital());
					data.setLevelOfResponsibility(rmiData.getResponsibility());
					data.setMaxDropdown(rmiData.getMaxDropdown());
					data.setMaxInvestitions(-42);
					data.setMinimalLeftAndAdd(rmiData.getMinSummAndLeft());
					data.setMinimalSummOfAdditional(-DATA_NOT_SET);
					data.setNumOfOpenDeals(rmiData.getNumOfOpenDeals());
					data.setProfit(-DATA_NOT_SET);
					data.setProfitPerMounth(-DATA_NOT_SET);
					data.setProfitPerWeek(-DATA_NOT_SET);
					data.setReferral(rmiData.getCommission());
					data.setSellPeriod(rmiData.getPeriod());
					data.setStartCapital(rmiData.getStartCapital());
					data.setStrategy(rmiData.getStrategy());
					data.setSummInControl(rmiData.getInvestitions());
					data.setType(calcType(rmiData));
					result.add(data);
				}
			} catch (RemoteException e) {
				log.error("Remote excepiion! Host: " + getHost(), e);
			}
		}
		return result;
	}

	private FxTTypes calcType(FxtAccountsMinimalDTO rmiData) {
		int type = rmiData.getType();
		if (type == 1) {
			return FxTTypes.PAMM;
		}
		if (type == 2) {
			return FxTTypes.PAMM2;
		}
		throw new NotImplementedException();
	}

	private double calculateCICA(FxtAccountsMinimalDTO rmiData) {
		return rmiData.getInvestitions() / rmiData.getCurrentCapital();
	}

	private long calculateAge(FxtAccountsMinimalDTO rmiData) {
		return rmiData.getDateReg().getTime() / MILISECONDS_IN_DAY;
	}

}
