package example.jdbc;

import java.sql.Date;
import java.sql.SQLException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import lombok.Value;

public class Main {

	@Value
	public static class Name {
		private Long id;
		private String firstname;
		private String lastname;
		private String middlename;
		private Date date;
	}

	public static void main(String[] args) throws SQLException {

		Sql2o sql2o = new Sql2o("jdbc:postgresql:jdbc-on-fingers", "jdbc-on-fingers", "jdbc-on-fingers");

		try (Connection conn = sql2o.open()) {

			if (conn == null) {
				System.out.println("No database connection");
				System.exit(0);
			}

			conn.createQuery("SELECT * FROM names").executeAndFetch(Name.class).forEach(n -> {
				System.out.println(n.getId() + ". " + n.getFirstname() + "\t" + n.getLastname());
			});
		}
	}
}
