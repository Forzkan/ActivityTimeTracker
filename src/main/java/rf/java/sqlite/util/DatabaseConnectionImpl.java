package rf.java.sqlite.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnectionImpl implements DatabaseConnection {

	private final Logger LOGGER = LogManager.getLogger(DatabaseConnectionImpl.class);

	private final String database;

	public DatabaseConnectionImpl(String aDatabasePath) {
		database = stringPathToConnectionString(aDatabasePath);
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

	private Connection connect() {
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
