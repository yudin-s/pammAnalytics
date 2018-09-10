package org.client.grid.columns;

import org.eclipse.swt.SWT;

/**
 * Wrap SWT alignment constants.
 *
 */
public enum ColumnStyle {
	NONE(SWT.NONE), LEFT(SWT.LEFT), RIGHT(SWT.RIGHT), CENTER(SWT.CENTER);

	private final int style;

	ColumnStyle(int style) {
		this.style = style;
	}

	public int getSWTStyle() {
		return style;
	}
}
