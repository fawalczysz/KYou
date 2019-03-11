package fr.isima.kyou.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketProductMapper;
import fr.isima.kyou.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	private BasketProductMapper basketProductMapper;

	@Override
	public List<BasketProduct> getProductsFromBasket(Basket basket) {
		return basketProductMapper.selectProductsFromBasket(basket);
	}
}
