package org.shared.api.ver1;

import java.io.Serializable;

public class AdvancedDataDTO implements Serializable{
	private static final long serialVersionUID = 6150381404946250872L;
	private double capital;
	private int ID;
	private double active;
	private String level;
	private double maxdrop;
	private double avgDrop;
	private String rollover;
	private double cover;
	private double yearprofit;
	private double start;
	private long timestamp;

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getActive() {
		return active;
	}

	public void setActive(double active) {
		this.active = active;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getMaxdrop() {
		return maxdrop;
	}

	public void setMaxdrop(double maxdrop) {
		this.maxdrop = maxdrop;
	}

	public double getAvgDrop() {
		return avgDrop;
	}

	public void setAvgDrop(double avgDrop) {
		this.avgDrop = avgDrop;
	}

	public String getRollover() {
		return rollover;
	}

	public void setRollover(String rollover) {
		this.rollover = rollover;
	}

	public double getCover() {
		return cover;
	}

	public void setCover(double cover) {
		this.cover = cover;
	}

	public double getYearprofit() {
		return yearprofit;
	}

	public void setYearprofit(double yearprofit) {
		this.yearprofit = yearprofit;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
