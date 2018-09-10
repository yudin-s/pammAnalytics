package org.client.grid.nodes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import org.client.grid.Grid;

/**
 * Abstract class which provide nodes to grid.
 * Children classes have to override setNodeData() function, 
 * which set data from DTO object to node.
 * @author bars
 *
 */
public abstract class NodeFactory implements INodeFactory {

	@SuppressWarnings("rawtypes")
	List itemsList;
	Grid grid;

	@SuppressWarnings("rawtypes")
	@Override
	public void setData(List itemsList) {
		this.itemsList = itemsList;
	}

	@Override
	public List<Node> getNodes() {
		List<Node> values = new ArrayList<Node>();
		if (itemsList == null) {
			return values;
		}
		for (Object value : itemsList) {
			values.add(getNodeFromObject(value).setRepresentedObject(value));
		}
		return values;
	}

	@Override
	public Node getNodeFromObject(Object value) {
		Node node = new Node();
		setNodeData(node, value);
		return node;
	}

	public abstract void setNodeData(Node node, Object object);

	@Override
	public Grid getGrid() {
		return grid;
	}

	@Override
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	protected Composite getComposite() {
		return (Composite) getGrid().getControl();
	}
}
