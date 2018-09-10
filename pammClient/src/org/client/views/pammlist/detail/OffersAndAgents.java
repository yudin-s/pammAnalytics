package org.client.views.pammlist.detail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class OffersAndAgents extends Composite{
	private static final String EMPTY = "";
	private static final String PERCENT = "%";
	private static final String OFFERS_OPENED = "Сделок открыто";
	private static final String DEFAULT_COUNT = "1000";
	private static final String SPACE = " ";
	private static final String DEFAULT_PERCENT = "10%";
	private static final String BY_PROFIT = "с прибыли";
	private static final String BY_CALLING = "за привлечение";
	private static final String AGENTS = "Агентские";
	Label offersOpenText;
	Label offersOpenValue;
	Label agentsByProfitText;
	Label agentsByCallingText;
	Label agentsByProfitValue;
	Label agentsByCallingValue;
	
	private Label createLabel(String text) {
		Label result = new Label(this, SWT.NONE);
		if (text != null)
			result.setText(text);
		return result;
	}
	public void setAgentsByCallingPercents(double percents){
		agentsByCallingValue.setText(EMPTY+percents*100+PERCENT);
		agentsByCallingValue.pack();
	}
	public void setAgentsByProfitPercents(double percents){
		agentsByProfitValue.setText(EMPTY+percents*100+PERCENT);
		agentsByProfitValue.pack();
	}
	public void setAgentsByProfitPercents(short openOffers){
		offersOpenValue.setText(EMPTY+openOffers);
		offersOpenValue.pack();
	}
	public OffersAndAgents(Composite parent, int style) {
		super(parent, style);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 3;
		setLayout(layout);
		offersOpenText = createLabel(OFFERS_OPENED);
		TableWrapData data = new TableWrapData();
		data.colspan = 2;
		offersOpenText.setLayoutData(data);
		
		offersOpenValue = createLabel(DEFAULT_COUNT);
		data = new TableWrapData();
		offersOpenValue.setLayoutData(data);
		
		createLabel(AGENTS);
		agentsByCallingText = createLabel(BY_CALLING);
		agentsByCallingValue = createLabel(DEFAULT_PERCENT);
		
		createLabel(SPACE);
		agentsByProfitText = createLabel(BY_PROFIT);
		agentsByProfitValue = createLabel(DEFAULT_PERCENT);
		pack();
	}
	
}
