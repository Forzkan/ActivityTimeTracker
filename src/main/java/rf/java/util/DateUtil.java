package rf.java.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	private static final String DATE_FORMAT = "MMM d, yyyy HH:mm a";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

	public DateUtil() {
	}

//	public static Timestamp getTimestampCurrentTime() {
//		Date date = new Date();
//		long time = date.getTime();
//		Timestamp ts = new Timestamp(time);
//		return ts;
//	}


	public static LocalDateTime getNowFormated() {
		return LocalDateTime.now();
	}

	public static LocalDateTime toFormattedDateTime(String dateTime) {
		return LocalDateTime.parse(dateTime, formatter);
	}

	public static String toFormattedDateTime(LocalDateTime dateTime) {
		return LocalDateTime.parse(dateTime.toString(), formatter).toString();
	}


//	public static boolean dateHasPassed(LocalDateTime date, LocalDateTime deadlineDate) {
//		return date.isAfter(date);
//	}

}
