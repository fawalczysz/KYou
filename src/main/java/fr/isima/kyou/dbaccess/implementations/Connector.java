package fr.isima.kyou.dbaccess.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	private static Connector instance;
	private static Connection connection;

	Connector() {

	}

	public static Connector getInstance() {
		if (instance == null) {
			instance = new Connector();
		}
		return instance;
	}

	/**
	 * Connect to the test.db database
	 * 
	 * @return the Connection object
	 */
	protected static Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:" + ClassLoader.getSystemClassLoader().getResource("kUser.db").getFile();
		url = url.replace("/", "\\");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}
