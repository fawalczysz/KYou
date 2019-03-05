package fr.isima.kyou.apiaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.dbaccess.interfaces.IBasketProducts;
import fr.isima.kyou.dbaccess.interfaces.IBaskets;
import fr.isima.kyou.dbaccess.interfaces.IProducts;
import fr.isima.kyou.dbaccess.interfaces.IUsers;

@Service
public class Test implements ITest {

	@Autowired
	IUsers users;

	@Autowired
	IProducts products;

	@Autowired
	IBaskets baskets;

	@Autowired
	IBasketProducts basketProducts;

	public void test() {
		users.selectAll();
		products.selectAll();
		baskets.selectAll();
		basketProducts.selectAll();
	}
}
