package org.client.views.pammlist.detail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class DropdownAndPeriod extends Composite{
	private static final String PERCENT = "%";
	private static final int NORMALIZE_PERCENTS = 100;
	private static final String DEFAULT_PERIOD = "1";
	private static final String RELATIVE_DROPDOWN = "Средняя просадка";
	private static final String DEFAULT_VALUE = "50%";
	private static final String MAXIMAL_DROPDOWN = "Максимальная просадка";
	private static final String SELL_PERIOD = "Торговый период (недель)";
	Label relativeDropdownText;
	Label relativeDropdownValue;
	Label maximalText;
	Label maximalValue;	
	Label sellPeriodText;
	Label sellPeriodValue;

	public DropdownAndPeriod(Composite parent, int style) {
		super(parent, style);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		setLayout(layout);
		relativeDropdownText = createLabel(RELATIVE_DROPDOWN);
		relativeDropdownValue = createLabel(DEFAULT_VALUE);
		maximalText = createLabel(MAXIMAL_DROPDOWN);
		maximalValue = createLabel(DEFAULT_VALUE);
		sellPeriodText = createLabel(SELL_PERIOD);
		sellPeriodValue = createLabel(DEFAULT_PERIOD);
		pack();
	}
	public void setRelativeDropdown(double percents) {
		relativeDropdownValue.setText(new StringBuffer(Double.toString(percents
				* NORMALIZE_PERCENTS)).append(PERCENT).toString());
	    relativeDropdownValue.pack();
	}
	public void setMaximalDropdown(double percents) {
		maximalValue.setText(new StringBuffer(Double.toString(percents
				* NORMALIZE_PERCENTS)).append(PERCENT).toString());
	    maximalValue.pack();
	}
	public void setSellPeriod(int period) {
		sellPeriodValue.setText(Integer.toString(period));
	    sellPeriodValue.pack();
	}
	private Label createLabel(String text) {
		Label result = new Label(this, SWT.NONE);
		if (text != null)
			result.setText(text);
		return result;

	}
}
