package fr.isima.kyou.dbaccess.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.dao.User;

/**
 * MyBatis mapper for users, Sql requests defined in resources inside
 * UserMapper.xml
 * 
 *
 */
@Component
public interface UserMapper {
	/**
	 * get user from email
	 * 
	 * @param email
	 * @return
	 */
	User getUser(@Param("email") String email);

	/**
	 * add new User
	 * 
	 * @param user
	 */
	void addUser(@Param("user") User user);
}
