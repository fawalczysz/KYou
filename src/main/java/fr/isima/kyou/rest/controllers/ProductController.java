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
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;
import fr.isima.kyou.services.interfaces.IUserService;

@RestController
public class ProductController {

	@Autowired
	IProductService productService;

	@Autowired
	IUserService userService;

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

}
