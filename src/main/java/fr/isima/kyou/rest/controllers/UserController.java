package fr.isima.kyou.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IUserService;

@RestController
public class UserController {

	@Autowired
	IUserService userservice;

	/**
	 * returns user from email
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/users")
	public ResponseEntity<User> user(@RequestParam String email) {
		return ResponseEntity.ok(userservice.getUser(email));
	}

	/**
	 * returns baskets linked to user from email
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/baskets")
	public ResponseEntity<List<Basket>> getBasketsOfUser(@RequestParam String email) {
		final User user = userservice.getUser(email);
		return ResponseEntity.ok(userservice.selectBasketsOfUser(user));
	}

	/**
	 * creates new basket for user email and new basketNumber for a user
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/createBasket")
	public ResponseEntity createBasket(@RequestParam String email, @RequestParam Integer basketId) {

		try {
			return ResponseEntity.ok(userservice.createBasketFromuser(basketId, email));
		} catch (final DaoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}

	/**
	 * creates new User
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @return
	 * @throws DaoException
	 */
	@GetMapping("/createUser")
	public ResponseEntity<Integer> addUser(@RequestParam String firstname, @RequestParam String lastname,
			@RequestParam String email) throws DaoException {
		final User user = new User(firstname, lastname, email);
		return ResponseEntity.ok(userservice.addUser(user));
	}

}
