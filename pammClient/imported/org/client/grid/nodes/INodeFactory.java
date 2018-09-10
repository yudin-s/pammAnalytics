package org.client.grid.nodes;

import java.util.List;

import org.client.grid.Grid;

public interface INodeFactory {

	public void setData(List<Object> itemsList);

	public List<Node> getNodes();

	public Node getNodeFromObject(Object value);

	public Grid getGrid();

	public void setGrid(Grid grid);
}
