package fr.isima.kyou.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketProductMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.ProductMapper;
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	private BasketProductMapper basketProductMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private BasketMapper basketMapper;

	@Override
	public List<BasketProduct> getProductsFromBasket(Basket basket) {
		return basketProductMapper.selectProductsFromBasket(basket);
	}

	@Override
	public BasketProduct addProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		final User user = new User();
		user.setEmail(email);
		final Basket basket = basketMapper.selectBasketFromIdAndUser(basketId, email);

		if (basket == null)
			throw new DaoException("Basket Does not exist");

		Product product = productMapper.getProductFromBarCode(barCode);
		System.out.println(product);
		if (product == null) {
			product = new Product();
			product.setBarCode(barCode);
			final Integer id = productMapper.addProduct(product);
			product.setId(id);
		}

		BasketProduct bp = basketProductMapper.selectProductFromBasket(basket, product);
		if (bp == null) {
			bp = new BasketProduct();
			bp.setProduct(product);
			bp.setBasket(basket);
			bp.setProductNumber(1);
			basketProductMapper.addNewProductInBasketProduct(bp);

		} else {
			bp.setProductNumber(bp.getProductNumber() + 1);
			basketProductMapper.updateProductNumberInBasketProduct(bp);
		}

		return bp;
	}

	@Override
	public BasketProduct removeProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		final Product product = new Product();
		final Basket basket = new Basket();
		final BasketProduct bp = new BasketProduct();

		checkMandatories(product, basket, bp, email, barCode, basketId);
		if (bp.getProductNumber() == 1) {
			basketProductMapper.removeProductFromBasketProduct(bp, product);
		} else {
			bp.setProductNumber(bp.getProductNumber() - 1);
			basketProductMapper.updateProductNumberInBasketProduct(bp);
		}
		return bp;
	}

	@Override
	public void removeAllProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		final Product product = new Product();
		final Basket basket = new Basket();
		final BasketProduct bp = new BasketProduct();

		checkMandatories(product, basket, bp, email, barCode, basketId);
		basketProductMapper.removeProductFromBasketProduct(bp, product);
	}

	private void checkMandatories(Product product, Basket basket, BasketProduct bp, String email, String barCode,
			Integer basketId) throws DaoException {

		product = productMapper.getProductFromBarCode(barCode);
		if (product == null)
			throw new DaoException("Product does not exist");

		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		if (basket == null)
			throw new DaoException("Basket Does not exist");

		bp = basketProductMapper.selectProductFromBasket(basket, product);
		if (bp == null)
			throw new DaoException("Product does not exist in Basket");

	}

}
