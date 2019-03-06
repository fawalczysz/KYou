package fr.isima.kyou.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.isima.kyou.services.interfaces.IProductService;

@RestController
public class ProductController {

	@Autowired
	IProductService productService;

	@GetMapping("/selectall")
	public ResponseEntity selectAll() {
		productService.selectAll();
		return ResponseEntity.ok("test");
	}

	@GetMapping("/resourceGroup/{id}")
	public ResponseEntity getProductValue(@PathVariable String id) {
		return ResponseEntity.ok(productService.getProductValue(id));
	}

}
