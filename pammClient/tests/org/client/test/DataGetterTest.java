package org.client.test;

import static org.junit.Assert.*;

import java.util.List;

import org.client.core.CustomInitCode;
import org.client.datamanager.DataGetter;
import org.junit.Test;

public class DataGetterTest {

	@Test
	public void testGetDataForPAMMListTable() {
		CustomInitCode.init();
		DataGetter getter = DataGetter.getInstance();
		List<?> list = getter.getDataForTable();
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}

}
