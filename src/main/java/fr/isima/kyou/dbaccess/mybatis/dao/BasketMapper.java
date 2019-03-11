package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.api.Product;
import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;

@Component
public interface BasketMapper {

	List<Basket> selectBasketsOfUser(@Param("user") User user);

	Integer addProductInBasket(@Param("product") Product product, @Param("basket") Basket basket);

}
