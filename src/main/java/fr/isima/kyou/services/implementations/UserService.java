package fr.isima.kyou.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.UserMapper;
import fr.isima.kyou.exceptions.DaoException;
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

	@Override
	public Basket selectBasketFromIdAndUser(String email, Integer basketId) {
		return basketMapper.selectBasketFromIdAndUser(basketId, email);
	}

	@Override
	public Basket createBasketFromuser(Integer basketNumber, String email) throws DaoException {
		final User user = userMapper.getUser(email);
		if (user == null) {
			throw new DaoException("user does not exist");
		}

		Basket basket = basketMapper.selectBasketFromIdAndUser(basketNumber, email);
		if (basket != null) {
			throw new DaoException("You already have this basketNumber");
		}

		basket = new Basket();
		basket.setBasketNumber(basketNumber);
		basket.setUser(user);

		basketMapper.createBasketFromuser(basket);

		basket.setId(basketMapper.selectBasketFromIdAndUser(basketNumber, email).getId());
		return basket;
	}
}
