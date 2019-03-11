package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;

public interface BasketProductMapper {

	List<BasketProduct> selectProductsFromBasket(@Param("basket") Basket basket);
}
