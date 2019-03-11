package fr.isima.kyou.services.interfaces;

import java.util.List;

public interface IUserService {

	void selectAll();

	List<Integer> selectBasketsOfUser(String email);
}
