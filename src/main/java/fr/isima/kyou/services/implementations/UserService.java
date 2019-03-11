package fr.isima.kyou.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.User;
import fr.isima.kyou.dbaccess.mybatis.dao.UserMapper;
import fr.isima.kyou.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUser(String email) {
		return userMapper.getUser(email);
	}

	@Override
	public Integer addUser(User user) {
		userMapper.addUser(user);
		return user.getId();
	}
}
