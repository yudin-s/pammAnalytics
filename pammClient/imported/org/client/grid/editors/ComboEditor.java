package org.client.grid.editors;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;

import org.client.grid.Grid;
import org.client.grid.nodes.Node;


/**
 * Cell combo editor. After any change in Combo call modifyCell(), which can be override.
 *
 */
public abstract class ComboEditor extends Editor {

	public ComboEditor(Combo combo) {
		super(combo);
	}

	@Override
	protected void addListeners() {
		((Combo) control).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				modifyCell(grid, node, (Combo) control);
			}
		});
	}

	protected abstract void modifyCell(Grid grid, Node node, Combo text);

}
