package org.client.grid.listeners;

import org.client.grid.columns.IColumn;
import org.client.grid.nodes.Node;

/**
 * Contains information about grid event.
 * Used when user click to grid or select item.
 *
 */
public class EventData {

	private IColumn column;
	private Node node;
	private boolean headerClick;

	public EventData() {
	}

	public EventData(IColumn column, Node node) {
		this.column = column;
		this.node = node;
	}

	public EventData(IColumn column, Node node, boolean headerClick) {
		this(column, node);
		this.headerClick = headerClick;
	}

	public IColumn getColumn() {
		return column;
	}

	public void setColumn(IColumn column) {
		this.column = column;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Object getObject() {
		return node.getRepresentedObject();
	}

	/**
	 * 
	 * @return true when user click on column header.
	 */
	public boolean isHeaderClick() {
		return headerClick;
	}

	public void setHeaderClick(boolean headerClick) {
		this.headerClick = headerClick;
	}

	/**
	 * 
	 * @return true when user click on empty item/column of grid.
	 */
	public boolean isEmptyRowClick() {
		return ((node == null) && (!headerClick));
	}

}
