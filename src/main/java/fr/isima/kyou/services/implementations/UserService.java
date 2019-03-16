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

/**
 * Class used to process all calls used for user and basket usage
 *
 *
 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer addUser(User user) throws DaoException {
		if (userMapper.getUser(user.getEmail()) != null)
			throw new DaoException("email already exists");
		userMapper.addUser(user);
		return user.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Basket> selectBasketsOfUser(User user) {
		return basketMapper.selectBasketsOfUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Basket selectBasketFromIdAndUser(String email, Integer basketId) {
		return basketMapper.selectBasketFromIdAndUser(basketId, email);
	}

	/**
	 * {@inheritDoc}
	 */
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
