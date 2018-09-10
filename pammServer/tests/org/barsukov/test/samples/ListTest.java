package org.barsukov.test.samples;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class ListTest {

	@Test
	public void test() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (int i = 0; i < 1e7; i++) {
			arrayList.add(i);
		}
		System.gc();
		System.out.println("ArrayList: "  + (Runtime.getRuntime().maxMemory()
				- Runtime.getRuntime().freeMemory()));
	}

	@Test
	public void test2() {
		LinkedList<Integer> arrayList = new LinkedList<Integer>();
		for (int i = 0; i < 1e7; i++) {
			arrayList.add(i);
		}
		System.gc();
		System.out.println("LinkedList: "  + (Runtime.getRuntime().maxMemory()
				- Runtime.getRuntime().freeMemory()));

	}
}
