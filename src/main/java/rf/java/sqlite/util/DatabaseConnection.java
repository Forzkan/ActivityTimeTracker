package rf.java.sqlite.util;

public interface DatabaseConnection {

	/**
	 * Verifies that the database on the given path exist.
	 *
	 * @param aDatabasePath
	 * @return true if exist, false otherwise.
	 */
	public boolean databaseExists();

	public boolean verifyConnection();

	public boolean exexuteStatement(String aStatement);

	public String stringPathToConnectionString(String aDatabasePath);
}
