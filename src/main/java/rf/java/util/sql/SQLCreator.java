package rf.java.util.sql;

/**
 *
 * @author robert
 *
 *         This class is used for all general queries such as Insert new entry
 *         into table, Select all entries from Table, and update values in entry
 *         where one value = x.
 *
 */
public class SQLCreator {

	public static String getInsertNewEntryIntoTableQuery(String aTableName, String... entries) {

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO " + aTableName + "(");
		String endString = "";
		for(int i = 0; i < entries.length; i++) {
			sql.append(entries[i]);
			endString += "?";
			// If not last entry in entries then add a comma before adding the next entry to be inserted.
			if(i != entries.length - 1) {
				sql.append(",");
				endString += ",";
			}
		}

		sql.append(") VALUES (");
		sql.append(endString);
		sql.append(")");

		return sql.toString();
	}


	public static String getSelectAllEntriesFromTableSQL(String aTableName, String aCondition, String... entries) {
		//String sql = "SELECT id, name, description FROM projectInformation";
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		for(int i = 0; i < entries.length; i++) {
			sql.append(entries[i]);
			// If not last entry in entries then add a comma before adding the next entry to be inserted.
			if(i != entries.length - 1) {
				sql.append(",");
			}
		}
		sql.append(" FROM "+aTableName);
		return sql.toString();
	}


	public static String getUpdateEntriesWhereSQL(String aTableName, String aCondition, String... entries) {
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE "+aTableName+ " SET ");
		for(int i = 0; i < entries.length; i++) {
			sql.append(entries[i] +" = ? ");
			// If not last entry in entries then add a comma before adding the next entry to be updated.
			if(i != entries.length - 1) {
				sql.append(",");
			}
		}
		sql.append("WHERE " + aCondition + " = ?");

		return sql.toString();
	}

}
