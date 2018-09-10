package org.client.grid.columns;

import java.util.Date;

import org.client.grid.properties.Property;
import org.client.utils.DateTimeUtils;

/**
 * Wrapper column class which specify format of displaying date.
 *
 */
public class DateColumn extends ColumnDecorator {

	private DateFormat dateFormat;

	public enum DateFormat {
		DATE, DATE_TIME_SHORT, DATE_TIME_FULL, MONTH;
	}

	public DateColumn(IColumn column) {
		this(column, DateFormat.DATE_TIME_FULL);
	}

	public DateColumn(IColumn column, DateFormat dateFormat) {
		super(column);
		this.dateFormat = dateFormat;
	}

	@Override
	public Property processAdditional(Property property) {
		try {
			Date date = (Date) property.getValue();
			String text;
			if (dateFormat == DateFormat.DATE) {
				text = DateTimeUtils.formatDate(date);
			} else if (dateFormat == DateFormat.DATE_TIME_SHORT) {
				text = DateTimeUtils.formatDateTimeShort(date);
			} else if (dateFormat == DateFormat.DATE_TIME_FULL) {
				text = DateTimeUtils.formatDateTimeFull(date);
			} else if (dateFormat == DateFormat.MONTH) {
				text = DateTimeUtils.formatMonth(date);
			} else {
				// Set default format
				text = DateTimeUtils.formatDateTimeFull(date);
			}
			property.setText(text);
		} catch (ClassCastException exc) {
		}
		return property;
	}

}
