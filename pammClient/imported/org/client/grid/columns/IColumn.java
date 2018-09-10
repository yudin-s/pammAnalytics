package org.client.grid.columns;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import org.client.grid.properties.Property;

/**
 * Specify all column properties
 *
 */
public interface IColumn {

	public ColumnStyle getStyle();

	public Color getTextColor();

	public Color getBackgroundColor();

	public String getText();

	public String getTooltip();

	public String getHeaderTooltip();

	public Image getImage();

	public Font getHeaderFont();

	public Font getFont();

	public int getWidth();

	public boolean isWordWrap();

	public boolean isMoveable();

	public boolean isSortable();

	public boolean isResizeable();

	public boolean isVisible();
	
	public boolean isAutoResizeable();

	public Property processProperty(Property property);
}
