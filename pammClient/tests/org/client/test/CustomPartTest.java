package org.client.test;

import static org.junit.Assert.*;

import org.client.core.CustomInitCode;
import org.junit.Test;

public class CustomPartTest {

	@Test
	public void testInitCustomPart() {
		CustomInitCode.init();
	}

}
