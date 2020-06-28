package activity.tracker.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import activity.tracker.database.dto.Activity;

public class ActivityDatabaseHelper {

	// Table and column names.
	private static final String tableName = "activity";
	private static final String id = "activity_id";
	private static final String name = "name";
	private static final String description = "description";
	private static final String createdDate = "createdDate";
	private static final String lastTrackedDate = "lastTrackedDate";
	private static final String totalMinutesTracked = "totalMinutesTracked";
	private static final String favorite = "favorite";
	private static final String DATE_FORMAT = "MMM d, yyyy HH:mm a";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

	public String createActivityTableQuery() {
		// formatter = new SimpleDateFormat(DATE_FORMAT);

		StringBuilder query = new StringBuilder();

		query.append("CREATE TABLE IF NOT EXISTS " + tableName + " (");
		query.append(id + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		query.append(name + " text NOT NULL,");
		query.append(description + " text DEFAULT '',");
		query.append(createdDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
		query.append(lastTrackedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
		query.append(totalMinutesTracked + " INTEGER DEFAULT 0,");
		query.append(favorite + " INTEGER DEFAULT 0");
		query.append(");");
		System.out.println(query.toString());
		return query.toString();
	}

	public String getSelectAllQuery() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM " + tableName + ";");
		return query.toString();
	}

	public String insertNewActivityQuery(String aName, String aDescription) {
		StringBuilder query = new StringBuilder();

		query.append("INSERT INTO " + tableName + " (" + name + "," + description + ")");
		query.append("VALUES('" + aName + "', '" + aDescription + "');");

		return query.toString();
	}

	public ArrayList<Activity> getAllActivities(Connection conn) throws SQLException, ParseException {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(getSelectAllQuery())) {
			// loop through the result set
			while (rs.next()) {
				Activity activity = getNextActivityOnResultSet(rs);
				activities.add(activity);
				System.out.println(activity.activityInformationToString());
			}
		}
		return activities;
	}


	public Activity getActivityOnName(Connection aConn, String aName) {
		Activity activity = null;
		String sql = getActivityOnNameQuery(aName);
		try (Statement stmt = aConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			// loop through the result set
			if (rs.next()) {
				System.out.println("WE ARE CREATING A NEW ACTIVITY...");
				System.out.println(rs.getInt(id) + "" + rs.getString(name));
				activity = getNextActivityOnResultSet(rs);
			}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activity;
	}

	// formatter
	private Activity getNextActivityOnResultSet(ResultSet rs) throws SQLException, ParseException {
		Activity activity;
		activity = new Activity(rs.getInt(id), rs.getString(name), rs.getString(description),
				LocalDateTime.parse(rs.getString(createdDate)),
				LocalDateTime.parse(rs.getString(lastTrackedDate), formatter),
				rs.getInt(totalMinutesTracked), rs.getInt(favorite) == 0 ? false : true);
		return activity;
	}

	private String getActivityOnNameQuery(String aName) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM " + tableName + " WHERE " + name + "='" + aName + "';");
		return query.toString();
	}

}
