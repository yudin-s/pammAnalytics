package org.client.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import org.client.core.CustomInitCode;
import org.junit.Before;
import org.junit.Test;
import org.shared.api.ver2.dto.ClientDTO;
import org.shared.rmi.IRMI;

public class RMITest {
	private static final String RMI_HOST = "rmi://localhost:12345/pammServer";
	IRMI server;
	ClientDTO dto;

	@Before
	public void setUp() {
		CustomInitCode.init();
		try {
			server = (IRMI) Naming.lookup(RMI_HOST);
			dto = new ClientDTO();
			dto.setApiVer(2);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void dealsGet() {
		try {
			int accountNumber = 10457;
			List<?> deals = server.getFxTNewDealsProfit(new Date(0),
					accountNumber, dto);
			assertTrue(deals.size() > 0);
		} catch (RemoteException e) {
			fail("Deals by id not get");
			e.printStackTrace();
		}
	}

	@Test
	public void accounts() {
		List<?> accounts;
		try {
			accounts = server.getFxTAccounts(dto);
			assertTrue(accounts.size() != 0);
		} catch (RemoteException e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void OfferById() {
		int id = 3458;
		try {
			assertNotNull(server.getFxTOfferById(id, dto));
		} catch (RemoteException e) {
			fail("Offer with id: " + id);
			e.printStackTrace();
		}
	}

	@Test
	public void Offers() {
		List<?> Offers;
		try {
			Offers = server.getFxTOffers(dto);
			assertTrue(Offers.size() != 0);
		} catch (RemoteException e) {
			fail("Offers not getted");
			e.printStackTrace();
		}
	}

	@Test
	public void profitPerWeekByID() {
		try {
			int accountNumber = 511280;
			List<?> profit = server.getFxtNewWeekProfit(new Date(0),
					accountNumber,
					dto);
			assertTrue(profit.size() > 0);
		} catch (RemoteException e) {
			fail("Profit per week not getted");
			e.printStackTrace();
		}
	}
	@Test
	public void profitPerMounth(){
		try {
			int accountNumber = 10459;
			List<?> profit = server.getFxTNewMounthProfit(new Date(0), accountNumber, dto);
			assertTrue(profit.size() > 0);
		} catch (RemoteException e) {
			fail("Profit per mounth not get");
			e.printStackTrace();
		}
	}
	
}
