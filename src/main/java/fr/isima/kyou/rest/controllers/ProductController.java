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
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;
import fr.isima.kyou.services.interfaces.IUserService;

@RestController
public class ProductController {

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;
	
	@GetMapping("/createProduct")
	public ResponseEntity<Product> createProdcut(@RequestParam String barCode) {
		return ResponseEntity.ok(productService.insertProductFromAPI(barCode));
	}

	@GetMapping("/products")
	public ResponseEntity<List<BasketProduct>> getProductsFromBasket(@RequestParam Integer basketId,
			@RequestParam String email) {
		final Basket basket = userService.selectBasketFromIdAndUser(email, basketId);
		final List<BasketProduct> products = productService.getProductsFromBasket(basket);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/addProduct")
	public ResponseEntity addProductInBasket(@RequestParam Integer basketId, @RequestParam String email,
			@RequestParam String barCode) {
		try {
			return ResponseEntity.ok(productService.addProductInBasket(email, barCode, basketId));
		} catch (final DaoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
		}
	}

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
	
	@GetMapping("/qualities")
	public ResponseEntity<List<Nutriment>> getProductQualities(@RequestParam String barCode)
	{
		return ResponseEntity.ok(productService.getProductQualities(barCode));
	}
	
	@GetMapping("/defects")
	public ResponseEntity<List<Nutriment>> getProductDefects(@RequestParam String barCode)
	{
		return ResponseEntity.ok(productService.getProductDefects(barCode));
	}
	
	@GetMapping("/score")
	public ResponseEntity<String> getProductScore(@RequestParam String barCode)
	{
		return ResponseEntity.ok(productService.getProductScore(barCode));
	}
	
	@GetMapping("/basketQualities")
	public ResponseEntity<List<Nutriment>> getBasketQualities(@RequestParam String email, @RequestParam Integer basketId)
	{
		return ResponseEntity.ok(productService.getBasketQualities(email, basketId));
	}
	
	@GetMapping("/basketDefects")
	public ResponseEntity<List<Nutriment>> getBasketDefects(@RequestParam String email, @RequestParam Integer basketId)
	{
		return ResponseEntity.ok(productService.getBasketDefects(email, basketId));
	}

}
