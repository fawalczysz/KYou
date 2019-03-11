package fr.isima.kyou.dbaccess.interfaces;

import java.util.List;

import fr.isima.kyou.beans.Basket;

public interface IBaskets {

	void selectAll();

	List<Basket> selectBasketsOfUser(String email);

	List<Basket> addProductInBasket(String email, Integer basketNumber, String barCode);
}
