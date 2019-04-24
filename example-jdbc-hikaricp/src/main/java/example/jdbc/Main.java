package example.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Main {

	public static void main(String[] args) throws Exception {

		DataSource ds = createDataSource();

		try (Connection conn = ds.getConnection()) {

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
		}
	}

	private static DataSource createDataSource() {
		String propFileName = "hikari.properties";
		try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(propFileName)) {
			Properties props = new Properties();
			props.load(inputStream);
			HikariConfig config = new HikariConfig(props);
			return new HikariDataSource(config);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
