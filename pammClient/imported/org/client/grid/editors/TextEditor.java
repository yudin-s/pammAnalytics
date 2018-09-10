package org.client.grid.editors;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import org.client.grid.Grid;
import org.client.grid.nodes.Node;

/**
 * Cell text editor. After any change in Text call modifyCell(), which can be override.
 *
 */
public abstract class TextEditor extends Editor {

	public TextEditor(Text text) {
		super(text);
	}

	@Override
	protected void addListeners() {
		((Text) control).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				modifyCell(grid, node, (Text) control);
			}
		});
	}

	protected abstract void modifyCell(Grid grid, Node node, Text text);

}
