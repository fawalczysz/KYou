package fr.isima.kyou.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.enums.Score;
import fr.isima.kyou.exceptions.ApiException;
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;
import fr.isima.kyou.services.interfaces.IUserService;

@RestController
public class ProductController {

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;

	/**
	 * add new product in database using open food api. This product will not be
	 * linked to any basket, its nutriments will however be recovered
	 * 
	 * @param barCode
	 * @return
	 * @throws ApiException
	 */
	@GetMapping("/createProduct")
	public ResponseEntity<Product> createProduct(@RequestParam String barCode) throws ApiException {
		return ResponseEntity.ok(productService.insertProductFromAPI(barCode));
	}

	/**
	 * get all products inside of a basket for a user and its basketNumber
	 * 
	 * @param basketId
	 * @param email
	 * @return
	 */
	@GetMapping("/products")
	public ResponseEntity<List<BasketProduct>> getProductsFromBasket(@RequestParam Integer basketId,
			@RequestParam String email) {
		final Basket basket = userService.selectBasketFromIdAndUser(email, basketId);
		final List<BasketProduct> products = productService.getProductsFromBasket(basket);
		return ResponseEntity.ok(products);
	}

	/**
	 * add a new product in basket for a user
	 * 
	 * @param basketId
	 * @param email
	 * @param barCode
	 * @return
	 * @throws ApiException
	 */
	@GetMapping("/addProduct")
	public ResponseEntity addProductInBasket(@RequestParam Integer basketId, @RequestParam String email,
			@RequestParam String barCode) throws ApiException {
		try {
			return ResponseEntity.ok(productService.addProductInBasket(email, barCode, basketId));
		} catch (final DaoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
		}
	}

	/**
	 * removes only one item of a product in a basket
	 * 
	 * @param basketId
	 * @param email
	 * @param barCode
	 * @return
	 */
	@GetMapping("/remove")
	public ResponseEntity<String> removeProductInBasket(@RequestParam Integer basketId, @RequestParam String email,
			@RequestParam String barCode) {
		try {
			productService.removeProductInBasket(email, barCode, basketId);
			return ResponseEntity.ok("Product removed");
		} catch (final DaoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
		}
	}

	/**
	 * removes all items of a product in a basket
	 * 
	 * @param basketId
	 * @param email
	 * @param barCode
	 * @return
	 */
	@GetMapping("/removeAll")
	public ResponseEntity<String> removeAllProductInBasket(@RequestParam Integer basketId, @RequestParam String email,
			@RequestParam String barCode) {
		try {
			productService.removeAllProductInBasket(email, barCode, basketId);
			return ResponseEntity.ok("Product removed");
		} catch (final DaoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
		}
	}

	/**
	 * returns qualities associated to product
	 * 
	 * @param barCode
	 * @return
	 * @throws DaoException
	 */
	@GetMapping("/qualities")
	public ResponseEntity<List<Nutriment>> getProductQualities(@RequestParam String barCode) throws DaoException {
		return ResponseEntity.ok(productService.getProductQualities(barCode));
	}

	/**
	 * returns defects associated to product
	 * 
	 * @param barCode
	 * @return
	 * @throws DaoException
	 */
	@GetMapping("/defects")
	public ResponseEntity<List<Nutriment>> getProductDefects(@RequestParam String barCode) throws DaoException {
		return ResponseEntity.ok(productService.getProductDefects(barCode));
	}

	/**
	 * return product nutritionnal score
	 * 
	 * @param barCode
	 * @return
	 * @throws DaoException
	 */
	@GetMapping("/score")
	public ResponseEntity<Score> getProductScore(@RequestParam String barCode) throws DaoException {
		return ResponseEntity.ok(productService.getProductScore(barCode));
	}

	/**
	 * Returns the qualities associated to a basket. All nutriments in basket are
	 * summed to get basket quality average
	 * 
	 * @param email
	 * @param basketId
	 * @return
	 */
	@GetMapping("/basketQualities")
	public ResponseEntity<List<Nutriment>> getBasketQualities(@RequestParam String email,
			@RequestParam Integer basketId) {
		return ResponseEntity.ok(productService.getBasketQualities(email, basketId));
	}

	/**
	 * Returns the defects associated to a basket. All nutriments in basket are
	 * summed to get basket quality average
	 * 
	 * @param email
	 * @param basketId
	 * @return
	 */
	@GetMapping("/basketDefects")
	public ResponseEntity<List<Nutriment>> getBasketDefects(@RequestParam String email,
			@RequestParam Integer basketId) {
		return ResponseEntity.ok(productService.getBasketDefects(email, basketId));
	}

}
