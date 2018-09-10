package org.client.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtils {

private static SimpleDateFormat timeDF = // getDateShortFormat
	new SimpleDateFormat("HH:mm", Locale.getDefault());
private static SimpleDateFormat monthDF = // getDateShortFormat
	new SimpleDateFormat("yyyy / MM", Locale.getDefault());	
	private static SimpleDateFormat dateDF = // getDateShortFormat
	new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	private static SimpleDateFormat shortDateTimeDF = // getDateTimeShortFormat
	new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
	private static SimpleDateFormat fullDateTimeDF = // getDateTimeFullFormat
	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	private static String NOT_DEFINED_DATE = "-";

	private DateTimeUtils() {
	}

	public static String getDate(Date date, int format, Locale locale) {
		return SimpleDateFormat.getDateInstance(format, locale).format(date);
	}

	public static String getTime(Date time, int format, Locale locale) {
		return SimpleDateFormat.getTimeInstance(format, locale).format(time);
	}

	public static String getDateTime(Date dateTime, int format, Locale locale) {
		return SimpleDateFormat.getDateTimeInstance(format, format, locale).format(dateTime);
	}

	/**
	 * @return Localized date value in format yyyy-MM
	 */
	public static String formatMonth(Date date) {
		if (date != null) {
			return monthDF.format(date.getTime());
		}
		else {
			return NOT_DEFINED_DATE;
		}
	}

	/**
	 * @return time value in format HH:mm
	 */
	public static String formatTime(Date date) {
		if (date == null) {
			return NOT_DEFINED_DATE;
		}
		return timeDF.format(date.getTime());
	}

	/**
	 * @return Localized date value in format yyyy-MM-dd
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return NOT_DEFINED_DATE;
		}
		return dateDF.format(date.getTime());
	}

	/**
	 * @return Localized time value in format yyyy-MM-dd HH:mm
	 */
	public static String formatDateTimeShort(Date time) {
		if (time == null) {
			return NOT_DEFINED_DATE;
		}
		return shortDateTimeDF.format(time);
	}

	/**
	 * @return Localized date time value in format yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateTimeFull(Date dateTime) {
		if (dateTime == null) {
			return NOT_DEFINED_DATE;
		}
		return fullDateTimeDF.format(dateTime);
	}
}
