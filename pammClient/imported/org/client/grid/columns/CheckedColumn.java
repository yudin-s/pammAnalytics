package org.client.grid.columns;

/**
 * Base column class describes the behavior of the check column.
 *
 */
public class CheckedColumn extends Column {

	private boolean checkable = false;
	private boolean checked = false;
	private boolean grayed = false;

	private CheckedColumn(String text) {
		super(text);
	}

	public static CheckedColumn createFromColumnName(String columnName) {
		return new CheckedColumn(columnName);
	}

//	public static CheckedColumn createFromResourceId(IResources resource, String resourceId) {
//		return new CheckedColumn(resource.wrap(resourceId));
//	}

	/**
	 * Checks whether the user can change the value of check.
	 */
	public boolean isCheckable() {
		return checkable;
	}

	/**
	 * Set can user change value of check.
	 * You can set whether the cell is changed only if column can be changed.
	 * If user can't change column than checkable parameter of cells is ignored.
	 * @param checkable true if user can change value of check and false otherwise
	 */
	public CheckedColumn setCheckable(boolean checkable) {
		this.checkable = checkable;
		return this;
	}

	public boolean isChecked() {
    	return checked;
    }

	public CheckedColumn setChecked(boolean checked) {
    	this.checked = checked;
		return this;
    }

	public boolean isGrayed() {
    	return grayed;
    }

	public CheckedColumn setGrayed(boolean grayed) {
    	this.grayed = grayed;
		return this;
    }

}
