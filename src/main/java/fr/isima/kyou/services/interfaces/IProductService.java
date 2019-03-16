package fr.isima.kyou.services.interfaces;

import java.util.List;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.enums.Score;
import fr.isima.kyou.exceptions.ApiException;
import fr.isima.kyou.exceptions.DaoException;

public interface IProductService {

	/**
	 * get all products from specified basket. Basket id is used in order to get
	 * those items
	 * 
	 * @param basket
	 * @return
	 */
	List<BasketProduct> getProductsFromBasket(Basket basket);

	/**
	 * Insert product in database using API using given barCode. If this product is
	 * not found in api, it returns a DAO Exception if the product is not already
	 * inserted in database
	 * 
	 * @param barCode
	 * @return
	 * @throws ApiException
	 */
	Product insertProductFromAPI(String barCode) throws ApiException;

	/**
	 * Add product using a given barCode for a User and a specific basketNumber
	 * 
	 * @param email
	 * @param barCode
	 * @param basketId
	 * @return
	 * @throws DaoException
	 * @throws ApiException
	 */
	BasketProduct addProductInBasket(String email, String barCode, Integer basketId) throws DaoException, ApiException;

	/**
	 * Delete one product item using a given barCode for a User and a specific
	 * basketNumber if there is multiple products into the same basket, only one
	 * will be removed call
	 * {@link #fr.isima.kyou.services.interfaces.removeAllProductInBasket
	 * removeAllProductInBasket} to remove all of them
	 * 
	 * @param email
	 * @param barCode
	 * @param basketId
	 * @return
	 * @throws DaoException
	 */
	BasketProduct removeProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	/**
	 * Delete all product items using a given barCode for a User and a specific
	 * basketNumber if there is multiple products into the same basket, it will
	 * remove all of them.
	 * {@link #fr.isima.kyou.services.interfaces.removeProductInBasket
	 * removeProductInBasket} to remove only one
	 * 
	 * @param email
	 * @param barCode
	 * @param basketId
	 * @throws DaoException
	 */
	void removeAllProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	/**
	 * Return all the qualities associated to a specific product
	 * 
	 * @param barCode
	 * @return
	 * @throws DaoException
	 */
	List<Nutriment> getProductQualities(String barCode) throws DaoException;

	/**
	 * Return all the defaults associated to a specific product
	 * 
	 * @param barCode
	 * @return
	 * @throws DaoException
	 */
	List<Nutriment> getProductDefects(String barCode) throws DaoException;

	/**
	 * returns the nutritionnal score for a product
	 * 
	 * @param barCode
	 * @return
	 * @throws DaoException
	 */
	Score getProductScore(String barCode) throws DaoException;

	/**
	 * Returns the qualities associated to a basket. All nutriments in basket are
	 * summed to get basket quality average
	 * 
	 * @param email
	 * @param basketId
	 * @return
	 */
	List<Nutriment> getBasketQualities(String email, Integer basketId);

	/**
	 * Returns the defects associated to a basket. All nutriments in basket are
	 * summed to get basket quality average
	 * 
	 * @param email
	 * @param basketId
	 * @return
	 */
	List<Nutriment> getBasketDefects(String email, Integer basketId);

}
