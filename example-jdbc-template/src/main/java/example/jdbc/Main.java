package example.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.Data;

public class Main {

	@Data
	public static class Name {
		private final Long id;
		private String firstname;
		private String lastname;

		public static Name mapper(ResultSet rs, int rowNum) throws SQLException {
			Name name = new Name(rs.getLong("id"));
			name.setFirstname(rs.getString("firstname"));
			name.setLastname(rs.getString("lastname"));
			return name;
		}
	}

	public static void main(String[] args) throws SQLException {
		DataSource ds = createDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.query("SELECT * FROM names", Name::mapper).forEach(System.out::println);
	}

	private static DataSource createDataSource() {
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName("localhost");
		ds.setPortNumber(5432);
		ds.setDatabaseName("jdbc-on-fingers");
		ds.setUser("jdbc-on-fingers");
		ds.setPassword("jdbc-on-fingers");
		return ds;
	}
}
