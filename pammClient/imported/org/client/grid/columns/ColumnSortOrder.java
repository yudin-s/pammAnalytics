package org.client.grid.columns;

import org.eclipse.swt.SWT;

/**
 * Specify column sorting labels.
 * Wrap standard SWT constants.
 */
public enum ColumnSortOrder {
	NONE(SWT.NONE), DOWN(SWT.DOWN), UP(SWT.UP);

	private final int order;

	ColumnSortOrder(int order) {
		this.order = order;
	}

	public int getSWTOrder() {
		return order;
	}

}
