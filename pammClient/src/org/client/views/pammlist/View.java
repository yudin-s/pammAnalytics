package org.client.views.pammlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.client.beans.FxTGridData;
import org.client.beans.FxTTypes;
import org.client.datamanager.DataGetter;
import org.client.grid.Grid;
import org.client.grid.columns.BooleanColumn;
import org.client.grid.columns.CheckedColumn;
import org.client.grid.columns.Column;
import org.client.grid.columns.IColumn;
import org.client.grid.nodes.Node;
import org.client.grid.nodes.NodeFactory;
import org.client.grid.properties.Property;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.*;

public class View extends ViewPart {

	private static final String TAGS = "Тэги";
	private static final String MAX_INVEST = "Максимальные инвестиции";
	private static final String DEFAULT_DETAIL = "Детали счёта 2202(Dan)";
	private static final String SPACE = " ";
	private static final String FILTER = "Фильтр";
	private static final String STRATEGY = "Описание торговой стратегии";
	private static final String CONNECTIION_METHOD = "Способ связи с управляющим";
	private static final String AGENTS_BY_PROFIT = "Агентские с прибыли";
	private static final String AGENT_BY_CALLING_IN = "Агентские за привлечение";
	private static final String MIN_SUM_OF_ADD = "Минимальная сумма пополнения";
	private static final String MIN_SUMM_AND_LEFT = "Минимальная сумма и остаток";
	private static final String REFFERAL = "Комиссия";
	private static final String PROFIT = "Вознаграждение";
	private static final String OFFER_PERIOD = "Торговый период";
	private static final String NUM_OF_OPEN_DEALS = "Количество открытых сделок";
	private static final String AVGRADE_DROPDOWN = "Относительная просадка";
	private static final String MAX_DROPDOWN = "Максимальная просадка";
	private static final String LEVEL_ONUS = "Уровень ответственности";
	private static final String PROFIT_PER_MOUNTH = "Доходность за месяц";
	private static final String PROFIT_PER_WEEK = "Доходность за неделю";
	private static final String AGE = "Возраст";
	private static final String KY_KI = "КУ:КИ";
	private static final String CASH_IN_CONTROL = "Сумма в управлении";
	private static final String CURRENT_CAPITAL = "Текущий капитал управляющего";
	private static final String START_CAPITAL = "Начальный капитал управляющего";
	private static final String ACCOUNT_NUMBER = "Номер счёта";
	private static final String PAMM_2 = "ПАММ 2.0";
	private static final String PAMM = "ПАММ";
	private static final String TYPE_ACCOUNT = "Тип счёта";

	private FormToolkit toolkit;
	private ScrolledForm form;

	Grid grid;
	Filter filter;
	Detail detail;
	SearchBlock search;

	public View() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {

		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setExpandHorizontal(false);
		form.pack();

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);

		initFilterAndSearchGroup(form.getBody());
		initMainGrid(form.getBody());
		initDetailTabs(form.getBody());

		form.reflow(true);
	}

	private void initFilterAndSearchGroup(final Composite parent) {
		final Section group = new Section(parent, Section.TITLE_BAR
				| Section.TWISTIE);
		group.setText(FILTER);
		group.setLayout(new GridLayout());
		group.addExpansionListener(new GroupOnExpansionListener(group));

		TableWrapData td = new TableWrapData();
		td.colspan = 1;
		td.maxWidth = 150;
		group.setLayoutData(td);

		filter = new Filter(group, SWT.WRAP);
		filter.setLayoutData(new GridData());

		group.setClient(filter);

		search = new SearchBlock(parent, SWT.WRAP);
		toolkit.adapt(search);
		td = new TableWrapData();
		td.colspan = 1;
		td.grabHorizontal = true;
		td.align = TableWrapData.FILL;
		search.setLayoutData(td);

	}

	private void initMainGrid(Composite parent) {
		List<IColumn> columnsList = new ArrayList<IColumn>();
		columnsList.add(CheckedColumn.createFromColumnName(SPACE));
		columnsList.add(new BooleanColumn(Column
				.createFromColumnName(TYPE_ACCOUNT), PAMM, PAMM_2));
		columnsList.add(Column.createFromColumnName(ACCOUNT_NUMBER));
		columnsList.add(Column.createFromColumnName(START_CAPITAL));
		columnsList.add(Column.createFromColumnName(CURRENT_CAPITAL));
		columnsList.add(Column.createFromColumnName(CASH_IN_CONTROL));
		columnsList.add(Column.createFromColumnName(KY_KI));
		columnsList.add(Column.createFromColumnName(AGE));
		columnsList.add(Column.createFromColumnName(PROFIT_PER_WEEK));
		columnsList.add(Column.createFromColumnName(PROFIT_PER_MOUNTH));
		columnsList.add(Column.createFromColumnName(LEVEL_ONUS));
		columnsList.add(Column.createFromColumnName(MAX_DROPDOWN));
		columnsList.add(Column.createFromColumnName(AVGRADE_DROPDOWN));
		columnsList.add(Column.createFromColumnName(NUM_OF_OPEN_DEALS));
		columnsList.add(Column.createFromColumnName(OFFER_PERIOD));
		columnsList.add(Column.createFromColumnName(PROFIT));
		columnsList.add(Column.createFromColumnName(REFFERAL));
		columnsList.add(Column.createFromColumnName(MIN_SUMM_AND_LEFT));
		columnsList.add(Column.createFromColumnName(MIN_SUM_OF_ADD));
		columnsList.add(Column.createFromColumnName(AGENT_BY_CALLING_IN));
		columnsList.add(Column.createFromColumnName(AGENTS_BY_PROFIT));
		columnsList.add(Column.createFromColumnName(CONNECTIION_METHOD));
		columnsList.add(Column.createFromColumnName(MAX_INVEST));
		columnsList.add(Column.createFromColumnName(STRATEGY));
		columnsList.add(Column.createFromColumnName(TAGS));

		ScrolledComposite sComposite = new ScrolledComposite(parent, SWT.NONE
				| SWT.H_SCROLL | SWT.V_SCROLL);
		sComposite.setExpandHorizontal(true);
		sComposite.setExpandVertical(true);
		sComposite.setMinSize(400, 250);
		toolkit.adapt(sComposite);
		sComposite.setLayout(new TableWrapLayout());
		TableWrapData td = new TableWrapData();
		td.colspan = 2;
		td.grabHorizontal = true;
		td.maxWidth = 100;
		td.maxHeight = 500;
		td.align = TableWrapData.FILL;

		sComposite.setLayoutData(td);
		grid = new Grid(sComposite, Grid.DEFAULT_STYLE | SWT.WRAP,
				new PammListNodeFactory(), columnsList);
		sComposite.setContent(grid.getComposite());

		sComposite.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				DataGetter getter = DataGetter.getInstance();
				List<FxTGridData> list = getter.getDataForTable();
				grid.setData(list);
			}
		});
	}

	private void initDetailTabs(Composite parent) {

		Section group = new Section(parent, Section.TITLE_BAR | Section.TWISTIE);
		detail = new Detail(group, SWT.WRAP);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		TableWrapData td = new TableWrapData();
		td.colspan = 2;

		group.setLayout(new GridLayout());
		detail.setLayoutData(new GridData());
		group.setClient(detail);
		group.setLayoutData(gridData);
		group.setLayoutData(td);
		detail.setLayoutData(td);

		detail.setNewAdministrator(0);
		group.setText(DEFAULT_DETAIL);

	}

	private class PammListNodeFactory extends NodeFactory {
		@Override
		public void setNodeData(Node node, Object object) {
			Map<String, Property> values = new HashMap<String, Property>();
			if (object instanceof FxTGridData) {
				FxTGridData data = (FxTGridData) object;
				values.put(ACCOUNT_NUMBER, property(data.getAccountNumber()));
				values.put(AGE, property(data.getAge()));
				values.put(AGENTS_BY_PROFIT, property(data.getAgentsByProfit()));
				values.put(AGENT_BY_CALLING_IN,
						property(data.getAgentsByCalling()));
				values.put(AVGRADE_DROPDOWN,
						property(data.getAvgrageDropdown()));
				values.put(CASH_IN_CONTROL, property(data.getSummInControl()));
				values.put(CONNECTIION_METHOD,
						property(data.getConnectionMethod()));
				values.put(CURRENT_CAPITAL, property(data.getCurrentCapital()));
				values.put(KY_KI, property(data.getCICA()));
				values.put(LEVEL_ONUS,
						property(data.getLevelOfResponsibility()));
				values.put(MAX_DROPDOWN, property(data.getMaxDropdown()));
				values.put(MAX_INVEST, property(data.getMaxInvestitions()));
				values.put(MIN_SUMM_AND_LEFT,
						property(data.getMinimalLeftAndAdd()));
				values.put(MIN_SUM_OF_ADD,
						property(data.getMinimalSummOfAdditional()));
				values.put(NUM_OF_OPEN_DEALS,
						property(data.getNumOfOpenDeals()));
				values.put(OFFER_PERIOD, property(data.getSellPeriod()));
				values.put(PROFIT, property(data.getProfit()));
				values.put(PROFIT_PER_MOUNTH,
						property(data.getProfitPerMounth()));
				values.put(PROFIT_PER_WEEK, property(data.getProfitPerWeek()));
				values.put(REFFERAL, property(data.getReferral()));
				values.put(START_CAPITAL, property(data.getStartCapital()));
				values.put(STRATEGY, property(data.getStrategy()));
				values.put(TYPE_ACCOUNT,
						property(getStringType(data.getType())));

			}
			node.setValues(values);
		}
	}

	private String getStringType(FxTTypes type) {
		return type.name;
	}

	private Property property(int val) {
		return new Property(val);
	}

	private Property property(String val) {
		return new Property(val);
	}

	private Property property(double val) {
		return new Property(val);
	}

	private class GroupOnExpansionListener implements IExpansionListener {

		private final Section section;

		private final TableWrapData beforeExpandGroup;
		private final TableWrapData beforeExpandSearch;
		private final TableWrapData afterExpandGroup;
		private final TableWrapData afterExpandSearch;

		public GroupOnExpansionListener(final Section group) {
			section = group;

			beforeExpandGroup = new TableWrapData();
			beforeExpandGroup.colspan = 1;
			beforeExpandGroup.maxWidth = 150;

			beforeExpandSearch = new TableWrapData();
			beforeExpandSearch.colspan = 1;
			beforeExpandSearch.grabHorizontal = true;
			beforeExpandSearch.align = TableWrapData.FILL;

			afterExpandGroup = new TableWrapData();
			afterExpandGroup.colspan = 2;
			afterExpandGroup.grabHorizontal = true;
			afterExpandGroup.maxWidth = 100;
			afterExpandGroup.align = TableWrapData.FILL;

			afterExpandSearch = new TableWrapData();
			afterExpandSearch.colspan = 2;
			afterExpandSearch.grabHorizontal = true;
			afterExpandSearch.maxWidth = 100;
			afterExpandSearch.align = TableWrapData.FILL;

		}

		@Override
		public void expansionStateChanging(ExpansionEvent e) {
			if (section.isExpanded()) {
				section.setLayoutData(beforeExpandGroup);
				search.setLayoutData(beforeExpandSearch);
			} else {
				section.setLayoutData(afterExpandGroup);
				search.setLayoutData(afterExpandSearch);
			}
		}

		@Override
		public void expansionStateChanged(ExpansionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void setFocus() {
		form.setFocus();
	}
}
