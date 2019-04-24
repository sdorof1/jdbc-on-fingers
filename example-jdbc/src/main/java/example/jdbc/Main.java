package example.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws SQLException {

		try (Connection conn = DriverManager.getConnection("jdbc:postgresql:jdbc-on-fingers", "jdbc-on-fingers", "jdbc-on-fingers")) {

			if (conn == null) {
				System.out.println("No database connection");
				System.exit(0);
			}

			try (Statement stmt = conn.createStatement()) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM names");
				while (rs.next()) {
					System.out
							.println(rs.getRow() + ". " + rs.getString("firstname") + "\t" + rs.getString("lastname"));
				}
			}

			{
				DatabaseMetaData md = conn.getMetaData();
				ResultSet rs = md.getTables(conn.getCatalog(), null, null, null);
				while (rs.next()) {
					System.out.println(rs.getObject(3));
				}
			}

		}
	}

}
