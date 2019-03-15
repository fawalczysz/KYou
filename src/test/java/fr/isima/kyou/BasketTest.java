package fr.isima.kyou;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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

		// new user
		final User user = new User();
		user.setFirstname("toto");
		user.setLastname("toto");
		user.setEmail(email);
		final Integer id = userService.addUser(user);
		assertNotNull(id);

		// new basket
		final Basket b = userService.createBasketFromuser(basketNumber, email);
		assertEquals(b.getBasketNumber().intValue(), 0);
		assertEquals(b.getUser().getEmail(), email);

		// new product in basket
		final BasketProduct bp = productService.addProductInBasket(email, barCode, basketNumber);

		assertEquals(bp.getProduct().getBarCode(), barCode);
		assertEquals(bp.getProductNumber().intValue(), 1);

	}

	@Test(expected = DaoException.class)
	@Sql(scripts = "classpath:clean.sql", config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void multipleUsers() throws DaoException {
		// first user creation
		String email = "nomail.nomail@nomail.com";
		User user = new User();
		user.setFirstname("toto");
		user.setLastname("toto");
		user.setEmail(email);
		Integer id = userService.addUser(user);
		assertNotNull(id);

		// new user with same email
		email = "nomail.nomail@nomail.com";
		user = new User();
		user.setFirstname("titi");
		user.setLastname("titi");
		user.setEmail(email);
		// expect error of same email added
		id = userService.addUser(user);
	}

	@Test
	@Sql(scripts = "classpath:clean.sql", config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void addMultipleProducts() throws DaoException {
		final String email = "toto.toto@toto.com";
		String barCode = "1234";
		final Integer basketNumber = 0;
		final User user = new User();
		user.setFirstname("toto");
		user.setLastname("toto");
		user.setEmail(email);

		// is user created
		final Integer id = userService.addUser(user);
		assertNotNull(id);

		// is Basket created and linked to User
		Basket b = userService.createBasketFromuser(basketNumber, email);
		assertEquals(b.getBasketNumber().intValue(), 0);
		assertEquals(b.getUser().getEmail(), email);

		// product added in basket
		BasketProduct bp = productService.addProductInBasket(email, barCode, basketNumber);
		assertEquals(bp.getProduct().getBarCode(), barCode);
		assertEquals(1, bp.getProductNumber().intValue());

		// 2 products added in same basket
		bp = productService.addProductInBasket(email, barCode, basketNumber);
		assertEquals(2, bp.getProductNumber().intValue());

		// other product added in basket
		barCode = "12345";
		bp = productService.addProductInBasket(email, barCode, basketNumber);
		assertEquals(1, bp.getProductNumber().intValue());

		// remove one of each product
		bp = productService.removeProductInBasket(email, barCode, basketNumber);
		barCode = "1234";
		bp = productService.removeProductInBasket(email, barCode, basketNumber);

		// basket should only contain the product added twice
		b = userService.selectBasketFromIdAndUser(email, basketNumber);
		final List<BasketProduct> products = productService.getProductsFromBasket(b);
		assertEquals(1, products.get(0).getProductNumber().intValue());
		assertEquals(1, products.size());

	}

	@Test
	@Sql(scripts = "classpath:clean.sql", config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void removeMultipleProducts() throws DaoException {
		final String email = "toto.toto@toto.com";
		final String barCode = "1234";
		final Integer basketNumber = 0;
		final User user = new User();
		user.setFirstname("toto");
		user.setLastname("toto");
		user.setEmail(email);

		// is user created
		final Integer id = userService.addUser(user);
		assertNotNull(id);

		// is Basket created and linked to User
		Basket b = userService.createBasketFromuser(basketNumber, email);
		assertEquals(b.getBasketNumber().intValue(), 0);
		assertEquals(b.getUser().getEmail(), email);

		// product added in basket
		BasketProduct bp = productService.addProductInBasket(email, barCode, basketNumber);
		assertEquals(bp.getProduct().getBarCode(), barCode);
		assertEquals(1, bp.getProductNumber().intValue());

		// 2 products added in same basket
		bp = productService.addProductInBasket(email, barCode, basketNumber);
		assertEquals(2, bp.getProductNumber().intValue());

		productService.removeAllProductInBasket(email, barCode, basketNumber);

		b = userService.selectBasketFromIdAndUser(email, basketNumber);
		final List<BasketProduct> products = productService.getProductsFromBasket(b);
		assertEquals(0, products.size());

	}

}
