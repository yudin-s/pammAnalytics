package org.client.views.pammlist.detail;

import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class DateAndProfit extends Composite {
	private static final String REFERRAL = "Коммисия:";
	private static final String DEFAULT_PERCENT = "50%";
	private static final String PROFIT = "Вознаграждение:";
	private static final String DEFAULT_DAY_COUNT = "(1000 дней)";
	private static final String DEFAULT_DAY = "30.07.2012 ";
	private static final String PERCENT = "%";
	private static final int NORMALIZE_PERCENTS = 100;
	private static final String ANTISCOBCA = " (";
	private static final String SCOBCA = ")";
	private static final String POINT = ".";
	private static final int HOUR_IN_DAY = 24;
	private static final int MINUTES_IN_HOUR = 60;
	private static final int SECONDS_IN_MINUTES = 60;
	private static final int MILISECONDS_IN_SECONDS = 1000;
	private static final int MILISECONDS_IN_DAY = MILISECONDS_IN_SECONDS
			* SECONDS_IN_MINUTES * MINUTES_IN_HOUR * HOUR_IN_DAY;
	private static final String DATE_OF_CREATE = "Дата создания: ";
	private static final String DEFAULT_DATE_MESSAGE = DATE_OF_CREATE + DEFAULT_DAY + DEFAULT_DAY_COUNT;
	Label date;
	Label profitText;
	Label referralText;
	Label profitPercent;
	Label referralPercent;

	public DateAndProfit(Composite parent, int style) {
		super(parent, style);
		GridLayout grid = new GridLayout();
		grid.numColumns = 2;
		setLayout(grid);

		date = createLabel(DEFAULT_DATE_MESSAGE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		date.setLayoutData(gridData);

		profitText = createLabel(PROFIT);

		profitPercent = createLabel(DEFAULT_PERCENT);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		profitPercent.setLayoutData(gridData);

		referralText = createLabel(REFERRAL);
		referralPercent = createLabel(DEFAULT_PERCENT);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		referralPercent.setLayoutData(gridData);
	}
	

	public void setDate(short day, short mounth, int year) {
		date.setText(new StringBuffer(DATE_OF_CREATE)
				.append(day)
				.append(POINT)
				.append(mounth)
				.append(POINT)
				.append(year)
				.append(ANTISCOBCA)
				.append(calcDaysBeforeCurrentDate(new GregorianCalendar(year,
						mounth-1, day).getTime())).append(SCOBCA).toString());
		date.pack();
	}

	public void setProfit(double percents) {
		profitPercent.setText(new StringBuffer(Double.toString(percents
				* NORMALIZE_PERCENTS)).append(PERCENT).toString());
		profitPercent.pack();
	}

	public void setReferral(double percents) {
		referralPercent.setText(new StringBuffer(Double.toString(percents
				* NORMALIZE_PERCENTS)).append(PERCENT).toString());
		referralPercent.pack();
	}
	
	private Label createLabel(String text) {
		Label result = new Label(this, SWT.NONE);
		if (text != null)
			result.setText(text);
		return result;

	}

	private int calcDaysBeforeCurrentDate(Date date1) {
		Date date2 = new Date(System.currentTimeMillis());
		return (int) ((date2.getTime() - date1.getTime()) / MILISECONDS_IN_DAY);
	}

}
