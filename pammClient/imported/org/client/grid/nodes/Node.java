package org.client.grid.nodes;

import java.util.Map;

import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import org.client.grid.properties.Property;

/**
 * Java bean which contains information about grid item.
 * @author bars
 *
 */
public class Node {

	private String tooltip;
	private Color textColor;
	private Color backgroundColor;
	private Font font;
	private int height;
	private Map<String, Property> values;
	private NodeProperty nodeProperty;
	private Object representedObject;
	private GridItem gridItem;

	public Color getTextColor() {
		return textColor;
	}

	public Node setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Node setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public Font getFont() {
		return font;
	}

	public Node setFont(Font font) {
		this.font = font;
		return this;
	}

	public Map<String, Property> getValues() {
		return values;
	}

	public Node setValues(Map<String, Property> values) {
		this.values = values;
		return this;
	}

	public NodeProperty getNodeProperty() {
		return nodeProperty;
	}

	public Node setNodeProperty(NodeProperty nodeProperty) {
		this.nodeProperty = nodeProperty;
		return this;
	}

	public String getTooltip() {
		return tooltip;
	}

	public Node setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public Node setHeight(int height) {
		this.height = height;
		return this;
	}

	public Object getRepresentedObject() {
		return representedObject;
	}

	public Node setRepresentedObject(Object representedObject) {
		this.representedObject = representedObject;
		return this;
	}

	public GridItem getGridItem() {
		return gridItem;
	}

	public void setGridItem(GridItem gridItem) {
		this.gridItem = gridItem;
	}

}
