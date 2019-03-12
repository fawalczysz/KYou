package fr.isima.kyou.services.interfaces;

import java.util.List;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.exceptions.DaoException;

public interface IProductService {

	List<BasketProduct> getProductsFromBasket(Basket basket);

	BasketProduct addProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	BasketProduct removeProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

	void removeAllProductInBasket(String email, String barCode, Integer basketId) throws DaoException;

}
