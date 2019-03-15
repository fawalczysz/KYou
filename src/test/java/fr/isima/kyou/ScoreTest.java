package fr.isima.kyou;

import static org.junit.Assert.assertEquals;

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
	@Sql(scripts = { "classpath:clean.sql",
			"classpath:insert.sql" }, config = @SqlConfig(dataSource = "testDataSource", transactionManager = "testTransactionManager"))
	public void testNutritionnalQualities() {
		final List<Nutriment> defects = productService.getProductDefects("1234");
		final List<Nutriment> qualities = productService.getProductQualities("1234");
		assertEquals(4, defects.size());
		assertEquals(2, qualities.size());
	}

}
