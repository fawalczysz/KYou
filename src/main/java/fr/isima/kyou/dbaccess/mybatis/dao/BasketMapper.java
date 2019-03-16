package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;

/**
 * MyBatis mapper for baskets, Sql requests defined in resources inside
 * BasketMapper.xml
 * 
 *
 */
@Component
public interface BasketMapper {
	/**
	 * returns all baskets for user
	 * 
	 * @param user
	 * @return
	 */
	List<Basket> selectBasketsOfUser(@Param("user") User user);

	/**
	 * select specific basket fom user and basketNumber
	 * 
	 * @param id
	 * @param email
	 * @return
	 */
	Basket selectBasketFromIdAndUser(@Param("basketNumber") Integer id, @Param("email") String email);

	/**
	 * returns baket using basket Id
	 * 
	 * @param id
	 * @return
	 */
	Basket selectBasketFromId(@Param("basketId") Integer id);

	/**
	 * create new basket for a given user
	 * 
	 * @param basket
	 * @return
	 */
	Integer createBasketFromuser(@Param("basket") Basket basket);

}
