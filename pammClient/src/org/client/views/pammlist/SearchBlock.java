package org.client.views.pammlist;

import org.client.wigets.RadioButtonGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class SearchBlock extends Composite {

	private static final String SEARCH_BUTTON = "Искать";
	private static final String SEARCH_TEXT = "Номер счёта или ник";

	public SearchBlock(Composite parent, int style) {
		super(parent, style);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		setLayout(layout);

		Composite temp = genSearchBlock(this);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		temp.setLayoutData(gridData);

		temp = genRadios(this);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.grabExcessHorizontalSpace = true;
		temp.setLayoutData(gridData);
		temp.pack();
	}

	private Composite genSearchBlock(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		final Text text = new Text(composite, SWT.BORDER);
		text.setText(SEARCH_TEXT);
		text.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				if (text.getText().equals(SEARCH_TEXT)) {
					text.setText("");
				}

			}

			@Override
			public void mouseDown(MouseEvent e) {
				mouseUp(e);
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				mouseUp(e);
			}
		});
		Button button = new Button(composite, SWT.NONE);
		button.setText(SEARCH_BUTTON);
		return composite;
	}

	private Composite genRadios(final Composite parent) {
		RadioButtonGroup group = new RadioButtonGroup(parent, SWT.NONE,
				SWT.RADIO);
		String buttons[] = new String[] { "Детали", "Ролловеры", "Доходность" };
		group.setButtons(buttons);
		return group;
	}
}
