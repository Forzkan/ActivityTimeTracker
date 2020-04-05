package activity.tracker.database;

import java.io.File;
import java.sql.Date;

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

	public DatabaseHandler(PropertiesHandler aProperties) {
		properties = aProperties;
	}

	public ObservableList<Activity> getSavedActivities() {
		ObservableList<Activity> history = FXCollections.<Activity>observableArrayList();
		history.addAll(
				new Activity(1, "activity_name", "description", new Date(System.currentTimeMillis()),
						new Date(System.currentTimeMillis()), 540),
				new Activity(2, "another name", "description", new Date(System.currentTimeMillis()),
						new Date(System.currentTimeMillis()), 540));
		return history;
	}

	public boolean createNewConnection() {
		// TODO, fix the connection and get the values from the properties.
		connection = new DatabaseConnectionImpl(getDatabasePath("myDB"));
		return connection.verifyConnection() && connection.databaseExists();
	}

	// TODO:: this should read the properties.
	private String getDatabasePath(String fileName) {
		return PathUtil.getProjectRoot() + File.separatorChar + "database" + File.separatorChar + fileName;
	}


}
