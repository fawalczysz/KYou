package fr.isima.kyou.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.UserMapper;
import fr.isima.kyou.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BasketMapper basketMapper;

	@Override
	public User getUser(String email) {
		return userMapper.getUser(email);
	}

	@Override
	public Integer addUser(User user) {
		userMapper.addUser(user);
		return user.getId();
	}

	@Override
	public List<Basket> selectBasketsOfUser(User user) {
		final List<Basket> baskets = basketMapper.selectBasketsOfUser(user);
		return baskets;
	}
}
