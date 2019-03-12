package fr.isima.kyou.dbaccess.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import fr.isima.kyou.beans.dao.Product;

public interface ProductMapper {

	Product getProductFromBarCode(@Param("barCode") String barCode);

	Integer addProduct(@Param("product") Product product);
}
