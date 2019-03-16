package fr.isima.kyou.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.exceptions.DaoException;

@Service
public interface IUserService {

	/**
	 * Get user Object based on email
	 * 
	 * @param email
	 * @return
	 */
	User getUser(String email);

	/**
	 * Add a new user in database
	 * 
	 * @param user
	 * @return
	 * @throws DaoException
	 *             if email is not unique
	 */
	Integer addUser(User user) throws DaoException;

	/**
	 * returns all baskets of a specific user
	 * 
	 * @param user
	 * @return
	 */
	List<Basket> selectBasketsOfUser(User user);

	/**
	 * returns a basket for a user given a specific basketNumber
	 * 
	 * @param email
	 * @param basketId
	 * @return
	 */
	Basket selectBasketFromIdAndUser(String email, Integer basketId);

	/**
	 * Creates new Basket for a User using a given email and basketNumber.
	 * 
	 * @param basketNumber
	 * @param email
	 * @return
	 * @throws DaoException
	 *             if this user already have this basketNumber
	 */
	Basket createBasketFromuser(Integer basketNumber, String email) throws DaoException;
}
