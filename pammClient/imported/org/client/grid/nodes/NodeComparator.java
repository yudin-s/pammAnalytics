package org.client.grid.nodes;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;

import org.client.grid.Grid;
import org.client.grid.properties.Property;

/**
 * Compare grid's nodes by column.
 * This class is used by sorting.
 *
 * @param <T> Node. Can compare GridItems too but now they aren't used.
 */
public class NodeComparator<T> implements Comparator<T> {

	private int direction;
	private String column;

	public NodeComparator(int direction, String column) {
		this.direction = direction;
		this.column = column;
	}

	public NodeComparator() {
		this.direction = SWT.UP;
		this.column = null;
	}

	@Override
	public int compare(T e1, T e2) {
		if (column == null) {
			return 0;
		}
		Object p1 = getObject(e1);
		Object p2 = getObject(e2);
		int rc = 0;
		if (p1 == null) {
			if (p2 == null) {
				rc = 0;
			} else {
				rc = 1;
			}
		} else if (p2 == null) {
			rc = -1;
		} else if (p1 instanceof String) {
			rc = String.valueOf(p2).compareTo(String.valueOf(p1));
		} else if (p1 instanceof Boolean) {
			if ((Boolean) p1 && !(Boolean) p2) {
				rc = 1;
			} else if (!(Boolean) p1 && (Boolean) p2) {
				rc = -1;
			}
		} else if (p1 instanceof Integer) {
			rc = ((Integer) p1 < (Integer) p2) ? 1 : (p1.equals(p2) ? 0 : -1);
		} else if (p1 instanceof Date) {
			rc = (((Date) p1).compareTo((Date) p2) < 0) ? 1 : (p1.equals(p2) ? 0 : -1);
		} else if (p1 instanceof Float) {
			rc = ((Float) p1 < (Float) p2) ? 1 : (p1.equals(p2) ? 0 : -1);
		} else if (p1 instanceof BigDecimal) {
			rc = (((BigDecimal) p2).compareTo((BigDecimal) p1));
		}

		// If descending order, flip the direction
		if (direction == SWT.DOWN)
			rc = -rc;
		return rc;
	}

	private Object getObject(Object e) {
		Object object = e;
		if (object instanceof GridItem) {
			object = ((GridItem) object).getData();
		}
		if (object instanceof Node) {
			Property property = ((Node) object).getValues().get(column);
			if (property != null) {
				return property.getValue();
			} else {
				return Grid.EMPTY_FIELD;
			}
		} else {
			return object;
		}
	}

}
