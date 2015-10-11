package com.barganha.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Date: 23/06/14
 * Time: 12:24
 */
public final class DateTimeHelper {

    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String FILE_DATETIME_FORMAT_PATTERN = "yyyyMMdd_HHmmss";

    public static final String GMT = "GMT";

    private DateTimeHelper() {
        throw new AssertionError();
    }

    public static LocalDate toDate(String dateString) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_FORMAT_PATTERN);
        DateTime date = new DateTime(fmt.parseDateTime(dateString), DateTimeZone.forID(GMT));
        return date.toLocalDate();
    }

    public static DateTime toDateTime(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATETIME_FORMAT_PATTERN);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
        return new DateTime(dateTime, DateTimeZone.forID(GMT));
    }

    public static LocalDate getCurrentDate() {
        return new LocalDate(DateTimeZone.forID(GMT));
    }

    public static DateTime getCurrentDateTime() {
        return new DateTime(DateTimeZone.forID(GMT));
    }

    public static String getCurrentDateTimeAsString() {
        DateTime now = getCurrentDateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(FILE_DATETIME_FORMAT_PATTERN);
        return now.toString(fmt);
    }
}
