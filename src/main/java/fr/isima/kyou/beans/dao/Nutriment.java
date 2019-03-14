package fr.isima.kyou.beans.dao;

public class Nutriment {
	private Integer id;
	private Product productId;
	private String  name;
	private Boolean component;
	private Double valueFor100g;


	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	public Product getProduct() { return productId; }
	public void setProduct(Product product) { this.productId = product; }

	public String getName () { return name; }
	public void setName (String name) { this.name = name; }

	public Boolean getComponent () { return component; }
	public void setComponent (Boolean component) { this.component = component; }

	public Double getValuefor100g () { return valueFor100g; }
	public void setValuefor100g (Double valuefor100g) { this.valueFor100g = valuefor100g; }
}
