package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Product;

/**
 * MyBatis mapper for basketProducts, Sql requests defined in resources inside
 * BasketProductsMapper.xml
 * 
 *
 */
public interface BasketProductMapper {
	/**
	 * returns all products using specific basket
	 * 
	 * @param basket
	 * @return
	 */
	List<BasketProduct> selectProductsFromBasket(@Param("basket") Basket basket);

	/**
	 * select product inside basket, links to product and number of products of this
	 * type inside of this basket
	 * 
	 * @param basket
	 * @param product
	 * @return
	 */
	BasketProduct selectProductFromBasket(@Param("basket") Basket basket, @Param("product") Product product);

	/**
	 * Updates the number of products in a basket for a specific product
	 * 
	 * @param basketProduct
	 */
	void updateProductNumberInBasketProduct(@Param("basketProduct") BasketProduct basketProduct);

	/**
	 * add new product, starting from 1 product when created
	 * 
	 * @param basketProduct
	 * @return
	 */
	Integer addNewProductInBasketProduct(@Param("basketProduct") BasketProduct basketProduct);

	/**
	 * deletes product in basket
	 * 
	 * @param basketProduct
	 * @param product
	 */
	void removeProductFromBasketProduct(@Param("basketProduct") BasketProduct basketProduct,
			@Param("product") Product product);
}
