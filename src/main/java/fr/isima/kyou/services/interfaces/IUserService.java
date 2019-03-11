package fr.isima.kyou.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.User;

@Service
public interface IUserService {

	User getUser(String email);

	Integer addUser(User user);

	List<Basket> selectBasketsOfUser(User user);
}
