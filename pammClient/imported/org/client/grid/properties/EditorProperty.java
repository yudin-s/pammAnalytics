package org.client.grid.properties;

import org.eclipse.swt.SWT;

import org.client.grid.editors.Editor;

/**
 * Java bean which contains all information about editor cell.
 *
 */
public class EditorProperty extends Property {

	private Editor editor;
	private boolean grabHorizontal = true;
	private boolean grabVertical = true;
	private int horizontalAlignment = SWT.LEFT;
	private int verticalAlignment = SWT.TOP;
	private int minimumWidth = 0;
	private int minimumHeight = 0;

	public EditorProperty(Object value) {
		super(value);
	}

	public EditorProperty(Object value, Editor editor) {
		this(value);// Value is used by sorting
		setEditor(editor);
	}

	public Editor getEditor() {
		return editor;
	}

	public EditorProperty setEditor(Editor editor) {
		this.editor = editor;
		return this;
	}

	public boolean isGrabHorizontal() {
		return grabHorizontal;
	}

	public EditorProperty setGrabHorizontal(boolean grabHorizontal) {
		this.grabHorizontal = grabHorizontal;
		return this;
	}

	public boolean isGrabVertical() {
		return grabVertical;
	}

	public EditorProperty setGrabVertical(boolean grabVertical) {
		this.grabVertical = grabVertical;
		return this;
	}

	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public EditorProperty setHorizontalAlignment(int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}

	public int getVerticalAlignment() {
		return verticalAlignment;
	}

	public EditorProperty setVerticalAlignment(int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}

	public int getMinimumWidth() {
		return minimumWidth;
	}

	public EditorProperty setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
		return this;
	}

	public int getMinimumHeight() {
		return minimumHeight;
	}

	public EditorProperty setMinimumHeight(int minimumHeight) {
		this.minimumHeight = minimumHeight;
		return this;
	}

}
