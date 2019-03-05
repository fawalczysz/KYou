package fr.isima.kyou.dbaccess.implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import fr.isima.kyou.dbaccess.interfaces.IBasketProducts;

@Component
public class BasketProducts implements IBasketProducts {

	@Override
	public void selectAll() {
		final String sql = "SELECT id, basketId, productId,productNumber FROM BasketProducts";
		try (Connection conn = Connector.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getInt("basketId") + "\t" + rs.getInt("productId") + "\t"
						+ rs.getInt("productNumber"));
			}

		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
