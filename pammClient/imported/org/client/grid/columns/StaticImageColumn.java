package org.client.grid.columns;

import org.eclipse.swt.graphics.Image;

import org.client.grid.properties.Property;

/**
 * Wrapper column which shows image in the cell.
 * It should be used as an alternative of button, because the picture is the same for all cells.
 *
 */
public class StaticImageColumn extends ColumnDecorator {

	private Image image;

	public StaticImageColumn(IColumn column, Image image) {
		super(column);
		this.image = image;
	}

	@Override
	public Property processAdditional(Property property) {
		property.setText("");
		property.setImage(image);
		return property;
	}

}
