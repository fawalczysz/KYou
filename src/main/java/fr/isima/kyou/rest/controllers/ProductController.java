package fr.isima.kyou.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import fr.isima.kyou.services.interfaces.IProductService;

@RestController
public class ProductController {

	@Autowired
	IProductService productService;

}
