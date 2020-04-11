package rf.java.sqlite.util;

import java.sql.Connection;

public interface DatabaseConnection {

	public Connection connect();
	/**
	 * Verifies that the database on the given path exist.
	 *
	 * @param aDatabasePath
	 * @return true if exist, false otherwise.
	 */
	public boolean databaseExists();

	public boolean verifyConnection();

	public boolean createNewDatabase();
	public boolean exexuteStatement(String aStatement);

	public String stringPathToConnectionString(String aDatabasePath);
}
