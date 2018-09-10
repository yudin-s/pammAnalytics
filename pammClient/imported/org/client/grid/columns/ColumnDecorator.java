package org.client.grid.columns;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import org.client.grid.properties.Property;

/**
 * Base class for column wrappers which specify specific properties of concrete column.
 *
 */
public abstract class ColumnDecorator implements IColumn {

	private IColumn column;

	public ColumnDecorator(IColumn column) {
		this.column = column;
	}

	protected IColumn getColumn() {
		return column;
	}

	@Override
	public ColumnStyle getStyle() {
		return column.getStyle();
	}

	@Override
	public String getText() {
		return column.getText();
	}

	@Override
	public String getTooltip() {
		return column.getTooltip();
	}

	@Override
	public String getHeaderTooltip() {
		return column.getHeaderTooltip();
	}

	@Override
	public int getWidth() {
		return column.getWidth();
	}

	@Override
	public Image getImage() {
		return column.getImage();
	}

	@Override
	public Font getHeaderFont() {
		return column.getHeaderFont();
	}

	@Override
	public Font getFont() {
		return column.getFont();
	}

	@Override
	public boolean isWordWrap() {
		return column.isWordWrap();
	}

	@Override
	public boolean isMoveable() {
		return column.isMoveable();
	}

	@Override
	public Color getTextColor() {
		return column.getTextColor();
	}

	@Override
	public Color getBackgroundColor() {
		return column.getBackgroundColor();
	}

	@Override
	public boolean isSortable() {
		return column.isSortable();
	}

	@Override
	public boolean isResizeable() {
		return column.isResizeable();
	}

	@Override
	public boolean isVisible() {
		return column.isVisible();
	}

	@Override
	public boolean isAutoResizeable() {
    	return column.isAutoResizeable();
    }

	public Property processProperty(Property property) {
		property = getColumn().processProperty(property);
		if (property != null) {
			if (property.getValue() != null) {
				processAdditional(property);
			}
		}
		return property;
	}

	/**
	 * Children classes have to implement their specific behavior.
	 */
	protected abstract Property processAdditional(Property property);

}
