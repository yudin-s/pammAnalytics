package org.client.views.pammlist;

import java.util.Properties;

import org.client.views.pammlist.detail.*;
import org.client.wigets.BarChartWiget;
import org.client.wigets.RadioButtonGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class Detail extends Composite {

	private Capital capitalBlock;
	private DateAndProfit dateAndProfit;
	@SuppressWarnings("unused")
	private OffersAndAgents offersAndAgents;
	@SuppressWarnings("unused")
	private ReportAndStrategy levelAndStrategy;
	@SuppressWarnings("unused")
	private DropdownAndPeriod dropdownAndPeriod;
	@SuppressWarnings("unused")
	private Summs summs;
	private TabFolder folder;
	private BarChartWiget profitDiagramm;
	private BarChartWiget capitalDiagramm;

	FormToolkit toolkit;

	public Detail(Composite parent, int style) {
		super(parent, style);
		initComponent();

	}

	private void initComponent() {
		toolkit = new FormToolkit(this.getDisplay());
		this.setLayout(new GridLayout());
		folder = new TabFolder(this, SWT.NONE);
		toolkit.adapt(folder);
		summaryChart(folder);
		profitChart(folder);
		capitalChart(folder);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		folder.setLayoutData(gridData);

	}

	public void setNewAdministrator(int id) {
		// TODO Implement get data from database
		double capitalInStart = 30;
		double capitalNow = 50000;
		double capitalOfInvestition = 50;
		capitalBlock.setNewValues(capitalOfInvestition, capitalInStart,
				capitalNow);
		capitalBlock.refreshPie();
		dateAndProfit.setDate((short) 1, (short) 1, 1970);
		dateAndProfit.setProfit(0.4);
		dateAndProfit.setReferral(0.1);
		Properties data = new Properties();
		data.setProperty("April", Double.toString(0.1));
		data.setProperty("March", Double.toString(1.1));
		data.setProperty("June", Double.toString(2.1));
		data.setProperty("July", Double.toString(3.1));
		profitDiagramm.setData(data);

	}

	public TabFolder getTabFolder() {
		return folder;
	}

	private void profitChart(TabFolder tabFolder) {
		TabItem profitChart = new TabItem(tabFolder, SWT.NONE);
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout());
		RadioButtonGroup buttonGroup = new RadioButtonGroup(composite,
				SWT.NONE, SWT.RADIO);
		buttonGroup.setButtons(new String[] { "По месяцам", "По неделям",
				"По сделкам" });
		buttonGroup.setSelected(0);

		profitDiagramm = new BarChartWiget(composite, SWT.BORDER);
		profitDiagramm.setSize(300, 200);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		buttonGroup.setLayoutData(gridData);

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		profitDiagramm.setLayoutData(gridData);

		Button copy = new Button(composite, SWT.NONE);
		copy.setText("Скопировать в буфер обмена");

		gridData = new GridData();
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		copy.setLayoutData(gridData);

		profitChart.setText("График доходности");
		profitChart.setControl(composite);

	}

	private void capitalChart(TabFolder tabFolder) {
		TabItem profitChart = new TabItem(tabFolder, SWT.NONE);
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout());
		RadioButtonGroup buttonGroup = new RadioButtonGroup(composite,
				SWT.NONE, SWT.RADIO);
		buttonGroup.setButtons(new String[] { "По месяцам", "По неделям",
				"По сделкам" });
		buttonGroup.setSelected(0);

		capitalDiagramm = new BarChartWiget(composite, SWT.BORDER);
		capitalDiagramm.setSize(300, 200);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		buttonGroup.setLayoutData(gridData);

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		capitalDiagramm.setLayoutData(gridData);

		Button copy = new Button(composite, SWT.NONE);
		copy.setText("Скопировать в буфер обмена");

		gridData = new GridData();
		gridData.verticalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		copy.setLayoutData(gridData);

		profitChart.setText("Капитал управляющего");
		profitChart.setControl(composite);

	}

	private void summaryChart(TabFolder tabFolder) {
		TabItem summary = new TabItem(tabFolder, SWT.WRAP);
		summary.setText("Общая информация");
		Composite composite = new Composite(tabFolder, SWT.WRAP);
		RowLayout layout = new RowLayout();
		layout.wrap = true;
		composite.setLayout(layout);
		capitalBlock = new Capital(composite, SWT.NONE);
		dateAndProfit = new DateAndProfit(composite, SWT.NONE);
		offersAndAgents = new OffersAndAgents(composite, SWT.NONE);
		levelAndStrategy = new ReportAndStrategy(composite, SWT.NONE);
		dropdownAndPeriod = new DropdownAndPeriod(composite, SWT.NONE);
		summs = new Summs(composite, SWT.NONE);
		capitalBlock.pack();
		summary.setControl(composite);

	}

}
