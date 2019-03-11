package fr.isima.kyou.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.User;
import fr.isima.kyou.dbaccess.mybatis.interfaces.IUserMapper;
import fr.isima.kyou.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserMapper userMapper;

	@Override
	public User getUser(String email) {
		return userMapper.getUser(email);
	}

}
