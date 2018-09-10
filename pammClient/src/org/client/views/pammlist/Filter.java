package org.client.views.pammlist;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Filter extends Composite {

	private static final String SHOW_ACCOUNTS_WITHOUT_OFFERS = "Показывать счета без оферт";
	private static final String ALWAYS_SHOW_FAVORITES = "Всегда показывать избранное";
	private static final String ACCESS_FOR_INVESTING_NOT_LESS = "Доступно для инвестирование, не меньше";
	private static final String SUMM_IN_CONTROL_NOT_LESS_THAN = "Сумма в управлении, не меньше";
	private static final String MAXIMAL_DROPDOWN_NOT_BIGEST = "Максимальная, не больше";
	private static final String RELATIVE_DROPDOWN_NOT_BIGEST = "Относительная, не больше";
	private static final String DROPDOWN = "Просадка";
	private static final String NOW_CAPITAL_NOT_LESS = "Текущий, не меньше";
	private static final String START_CAPITAL_NOT_LESS_THAN = "Начальный, не меньше";
	private static final String TRAIDER_CAPITAL = "Капитал управляющего";
	private static final String DAYS = "дней";
	private static final String HOW_AGE = "Возраст";
	private static final String REFERRAL = "Комиссия";
	private static final String REWARD_FOR_TRAIDER_NOT_BIGGEST_THAN = "Вознаграждение трейдеру, не более";
	private static final String GEETING = "Снятие";
	private static final String ADDITION = "Пополнение";
	private static final String SUMM_AND_LESS = "Сумма и остаток";
	private static final String MINIMAL = "Минимальное";
	private static final String FOUR = "4";
	private static final String THREE = "3";
	private static final String TWO = "2";
	private static final String ONE = "1";
	private static final String SELL_PERIOD = "Торговы период, недель";
	private static final String AGENTS_EXITS = "Наличие агентских";
	private static final String BY_SUMM_NOT_LESS_THAN = "От суммы, не меньше";
	private static final String BY_PROFIT_NOT_LESS_THAN = "От прибыли, не меньше";

	public Filter(Composite parent, int style) {
		super(parent, style);
		initComponent();

	}

	RowLayout layout;

	public RowLayout getLayout() {
		return layout;
	}

	private void initComponent() {
		layout = new RowLayout();
		layout.wrap = true;

		this.setLayout(layout);

		getTypeFilter(this);
		getAgentReward(this);
		getSellPeroid(this);
		getMinimal(this);
		getPercents(this);
		getCapital(this);
		getDropDown(this);
		getAdditional(this);
		getSomeCheckboxes(this);

	}

	private Composite getTypeFilter(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		Button pamm = new Button(composite, SWT.CHECK);
		pamm.setText("ПАММ");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		pamm.setLayoutData(gridData);

		Button pamm2 = new Button(composite, SWT.CHECK);
		pamm2.setText("ПАММ 2.0");
		gridData = new GridData();
		gridData.horizontalSpan = 1;
		pamm2.setLayoutData(gridData);

		Text text = new Text(composite, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalSpan = 1;
		gridData.horizontalAlignment = GridData.FILL;
		text.setLayoutData(gridData);
		composite.setLayoutData(new RowData(140, 80));
		return composite;
	}

	private Composite getAgentReward(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 2);

		Button checkAgent = new Button(composite, SWT.CHECK);
		checkAgent.setText(AGENTS_EXITS);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		checkAgent.setLayoutData(gridData);

		Label byProfitLabel = new Label(composite, SWT.NONE);
		byProfitLabel.setText(BY_PROFIT_NOT_LESS_THAN);
		byProfitLabel.setLayoutData(getNewStandartGD());

		Text byProfitText = new Text(composite, SWT.BORDER);
		byProfitText.setLayoutData(getNewStandartGD());

		Label bySummLabel = new Label(composite, SWT.NONE);
		bySummLabel.setText(BY_SUMM_NOT_LESS_THAN);
		bySummLabel.setLayoutData(getNewStandartGD());

		Text bySummText = new Text(composite, SWT.BORDER);
		bySummText.setLayoutData(getNewStandartGD());
		return composite;

	}

	private Composite getSellPeroid(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 2);
		Label title = new Label(composite, SWT.NONE);
		title.setText(SELL_PERIOD);
		GridData gd = getNewStandartGD();
		gd.horizontalSpan = 2;
		title.setLayoutData(gd);
		getNewCheckButton(composite, ONE);
		getNewCheckButton(composite, TWO);
		getNewCheckButton(composite, THREE);
		getNewCheckButton(composite, FOUR);
		return composite;

	}

	private Composite getMinimal(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 2);
		Label textLabel = new Label(composite, SWT.NONE);
		textLabel.setText(MINIMAL);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.BEGINNING;
		textLabel.setLayoutData(gridData);

		Label bySummAndLeft = new Label(composite, SWT.NONE);
		bySummAndLeft.setText(SUMM_AND_LESS);
		bySummAndLeft.setLayoutData(getNewStandartGD());

		Text bySummAndLeftField = new Text(composite, SWT.BORDER);
		bySummAndLeftField.setLayoutData(getNewStandartGD());

		Label byMinAdd = new Label(composite, SWT.NONE);
		byMinAdd.setText(ADDITION);
		byMinAdd.setLayoutData(getNewStandartGD());

		Text byMinAddField = new Text(composite, SWT.BORDER);
		byMinAddField.setLayoutData(getNewStandartGD());

		Label byMinGet = new Label(composite, SWT.NONE);
		byMinGet.setText(GEETING);
		byMinGet.setLayoutData(getNewStandartGD());

		Text byMinGetField = new Text(composite, SWT.BORDER);
		byMinGetField.setLayoutData(getNewStandartGD());

		return composite;
	}

	private Composite getPercents(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 3);

		Label byReward = new Label(composite, SWT.NONE);
		byReward.setText(REWARD_FOR_TRAIDER_NOT_BIGGEST_THAN);
		GridData gridData = getNewStandartGD();
		gridData.horizontalSpan = 2;
		byReward.setLayoutData(gridData);

		Text byRewardText = new Text(composite, SWT.BORDER);
		byRewardText.setLayoutData(getNewStandartGD());

		Label byRefferal = new Label(composite, SWT.NONE);
		byRefferal.setText(REFERRAL);
		gridData = getNewStandartGD();
		gridData.horizontalSpan = 2;
		byRefferal.setLayoutData(gridData);

		Text byReferralText = new Text(composite, SWT.BORDER);
		byReferralText.setLayoutData(getNewStandartGD());

		Label byAge = new Label(composite, SWT.NONE);
		byAge.setText(HOW_AGE);
		byAge.setLayoutData(getNewStandartGD());

		Text byAgeText = new Text(composite, SWT.BORDER);
		gridData = getNewStandartGD();
		gridData.horizontalAlignment = SWT.RIGHT;
		byAgeText.setLayoutData(gridData);

		Label byAgeAdditional = new Label(composite, SWT.NONE);
		byAgeAdditional.setText(DAYS);
		gridData = getNewStandartGD();
		byAgeAdditional.setLayoutData(gridData);

		return composite;

	}

	private Composite getCapital(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 2);

		Label title = new Label(composite, SWT.NONE);
		title.setText(TRAIDER_CAPITAL);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		title.setLayoutData(gridData);

		Label byStartCapitalLabel = new Label(composite, SWT.NONE);
		byStartCapitalLabel.setText(START_CAPITAL_NOT_LESS_THAN);
		byStartCapitalLabel.setLayoutData(getNewStandartGD());

		Text byStartCapitalText = new Text(composite, SWT.BORDER);
		byStartCapitalText.setLayoutData(getNewStandartGD());

		Label byNowCapitalLabel = new Label(composite, SWT.NONE);
		byNowCapitalLabel.setText(NOW_CAPITAL_NOT_LESS);
		byNowCapitalLabel.setLayoutData(getNewStandartGD());

		Text byNowCapitalText = new Text(composite, SWT.BORDER);
		byNowCapitalText.setLayoutData(getNewStandartGD());
		return composite;

	}

	private Composite getDropDown(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 2);

		Label title = new Label(composite, SWT.NONE);
		title.setText(DROPDOWN);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		title.setLayoutData(gridData);

		Label byMaxLabel = new Label(composite, SWT.NONE);
		byMaxLabel.setText(RELATIVE_DROPDOWN_NOT_BIGEST);
		byMaxLabel.setLayoutData(getNewStandartGD());

		Text byMaxText = new Text(composite, SWT.BORDER);
		byMaxText.setLayoutData(getNewStandartGD());

		Label byAvgradeLabel = new Label(composite, SWT.NONE);
		byAvgradeLabel.setText(MAXIMAL_DROPDOWN_NOT_BIGEST);
		byAvgradeLabel.setLayoutData(getNewStandartGD());

		Text byAvgradeText = new Text(composite, SWT.BORDER);
		byAvgradeText.setLayoutData(getNewStandartGD());
		return composite;

	}

	private Composite getAdditional(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 2);
		Label byControlLabel = new Label(composite, SWT.NONE);
		byControlLabel.setText(SUMM_IN_CONTROL_NOT_LESS_THAN);
		byControlLabel.setLayoutData(getNewStandartGD());

		Text byControlText = new Text(composite, SWT.BORDER);
		byControlText.setLayoutData(getNewStandartGD());

		Label byInvestedLabel = new Label(composite, SWT.NONE);
		byInvestedLabel.setText(ACCESS_FOR_INVESTING_NOT_LESS);
		byInvestedLabel.setLayoutData(getNewStandartGD());

		Text byAvgradeText = new Text(composite, SWT.BORDER);
		byAvgradeText.setLayoutData(getNewStandartGD());

		return composite;

	}

	private Composite getSomeCheckboxes(Composite parent) {
		Composite composite = getNewDefaultcompositeWithGridLayout(parent, 1);
		getNewCheckButton(composite, ALWAYS_SHOW_FAVORITES);
		getNewCheckButton(composite, SHOW_ACCOUNTS_WITHOUT_OFFERS);
		return composite;
	}

	private Composite getNewDefaultcompositeWithGridLayout(Composite parent,
			int columnCount) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = columnCount;
		composite.setLayout(layout);
		return composite;
	}

	private Button getNewCheckButton(Composite parent, String caption) {
		Button result = new Button(parent, SWT.CHECK);
		result.setLayoutData(getNewStandartGD());
		result.setText(caption);
		return result;
	}

	private GridData getNewStandartGD() {
		GridData result = new GridData();
		result.horizontalSpan = 1;
		result.horizontalAlignment = GridData.BEGINNING;
		return result;
	}
}
