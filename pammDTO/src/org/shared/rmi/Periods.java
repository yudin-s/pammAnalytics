package org.shared.rmi;
public enum Periods {
		WEEK(0),
		ONLINE(1),
		DIAILY(2);
		public final int toInt;
		Periods(int val){
		  toInt = val;
		}
	}