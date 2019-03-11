package fr.isima.kyou.services.implementations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.apiaccess.OpenFoodGetter;
import fr.isima.kyou.beans.Root;
import fr.isima.kyou.dbaccess.interfaces.IBasketProducts;
import fr.isima.kyou.dbaccess.interfaces.IProducts;
import fr.isima.kyou.dbaccess.interfaces.IUsers;
import fr.isima.kyou.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	IProducts products;

	@Autowired
	IBasketProducts basketProduct;

	@Autowired
	IUsers users;

	@Override
	public void selectAll() {
		users.selectAll();
		products.selectAll();
		basketProduct.selectAll();
	}

	@Override
	public Root getProductValue(@PathVariable String id) {
		Root result = new Root();
		System.out.println("test");
		try {
			result = OpenFoodGetter.getInstance().getData(id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | JSONException
				| UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
