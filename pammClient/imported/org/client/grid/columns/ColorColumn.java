package org.client.grid.columns;

import org.eclipse.swt.graphics.Color;

import org.client.grid.properties.Property;


/**
 * Wrapper column which specify foreground and background colors of column.
 * 
 */
public class ColorColumn extends ColumnDecorator {

	private Color textColor;
	private Color backgroundColor;

	public ColorColumn(IColumn column, Color color) {
		this(column, color, null);
	}

	public ColorColumn(IColumn column, Color textColor, Color backgroundColor) {
		super(column);
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}

	@Override
	public Property processAdditional(Property property) {
		property.setTextColor(textColor);
		property.setBackgroundColor(backgroundColor);
		return property;
	}
}
