package org.client.grid.editors;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import org.client.grid.Grid;
import org.client.grid.nodes.Node;

/**
 * Cell FormattedText editor. After any change in Text call modifyCell(), which can be override.
 *
 */
public abstract class FormattedTextEditor extends Editor {

	FormattedText formattedText;

	public FormattedTextEditor(FormattedText text) {
		super(text.getControl());
		this.formattedText = text;
	}

	@Override
	protected void addListeners() {
		((Text) control).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				modifyCell(grid, node, formattedText);
			}
		});
	}

	protected abstract void modifyCell(Grid grid, Node node, FormattedText text);

}
