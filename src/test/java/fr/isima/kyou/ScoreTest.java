package fr.isima.kyou;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.enums.Score;
import fr.isima.kyou.exceptions.ApiException;
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;
import fr.isima.kyou.services.interfaces.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/beans.xml" })
@SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager")
@EntityScan(basePackages = "fr.isima.kyou.beans.dao")

public class ScoreTest {

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;

	@Before
	public void cleanup() {

	}

	@Test
	public void testNutritionnalQualities() throws DaoException {
		List<Nutriment> defects = productService.getProductDefects("1234");
		List<Nutriment> qualities = productService.getProductQualities("1234");
		assertEquals(2, defects.size());
		assertEquals(4, qualities.size());

		defects = productService.getProductDefects("12345");
		qualities = productService.getProductQualities("12345");
		assertEquals(2, defects.size());
		assertEquals(4, qualities.size());
	}

	@Test
	public void testNutritionnalScore() throws DaoException {
		assertEquals(Score.TRES_BON, productService.getProductScore("12345678"));
		assertEquals(Score.BON, productService.getProductScore("1234"));
		assertEquals(Score.MOYEN, productService.getProductScore("12345"));
		assertEquals(Score.MAUVAIS, productService.getProductScore("123456"));
		assertEquals(Score.TRES_MAUVAIS, productService.getProductScore("1234567"));
	}

	@Test
	public void testBasketQualities() {
		assertEquals(0, productService.getBasketDefects("toto.toto@toto.com", 0).size());
		assertEquals(0, productService.getBasketQualities("toto.toto@toto.com", 0).size());

		assertEquals(2, productService.getBasketDefects("toto.toto@toto.com", 1).size());
		assertEquals(4, productService.getBasketQualities("toto.toto@toto.com", 1).size());

		// quality and defect aggregation
		assertEquals(5, productService.getBasketDefects("titi.titi@titi.com", 0).size());
		assertEquals(1, productService.getBasketQualities("titi.titi@titi.com", 0).size());

	}

	@Test(expected = ApiException.class)
	@Sql(scripts = { "classpath:clean.sql",
			"classpath:insert.sql" }, config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void testInsertBadProductFromApi() throws ApiException {
		productService.insertProductFromAPI("bla");
		fail();
	}

	@Test
	@Sql(scripts = { "classpath:clean.sql",
			"classpath:insert.sql" }, config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void testInsertProductFromApi() throws ApiException {
		Product product = productService.insertProductFromAPI("3017620429484");
		assertEquals(5, product.getId().intValue());

		product = productService.insertProductFromAPI("1234");
		assertEquals(0, product.getId().intValue());

		product = productService.insertProductFromAPI("3017760314190");
		assertEquals(6, product.getId().intValue());
	}

}
