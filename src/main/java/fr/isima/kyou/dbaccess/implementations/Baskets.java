package fr.isima.kyou.dbaccess.implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.Basket;
import fr.isima.kyou.beans.User;
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

	@Override
	public List<Basket> selectBasketsOfUser(String email) {
		final String sql = String.format(
				"SELECT Baskets.id, userId, basketNumber FROM Baskets join Users on Users.id=Baskets.userId where Users.email='%s'",
				email);

		try (Connection conn = Connector.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			final List<Basket> baskets = new ArrayList<>();
			// loop through the result set
			while (rs.next()) {
				final User user = new User();
				user.setId(rs.getInt("userId"));
				final Basket basket = new Basket();
				basket.setId(rs.getInt("id"));
				basket.setUser(user);
				basket.setNumber(rs.getInt("basketNumber"));
				baskets.add(basket);
			}
			return baskets;

		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Basket> addProductInBasket(String email, Integer basketNumber, String barCode) {
		// TODO
		return null;
	}

}
