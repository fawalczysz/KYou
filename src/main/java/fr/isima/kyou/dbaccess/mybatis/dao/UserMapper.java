package fr.isima.kyou.dbaccess.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.dao.User;

@Component
public interface UserMapper {

	User getUser(@Param("userId") String userId);

	void addUser(@Param("user") User user);
}
