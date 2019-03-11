package fr.isima.kyou.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
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
		final List<Basket> baskets = userService.selectBasketsOfUser(userService.getUser(email));
		final List<BasketProduct> products = productService.getProductsFromBasket(baskets.get(basketId));
		return ResponseEntity.ok(products);
	}

}
