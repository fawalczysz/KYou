package fr.isima.kyou.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.isima.kyou.services.interfaces.IUserService;

@RestController
public class UserController {

	@Autowired
	IUserService userservice;

	@GetMapping("/user")
	public ResponseEntity user(@RequestParam String email) {
		return ResponseEntity.ok(userservice.getUser(email));
	}

}