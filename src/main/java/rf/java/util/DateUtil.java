package rf.java.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {

	public static Timestamp getTimestampCurrentTime() {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		return ts;
	}

}
