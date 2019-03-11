package fr.isima.kyou.beans.dao;

public class Basket {

	private Integer id;
	private User userId;
	private Integer basketNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return userId;
	}

	public void setUser(User user) {
		this.userId = user;
	}

	public Integer getBasketNumber() {
		return basketNumber;
	}

	public void setBasketNumber(Integer basketNumber) {
		this.basketNumber = basketNumber;
	}

}
