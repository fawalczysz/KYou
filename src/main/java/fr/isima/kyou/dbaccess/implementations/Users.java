package fr.isima.kyou.dbaccess.implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import fr.isima.kyou.dbaccess.interfaces.IUsers;

@Repository
public class Users implements IUsers {

	@Override
	public void selectAll() {
		final String sql = "SELECT id, firstname, lastname, email FROM Users";
		try (Connection conn = Connector.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("firstname") + "\t" + rs.getString("lastname")
						+ "\t" + rs.getString("email"));
			}

		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
