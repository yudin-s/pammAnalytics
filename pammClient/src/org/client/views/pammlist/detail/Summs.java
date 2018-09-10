package org.client.views.pammlist.detail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class Summs extends Composite {
	private static final String DOLLAR = "$";
	private static final String DEFAULT_PERIOD = "1";
	private static final String MINIMAL_SUMM_AND_LEFT = "Минимальная сумма и остаток";
	private static final String DEFAULT_VALUE = "50$";
	private static final String MINIMAL_ADD = "Минимальная сумма пополнения";
	private static final String MINIMAL_GET = "Минимальная сумма снятия";
	Label minimalSummAndLeftText;
	Label minimalSummAndLeftValue;
	Label maximalAddText;
	Label minimalAddValue;
	Label minimalGetText;
	Label minimalGetValue;

	public Summs(Composite parent, int style) {
		super(parent, style);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		setLayout(layout);
		minimalSummAndLeftText = createLabel(MINIMAL_SUMM_AND_LEFT);
		minimalSummAndLeftValue = createLabel(DEFAULT_VALUE);
		maximalAddText = createLabel(MINIMAL_ADD);
		minimalAddValue = createLabel(DEFAULT_VALUE);
		minimalGetText = createLabel(MINIMAL_GET);
		minimalGetValue = createLabel(DEFAULT_PERIOD);
		pack();
	}

	public void setMinimalSummAndGet(double percents) {
		minimalSummAndLeftValue.setText(new StringBuffer(Double
				.toString(percents)).append(DOLLAR).toString());
		minimalSummAndLeftValue.pack();
	}

	public void setMinimalAdd(double percents) {
		minimalAddValue.setText(new StringBuffer(Double.toString(percents))
				.append(DOLLAR).toString());
		minimalAddValue.pack();
	}

	public void setMinimalGet(double percents) {
		minimalGetValue.setText(new StringBuffer(Double.toString(percents))
				.append(DOLLAR).toString());
		minimalGetValue.pack();
	}

	private Label createLabel(String text) {
		Label result = new Label(this, SWT.NONE);
		if (text != null)
			result.setText(text);
		return result;

	}
}
