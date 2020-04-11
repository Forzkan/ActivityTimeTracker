package rf.java.sqlite.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import activity.tracker.database.ActivityDatabaseHelper;

public class DatabaseConnectionImpl implements DatabaseConnection {

	private final Logger LOGGER = LogManager.getLogger(DatabaseConnectionImpl.class);

	private final String database;
	private final ActivityDatabaseHelper activityHelper = new ActivityDatabaseHelper();

	public DatabaseConnectionImpl(String aDatabasePath, boolean aCreate) {
		database = stringPathToConnectionString(aDatabasePath);
		// Create a new database if one does not exist.. if aCreate is true.
		if (aCreate && connect() == null) {
			createNewDatabase();
		}
	}

	/**
	 * Creates a new database in the database folder. At present, the database will
	 * always be created in the database folder in the project root directory.
	 *
	 * @param fileName the database file name
	 */
	@Override
	public boolean createNewDatabase() {
		return exexuteStatement(activityHelper.createActivityTableQuery());
	}

	@Override
	public boolean exexuteStatement(String aStatement) {

		try (Connection conn = connect(); Statement stmt = conn.createStatement()) {

			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				LOGGER.debug("Database driver detected: " + meta.getDriverName());
				LOGGER.debug("The driver name is " + meta.getDriverName());
				stmt.execute(aStatement);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public String stringPathToConnectionString(String aDatabasePath) {
		return "jdbc:sqlite:" + aDatabasePath.toString();
	}

	@Override
	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(database);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	@Override
	public boolean databaseExists() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean verifyConnection() {
		return connect() != null ? true : false;
	}


}
