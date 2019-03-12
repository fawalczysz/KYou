package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;

@Component
public interface BasketMapper {
	/**
	 * 
	 * 
	 * @param user
	 * @return
	 */
	List<Basket> selectBasketsOfUser(@Param("user") User user);

	Basket selectBasketFromIdAndUser(@Param("basketNumber") Integer id, @Param("email") String email);

	Basket selectBasketFromId(@Param("basketId") Integer id);

	Integer createBasketFromuser(@Param("basket") Basket basket);

}
