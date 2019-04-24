package example.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public class Main {

	private static Properties loadProperties(String filename) {
		try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename)) {
			Properties props = new Properties();
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws SQLException {
		Properties props = loadProperties("flyway.properties");
		Flyway flyway = new Flyway();
		flyway.configure(props);
		try {
			flyway.validate();
			System.out.println("Database is up to date");
		} catch (FlywayException e) {
			System.out.println("Migrating a database");
			flyway.migrate();
		}
	}
}
