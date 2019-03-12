package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Product;

public interface BasketProductMapper {

	List<BasketProduct> selectProductsFromBasket(@Param("basket") Basket basket);

	BasketProduct selectProductFromBasket(@Param("basket") Basket basket, @Param("product") Product product);

	void updateProductNumberInBasketProduct(@Param("basketProduct") BasketProduct basketProduct);

	Integer addNewProductInBasketProduct(@Param("basketProduct") BasketProduct basketProduct);

	void removeProductFromBasketProduct(@Param("basketProduct") BasketProduct basketProduct,
			@Param("product") Product product);
}
