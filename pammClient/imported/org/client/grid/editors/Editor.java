package org.client.grid.editors;

import org.eclipse.swt.widgets.Control;

import org.client.grid.Grid;
import org.client.grid.nodes.Node;

/**
 * Editor which place in cell.
 * Children editor classes have to implement addListeners() function.
 * That function determines the behavior of Editor.
 *
 */
public abstract class Editor {

	protected Control control;
	protected Node node;
	protected Grid grid;

	public Editor(Control control) {
		setControl(control);
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
		addListeners();
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	protected abstract void addListeners();

}
