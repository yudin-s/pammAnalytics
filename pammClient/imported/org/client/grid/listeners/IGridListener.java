package org.client.grid.listeners;

import org.client.grid.Grid;

/**
 * Interface which provides function for all user activity in grid.
 *
 */
public interface IGridListener {

	public void leftClick(Grid grid, EventData eventData);

	public void rightClick(Grid grid, EventData eventData);

	public void doubleClick(Grid grid, EventData eventData);

	public void selection(Grid grid, EventData eventData);
}
