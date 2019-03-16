package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;

/**
 * MyBatis mapper for nutriments, Sql requests defined in resources inside
 * NutrimentsMapper.xml
 * 
 *
 */
public interface NutrimentMapper {
	/**
	 * select all stored nutriments for a product
	 * 
	 * @param product
	 * @return
	 */
	List<Nutriment> selectNutrimentsFromProduct(@Param("product") Product product);

	/**
	 * add new nutriment in database
	 * 
	 * @param nutriment
	 * @return
	 */
	Integer addNutriment(@Param("nutriment") Nutriment nutriment);
}
