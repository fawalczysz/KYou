package fr.isima.kyou.dbaccess.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;

public interface NutrimentMapper {
	
	List<Nutriment> selectNutrimentsFromProduct(@Param("product") Product product);
	
	Integer addNutriment(@Param("nutriment") Nutriment nutriment);
}
