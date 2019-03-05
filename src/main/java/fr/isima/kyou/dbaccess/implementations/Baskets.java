package fr.isima.kyou.dbaccess.implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import fr.isima.kyou.dbaccess.interfaces.IBaskets;

@Component
public class Baskets implements IBaskets {

	@Override
	public void selectAll() {
		final String sql = "SELECT id, userId, basketNumber FROM Baskets";
		try (Connection conn = Connector.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getInt("userId") + "\t" + rs.getInt("basketNumber"));
			}

		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
