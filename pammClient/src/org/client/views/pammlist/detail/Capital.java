package org.client.views.pammlist.detail;

import org.client.views.pammlist.detail.PieChart.ValueCanNotBeNegative;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Capital extends Composite {

	private static final String DOUBLE_POINT = ": ";
	private static final String INVESTOR_INVESTITIONS = "КИ";
	private static final String CAPITAL_NOW = "Текущий КУ";
	private static final String START_CAPITAL = "Начальный КУ";
	
	private double capitalOfInvestor = 0;
	@SuppressWarnings("unused")
	private double capitalInStart = 0;
	private double capitalNow;

	Label capitalNowLabel;
	Label capitalStartLabel;
	Label investorCapitalLabel;
	PieChart pieChart;

	public Capital(Composite parent, int style) {
		super(parent, style);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		setLayout(layout);
		
		investorCapitalLabel = new Label(this,SWT.NONE);
		investorCapitalLabel.setLayoutData(new GridData());
		GridData pieChartGD = new GridData();
		pieChartGD.verticalSpan = 3;
		pieChartGD.horizontalSpan = 2;
		pieChartGD.horizontalAlignment = SWT.END;
		pieChart = new PieChart(this, SWT.NONE);
		pieChart.setLayoutData(pieChartGD);
		capitalNowLabel = new Label(this,SWT.NONE);
		capitalNowLabel.setLayoutData(new GridData());
		capitalStartLabel = new Label(this,SWT.NONE);
		capitalStartLabel.setLayoutData(new GridData());
		
	}

	public void setNewValues(double capitalOfInvestor, double capitalInStart,
			double capitalNow) {
		setCapitalInStart(capitalInStart);
		setCapitalNow(capitalNow);
		setCapitalOfInvestor(capitalOfInvestor);

	}

	public void setCapitalOfInvestor(double capitalOfInvestor) {
		this.capitalOfInvestor = capitalOfInvestor;
		updateLabelCapitalInvestor(capitalOfInvestor);
	}

	public void setCapitalNow(double capitalNow) {
		this.capitalNow = capitalNow;
		updateLabelCapitalNow(capitalNow);
	}

	public void setCapitalInStart(double capitalInStart) {
		this.capitalInStart = capitalInStart;
		updateLabelCapitalStart(capitalInStart);
	}
	public void refreshPie(){
		updateChart();
	}
	private void updateChart() {
		try {
			pieChart.drawByData(capitalNow,capitalOfInvestor);
			pieChart.pack();
		} catch (ValueCanNotBeNegative e) {
			e.printStackTrace();
		}
	}

		
	private void updateLabelCapitalNow(double newData) {
		capitalNowLabel.setText(CAPITAL_NOW + DOUBLE_POINT + newData);
	}

	private void updateLabelCapitalStart(double newData) {
		capitalStartLabel.setText(START_CAPITAL + DOUBLE_POINT + newData);
	}

	private void updateLabelCapitalInvestor(double newData) {
		investorCapitalLabel.setText(INVESTOR_INVESTITIONS + DOUBLE_POINT
				+ newData);
	}

}
