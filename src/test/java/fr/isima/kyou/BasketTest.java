package fr.isima.kyou;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;
import fr.isima.kyou.services.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/beans.xml" })
@SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager")
@EntityScan(basePackages = "fr.isima.kyou.beans.dao")

public class BasketTest {

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;

	@Before
	public void cleanup() {

	}

	@Test
	@Sql(scripts = "classpath:clean.sql", config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void NormalTest() throws DaoException {
		final String email = "toto.toto@toto.com";
		final String barCode = "1234";
		final Integer basketNumber = 0;
		final User user = new User();
		user.setFirstname("toto");
		user.setLastname("toto");
		user.setEmail(email);
		final Integer id = userService.addUser(user);
		assertNotNull(id);
		final Basket b = userService.createBasketFromuser(basketNumber, email);

		assertEquals(b.getBasketNumber().intValue(), 0);
		assertEquals(b.getUser().getEmail(), email);

		final BasketProduct bp = productService.addProductInBasket(email, barCode, basketNumber);

		assertEquals(bp.getProduct().getBarCode(), barCode);
		assertEquals(bp.getProductNumber().intValue(), 1);

	}
}
