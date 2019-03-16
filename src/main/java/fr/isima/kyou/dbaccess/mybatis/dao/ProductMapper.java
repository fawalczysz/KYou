package fr.isima.kyou.dbaccess.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Product;

/**
 * MyBatis mapper for products, Sql requests defined in resources inside
 * ProductMapper.xml
 * 
 *
 */
public interface ProductMapper {
	/**
	 * returns product store using barCode
	 * 
	 * @param barCode
	 * @return
	 */
	Product getProductFromBarCode(@Param("barCode") String barCode);

	/**
	 * add new product in database
	 * 
	 * @param product
	 * @return
	 */
	Integer addProduct(@Param("product") Product product);
}
