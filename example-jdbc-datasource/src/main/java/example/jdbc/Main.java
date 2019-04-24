package example.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGSimpleDataSource;

public class Main {

	public static void main(String[] args) throws SQLException {

		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName("localhost");
		ds.setPortNumber(5432);
		ds.setDatabaseName("jdbc-on-fingers");
		ds.setUser("jdbc-on-fingers");
		ds.setPassword("jdbc-on-fingers");

		try (Connection conn = ds.getConnection("jdbc-on-fingers", "jdbc-on-fingers")) {

			if (conn == null) {
				System.out.println("No database connection");
				System.exit(0);
			}

			try (Statement stmt = conn.createStatement()) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM names");
				while (rs.next()) {
					System.out.println(rs.getRow() + ". " + rs.getString("firstname") + "\t" + rs.getString("lastname"));
				}
			}
		}
	}

}
