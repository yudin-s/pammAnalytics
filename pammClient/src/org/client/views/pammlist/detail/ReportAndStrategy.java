package org.client.views.pammlist.detail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class ReportAndStrategy extends Composite {

	private static final String DEFAULT_STRATEGY_MESSAGE = "подбрасывать монетку";
	private static final String DEFAULT_CONNECTION_MESSAGE = "через радистку Кэт";
	private static final String SELL_STRATEGY = "Торговая стратегия:";
	private static final String CONNECTION_VARIANT = "Способ связи:";
	private static final String LEVEL_OF_REPORT = "Уровень отчётности:";
	Label levelReportText;
	Label levelReportValue;
	Label connectionText;
	Label connectionValue;
	Label strategyText;
	Label strategyValue;
	private final static String WEEKLY = "недельный";
	private final static String ONLINY = "онлайн";
	private final static String EVERYDAY = "ежедневный";

	private Label createLabel(String text) {
		Label result = new Label(this, SWT.NONE);
		if (text != null)
			result.setText(text);
		return result;

	}

	public enum LevelReport {
		WEEK(WEEKLY), ONLINE(ONLINY), DIALY(EVERYDAY);
		public final String val;

		private LevelReport(String v) {
			val = v;
		}
	}

	public void setReportLevel(LevelReport level) {
		levelReportValue.setText(level.val);
		levelReportValue.pack();
	}

	public void setStrategy(String strategy) {
		strategyValue.setText(strategy);
		strategyValue.pack();
	}

	public void setConnectionVar(String strategy) {
		connectionValue.setText(strategy);
		connectionValue.pack();
	}

	public ReportAndStrategy(Composite parent, int style) {
		super(parent, style);
		TableWrapLayout twl = new TableWrapLayout();
		twl.numColumns = 2;
		setLayout(twl);
		levelReportText = createLabel(LEVEL_OF_REPORT);
		levelReportValue = createLabel(LevelReport.DIALY.val);
		connectionText = createLabel(CONNECTION_VARIANT);
		connectionValue = createLabel(DEFAULT_CONNECTION_MESSAGE);
		strategyText = createLabel(SELL_STRATEGY);
		strategyValue = createLabel(DEFAULT_STRATEGY_MESSAGE);
		pack();
	}

}
