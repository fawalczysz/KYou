package fr.isima.kyou.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.Basket;
import fr.isima.kyou.dbaccess.interfaces.IBaskets;
import fr.isima.kyou.dbaccess.interfaces.IUsers;
import fr.isima.kyou.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	IUsers usersDAO;

	@Autowired
	IBaskets basketsDAO;

	@Override
	public void selectAll() {
		usersDAO.selectAll();
	}

	@Override
	public List<Integer> selectBasketsOfUser(String email) {
		final List<Basket> baskets = basketsDAO.selectBasketsOfUser(email);
		final List<Integer> basketNumbers = new ArrayList<>();
		baskets.forEach(basket -> basketNumbers.add(basket.getNumber()));

		return basketNumbers;
	}

}
