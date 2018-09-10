package org.client.wigets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class RadioButtonGroup extends Composite {
	int buttonStyle = SWT.RADIO;
	int aligment = SWT.VERTICAL;
	private Listener listener;
	FormToolkit toolkit;
	public RadioButtonGroup(Composite parent, int style) {
		super(parent, style);
		toolkit = new FormToolkit(parent.getDisplay());
		listener = new Listener() {
			public void handleEvent(Event e) {
				Control[] children = getChildren();
				for (int i = 0; i < children.length; i++) {
					Control child = children[i];
					if (e.widget != child && child instanceof Button
							&& (child.getStyle() & buttonStyle) != 0) {
						((Button) child).setSelection(false);
					}
				}
				((Button) e.widget).setSelection(true);
			}
		};
		FillLayout layout = new FillLayout();
		setLayout(layout);
	}

	public RadioButtonGroup(Composite parent, int style, int buttonStyle) {
		this(parent, style);
		this.buttonStyle = buttonStyle;
	}

	public RadioButtonGroup(Composite parent, int style, int buttonStyle,
			int aligment) {
		this(parent, style, buttonStyle);
		this.aligment = aligment;
		FillLayout layout = new FillLayout();
		layout.type = aligment;
		setLayout(layout);
	}

	public RadioButtonGroup(Composite parent, int style, int buttonStyle,
			int aligment, String buttons[]) {
		this(parent, style, buttonStyle, aligment);
		setButtons(buttons);
	}

	public void setButtons(String array[]) {
		for (String text : array) {
			Button button = toolkit.createButton(this, text,buttonStyle);
			button.addListener(SWT.Selection, listener);
		}
	}

	public void setSelected(int id) {
		Control arr[] = this.getChildren();
		int nowButton = 0;
		for (Control control : arr) {
			if (control instanceof Button) {
				Button now = (Button) control;
				if (nowButton == id)
					now.setSelection(true);
				else
					now.setSelection(false);
				nowButton++;
			}
		}
	}

	public int getSelectedId() {
		Control arr[] = this.getChildren();
		int nowButton = 0;
		for (Control control : arr) {
			if (control instanceof Button) {
				Button now = (Button) control;
				if (now.getSelection())
					return nowButton;
				else
					nowButton++;
			}
		}
		return -1;
	}
}
