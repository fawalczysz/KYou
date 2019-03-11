package fr.isima.kyou.services.interfaces;

import java.util.List;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;

public interface IProductService {

	List<BasketProduct> getProductsFromBasket(Basket basket);

}
