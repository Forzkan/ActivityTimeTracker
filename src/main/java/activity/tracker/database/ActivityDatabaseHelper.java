package activity.tracker.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public String createActivityTableQuery() {

		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n " + id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n "
				+ name
				+ " varchar NOT NULL,\n " + description + " text DEFAULT '',\n " + createdDate
				+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n " + lastTrackedDate
				+ " DATETIME DEFAULT CURRENT_TIMESTAMP, \n " + totalMinutesTracked + " INTEGER DEFAULT 0 \n " + ");";
		System.out.println(sql);

		StringBuilder query = new StringBuilder();

		query.append("CREATE TABLE IF NOT EXISTS " + tableName + " (");
		query.append(id + " INTEGER PRIMARY KEY AUTOINCREMENT,");
		query.append(name + " text NOT NULL,");
		query.append(description + " text DEFAULT '',");
		query.append(createdDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
		query.append(lastTrackedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
		query.append(totalMinutesTracked + " INTEGER DEFAULT 0");
		query.append(");");

		return query.toString();
	}

	public String getSelectAllQuery() {
//		return SQLCreator.getSelectAllEntriesFromTableSQL(tableName, id, name, description, createdDate,
//				lastTrackedDate, totalMinutesTracked);

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM " + tableName + ";");
		return query.toString();
	}

	public String insertNewActivityQuery(String aName, String aDescription) {
//		return SQLCreator.getInsertNewEntryIntoTableQuery(tableName, name, description);
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
				Activity a = new Activity(rs.getInt(id), rs.getString(name), rs.getString(description),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(createdDate)),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(lastTrackedDate)),
						rs.getInt(totalMinutesTracked));
				activities.add(a);
				System.out.println(a.activityInformationToString());
			}
		}
		return activities;
	}
//	public static String createProjectInfoTableSQL() {
//
//		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n " + id + " integer PRIMARY KEY,\n " + name
//				+ " varchar NOT NULL,\n " + description + " text NOT NULL,\n " + createdDate
//				+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n " + editedDate + " DATETIME DEFAULT CURRENT_TIMESTAMP \n "
//				+ ");";
//		System.out.println(sql);
//		return sql;
//	}

	public Activity getActivityOnName(Connection aConn, String aName) {
		Activity activity = null;
		String sql = getActivityOnNameQuery(aName);
		try (Statement stmt = aConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			// loop through the result set
			if (rs.next()) {
				System.out.println("WE ARE CREATING A NEW ACTIVITY...");
				System.out.println(rs.getInt(id) + "" + rs.getString(name));
				activity = new Activity(rs.getInt(id), rs.getString(name), rs.getString(description),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(createdDate)),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(lastTrackedDate)),
						rs.getInt(totalMinutesTracked));
			}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activity;
	}

	private String getActivityOnNameQuery(String aName) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM " + tableName + " WHERE " + name + "='" + aName + "';");
		return query.toString();
	}

}
