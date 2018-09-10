package org.parser.parser;

import java.sql.Date;

public class TimePeriod {
	public TimePeriod(){};
	public TimePeriod(Date start, Date end){
		setStart(start);
		setEnd(end);
	}
	private Date start;
	private Date end;
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
}