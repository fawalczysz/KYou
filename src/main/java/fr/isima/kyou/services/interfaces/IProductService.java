package fr.isima.kyou.services.interfaces;

import java.util.List;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.exceptions.DaoException;

public interface IProductService {

	List<BasketProduct> getProductsFromBasket(Basket basket);

	Product insertProductFromAPI(String barCode);

	BasketProduct addProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	BasketProduct removeProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	void removeAllProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	List<Nutriment> getProductQualities(String barCode);
	
	List<Nutriment> getProductDefects(String barCode);

	String getProductScore(String barCode);

	List<Nutriment> getBasketQualities(String email, Integer basketId);

	List<Nutriment> getBasketDefects(String email, Integer basketId);

}
