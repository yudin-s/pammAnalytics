package org.client.grid.columns;

import org.client.grid.properties.Property;

public class BooleanColumn extends ColumnDecorator {

	private Object trueObject;
	private Object falseObject;

	public BooleanColumn(IColumn column, Object trueObject, Object falseObject) {
		super(column);
		this.trueObject = trueObject;
		this.falseObject = falseObject;
	}

	@Override
	public Property processAdditional(Property property) {
		if (property.getValue() instanceof Boolean) {
			if ((Boolean) property.getValue()) {
				Column.fillCellValue(property, trueObject);
			} else {
				Column.fillCellValue(property, falseObject);
			}
		}
		return property;
	}

}
