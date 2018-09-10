package org.client.grid.properties;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

/**
 * Java bean which contains all information about cell.
 *
 */
public class Property {

	private Object value;
	private String text;
	private String toolTip;
	private Image image;
	private Color textColor;
	private Color backgroundColor;
	private Font font;

	private Boolean checked;
	private Boolean checkable;
	private Boolean grayed;

	public Property() {
		this(null);
	}

	/**
	 * Create property and initialize it's value.
	 * @param value value of cell
	 */
	public Property(Object value) {
		this.value = value;
	}

	/**
	 * Create property and initialize it's value.
	 * Used for cells with checkbox.
	 * @param value value of cell text
	 * @param checked value of checkbox
	 */
	public Property(Object value, Boolean checked) {
		this.value = value;
		this.checked = checked;
	}

	public Object getValue() {
		return value;
	}

	public Property setValue(Object value) {
		this.value = value;
		return this;
	}

	public String getText() {
		return text;
	}

	public Property setText(String text) {
		this.text = text;
		return this;
	}

	public String getToolTip() {
		return toolTip;
	}

	public Property setToolTip(String toolTip) {
		this.toolTip = toolTip;
		return this;
	}

	public Image getImage() {
		return image;
	}

	public Property setImage(Image image) {
		this.image = image;
		return this;
	}

	public Color getTextColor() {
		return textColor;
	}

	public Property setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Property setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public Font getFont() {
		return font;
	}

	public Property setFont(Font font) {
		this.font = font;
		return this;
	}

	public Boolean isChecked() {
		return checked;
	}

	public Property setChecked(Boolean checked) {
		this.checked = checked;
		return this;
	}

	public Boolean isCheckable() {
		return checkable;
	}

	public Property setCheckable(Boolean checkable) {
		this.checkable = checkable;
		return this;
	}

	public Boolean isGrayed() {
		return grayed;
	}

	public Property setGrayed(Boolean grayed) {
		this.grayed = grayed;
		return this;
	}

}
