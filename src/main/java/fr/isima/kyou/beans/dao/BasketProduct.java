package fr.isima.kyou.beans.dao;

public class BasketProduct {
	private Integer id;
	private Basket basketId;
	private Product productId;
	private Integer productNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Basket getBasket() {
		return basketId;
	}

	public void setBasket(Basket basket) {
		this.basketId = basket;
	}

	public Product getProduct() {
		return productId;
	}

	public void setProduct(Product product) {
		this.productId = product;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
}
