package org.barsukov.test.samples;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitMaskParseTestBed {

	@Test
	public void test() {
		assertTrue(maskCheck(16, 16));
	}
	
	private boolean maskCheck(int val, int maskVal) {
		return ((val | maskVal) == val);
	}

}
