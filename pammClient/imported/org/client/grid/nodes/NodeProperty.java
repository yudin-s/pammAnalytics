package org.client.grid.nodes;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * Java bean which contains information about item header.
 *
 */
public class NodeProperty {

	private String text;
	private Image image;
	private Color textColor;
	private Color backgroundColor;

	public NodeProperty() {

	}

	public NodeProperty(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public NodeProperty setText(String text) {
		this.text = text;
		return this;
	}

	public Image getImage() {
		return image;
	}

	public NodeProperty setImage(Image image) {
		this.image = image;
		return this;
	}

	public Color getTextColor() {
		return textColor;
	}

	public NodeProperty setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public NodeProperty setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

}
