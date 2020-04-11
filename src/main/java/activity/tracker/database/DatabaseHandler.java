package activity.tracker.database;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import activity.tracker.database.dto.Activity;
import activity.tracker.properties.PropertiesHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rf.java.sqlite.util.DatabaseConnection;
import rf.java.sqlite.util.DatabaseConnectionImpl;
import rf.java.util.PathUtil;

public class DatabaseHandler {

	private DatabaseConnection connection;
	private final PropertiesHandler properties;
	private final ActivityDatabaseHelper activityDbHelper = new ActivityDatabaseHelper();

	public DatabaseHandler(PropertiesHandler aProperties, String aDatabasePath) {
		properties = aProperties;
		connection = new DatabaseConnectionImpl(getDatabasePath(aDatabasePath), true);
	}

	public ObservableList<Activity> getSavedActivities() {
		ObservableList<Activity> history = FXCollections.<Activity>observableArrayList();
		history.addAll(getAllActivities());
		return history;
	}

	/**
	 * Verify database connection on file path.
	 *
	 * @param create - new database from the provided file path if true.
	 * @return true if a database connection could be established successfully.
	 */
	public boolean verifyDatabaseConnection() {
		if (connection.verifyConnection() && connection.databaseExists()) {
			return true;
		}
		return false;
	}

	// TODO:: this should read the properties.
	private String getDatabasePath(String fileName) {
		return PathUtil.getProjectRoot() + File.separatorChar + fileName;
	}

	/**
	 * Insert a new row into the ProjectInformation table
	 *
	 * @param name
	 * @param description
	 */
	public Activity insertNewActivity(String aName, String aDescription) {

		String sql = activityDbHelper.insertNewActivityQuery(aName, aDescription);
		connection.exexuteStatement(sql);
		return activityDbHelper.getActivityOnName(connection.connect(), aName);
	}

	public Activity getActivityOnName(String aName) {
		return activityDbHelper.getActivityOnName(connection.connect(), aName);
	}

	/**
	 * select all rows in the warehouses table
	 */
	public ArrayList<Activity> getAllActivities() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		//String sql = activityDbHelper.getSelectAllQuery();
		//System.out.println("Selecting by executing statement with the following SQL:  \n" + sql);
		try (Connection conn = connection.connect()) {
			activities = activityDbHelper.getAllActivities(conn);
		} catch (SQLException | ParseException e) {
			System.out.println("Error retrieving data from the database.");
			System.out.println(e.getMessage());
		}
		return activities;
	}
//
//	/**
//	 * Update data of a Project specified by the id
//	 *
//	 * @param id
//	 * @param name        name of the Project
//	 * @param description capacity of the Project
//	 */
//	public static void updateProjectInformationOnID(long aId, String dbName, String aName, String aDescription) {
//		String sql = SQLCreator.getUpdateEntriesWhereSQL(tableName, id, name, description);
//		// LOGGER.debug("Updating by executing statement with the following SQL: \n" +
//		// sql);
//		// TODO:: Edited date should be updated to the current date and time.
//		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//			// set the corresponding param
//			pstmt.setString(1, aName);
//			pstmt.setString(2, aDescription);
//			pstmt.setLong(3, aId);
//			// update
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//	}

}
