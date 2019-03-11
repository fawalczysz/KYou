package fr.isima.kyou.dbaccess.mybatis.interfaces;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import fr.isima.kyou.beans.User;

@Component
public interface IUserMapper {

	@Select("SELECT * FROM Users WHERE email = #{userId}")
	User getUser(@Param("userId") String userId);

}
