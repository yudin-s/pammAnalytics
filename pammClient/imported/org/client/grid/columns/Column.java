package org.client.grid.columns;

import java.text.DecimalFormat;
import java.util.Date;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import org.client.grid.Grid;
import org.client.grid.properties.Property;
import org.client.utils.DateTimeUtils;

/**
 * Java bean which contains information about column.
 *
 */
public class Column implements IColumn {

	private static final int DEFAULT_WIDTH = 100;

	private String text;
	private String tooltip;
	private String headerTooltip;
	private Image image;
	private Font headerFont;
	private Font font;
	private Color textColor;
	private Color backgroundColor;
	private int width = DEFAULT_WIDTH;
	private ColumnStyle style = ColumnStyle.NONE;
	private boolean moveable = true;
	private boolean sortable = true;
	private boolean resizeable = true;
	private boolean visible = true;
	private boolean wordWrap = false;
	private boolean autoResizeable = true;

	protected Column(String text) {
		this.text = text;
	}

	public static Column createFromColumnName(String columnName) {
		return new Column(columnName);
	}

//	public static Column createFromResourceId(IResources resource, String resourceId) {
//		return new Column(resource.wrap(resourceId));
//	}

	@Override
	public String getTooltip() {
		return tooltip;
	}

	public Column setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	@Override
	public String getHeaderTooltip() {
		return headerTooltip;
	}

	public Column setHeaderTooltip(String tooltip) {
		this.headerTooltip = tooltip;
		return this;
	}

	@Override
	public String getText() {
		return text;
	}

	public Column setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public Image getImage() {
		return image;
	}

	public Column setImage(Image image) {
		this.image = image;
		return this;
	}

	@Override
	public Font getHeaderFont() {
		return headerFont;
	}

	public Column setHeaderFont(Font headerFont) {
		this.headerFont = headerFont;
		return this;
	}

	@Override
	public Font getFont() {
		return font;
	}

	public Column setFont(Font font) {
		this.font = font;
		return this;
	}

	@Override
	public Color getTextColor() {
		return textColor;
	}

	public Column setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Column setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public Column setWidth(int width) {
		this.width = width;
		setAutoResizeable(false);
		return this;
	}

	@Override
	public ColumnStyle getStyle() {
		return style;
	}

	public Column setStyle(ColumnStyle style) {
		this.style = style;
		return this;
	}

	@Override
	public boolean isMoveable() {
		return moveable;
	}

	public Column setMoveable(boolean moveable) {
		this.moveable = moveable;
		return this;
	}

	@Override
	public boolean isSortable() {
		return sortable;
	}

	public Column setSortable(boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	@Override
	public boolean isResizeable() {
		return resizeable;
	}

	public Column setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
		return this;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public Column setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	@Override
	public boolean isWordWrap() {
		return wordWrap;
	}

	public Column setWordWrap(boolean wordWrap) {
		this.wordWrap = wordWrap;
		return this;
	}

	@Override
	public boolean isAutoResizeable() {
    	return autoResizeable;
    }

	public Column setAutoResizeable(boolean autoResizeable) {
    	this.autoResizeable = autoResizeable;
		return this;
    }

	@Override
	public Property processProperty(Property property) {
		if (property == null) {
			property = new Property(Grid.EMPTY_FIELD);
		}
		if (property.getValue() != null) {
			Object value = property.getValue();
			fillCellValue(property, value);
		} else {
			property.setText(Grid.EMPTY_FIELD);
		}
		return property;
	}
	
	static void fillCellValue(Property property, Object value) {
		String text = "";
		Image image = null;
		if (value instanceof Date) {
			text = DateTimeUtils.formatDateTimeFull((Date) value);
		} else if ((value instanceof Float) || (value instanceof Double)) {
			text = new DecimalFormat("#.##").format(value);
		} else if (value instanceof Image) {
			image = (Image) value;
		} else {
			text = value.toString();
		}
		property.setText(text);
		property.setImage(image);
	}
}
