package fr.isima.kyou.services.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.apiaccess.IApiGetter;
import fr.isima.kyou.beans.api.Nutriments;
import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketProductMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.NutrimentMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.ProductMapper;
import fr.isima.kyou.enums.Score;
import fr.isima.kyou.exceptions.ApiException;
import fr.isima.kyou.exceptions.DaoException;
import fr.isima.kyou.services.interfaces.IProductService;

/**
 * Class used to process all calls used for product usage
 *
 *
 */
@Service
public class ProductService implements IProductService {

	@Autowired
	private BasketProductMapper basketProductMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private BasketMapper basketMapper;

	@Autowired
	private NutrimentMapper nutrimentMapper;

	@Autowired
	private IApiGetter ofg;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BasketProduct> getProductsFromBasket(Basket basket) {
		return basketProductMapper.selectProductsFromBasket(basket);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Product insertProductFromAPI(String barCode) throws ApiException {
		Product product = productMapper.getProductFromBarCode(barCode);

		if (product != null)
			return product;

		final Nutriment nutriment = new Nutriment();
		Nutriments nutriments = new Nutriments();

		nutriments = ofg.getData(barCode).getProduct().getNutriments();

		if (nutriments == null) {
			throw new ApiException("Product not found in api");
		}

		product = new Product();
		product.setBarCode(barCode);
		productMapper.addProduct(product);
		product = productMapper.getProductFromBarCode(barCode);

		nutriment.setProduct(product);

		if (nutriments.getEnergy100g() != null) {

			nutriment.setName("energy_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(Double.valueOf(nutriments.getEnergy100g()));
			nutrimentMapper.addNutriment(nutriment);
		}

		if (nutriments.getSaturatedFat100g() != null) {

			nutriment.setName("satured-fat_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(nutriments.getSaturatedFat100g());
			nutrimentMapper.addNutriment(nutriment);
		}

		if (nutriments.getSugars100g() != null) {

			nutriment.setName("sugars_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(nutriments.getSugars100g());
			nutrimentMapper.addNutriment(nutriment);
		}

		if (nutriments.getSalt100g() != null) {
			nutriment.setName("salt_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(nutriments.getSalt100g());
			nutrimentMapper.addNutriment(nutriment);
		}

		if (nutriments.getFiber100g() != null) {

			nutriment.setName("fiber_100g");
			nutriment.setComponent(true);
			nutriment.setValuefor100g(nutriments.getFiber100g());
			nutrimentMapper.addNutriment(nutriment);
		}

		if (nutriments.getProteins100g() != null) {

			nutriment.setName("proteins_100g");
			nutriment.setComponent(true);
			nutriment.setValuefor100g(nutriments.getProteins100g());
			nutrimentMapper.addNutriment(nutriment);
		}

		return product;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasketProduct addProductInBasket(String email, String barCode, Integer basketId)
			throws DaoException, ApiException {
		final User user = new User();
		user.setEmail(email);
		final Basket basket = basketMapper.selectBasketFromIdAndUser(basketId, email);

		if (basket == null)
			throw new DaoException("Basket Does not exist");

		Product product = productMapper.getProductFromBarCode(barCode);

		if (product == null)
			product = insertProductFromAPI(barCode);

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasketProduct removeProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		Product product = null;
		Basket basket = null;
		BasketProduct bp = null;

		product = productMapper.getProductFromBarCode(barCode);
		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		bp = basketProductMapper.selectProductFromBasket(basket, product);

		checkMandatories(product, basket, bp);

		if (bp.getProductNumber() == 1) {
			basketProductMapper.removeProductFromBasketProduct(bp, product);
			bp = null;
		} else {
			bp.setProductNumber(bp.getProductNumber() - 1);
			basketProductMapper.updateProductNumberInBasketProduct(bp);
		}
		return bp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		Product product = new Product();
		Basket basket = new Basket();
		BasketProduct bp = new BasketProduct();

		product = productMapper.getProductFromBarCode(barCode);
		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		bp = basketProductMapper.selectProductFromBasket(basket, product);

		checkMandatories(product, basket, bp);
		basketProductMapper.removeProductFromBasketProduct(bp, product);
	}

	/**
	 * check that all mandatory components have been get correctly from database
	 * 
	 * @param product
	 * @param basket
	 * @param bp
	 * @throws DaoException
	 */
	private void checkMandatories(Product product, Basket basket, BasketProduct bp) throws DaoException {

		if (product == null)
			throw new DaoException("Product does not exist");

		if (basket == null)
			throw new DaoException("Basket Does not exist");

		if (bp == null)
			throw new DaoException("Product does not exist in Basket");

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws DaoException
	 */
	@Override
	public List<Nutriment> getProductQualities(String barCode) throws DaoException {
		Product product = new Product();
		List<Nutriment> nutriments;
		final List<Nutriment> qualities = new ArrayList<>();
		product = productMapper.getProductFromBarCode(barCode);
		if (product == null)
			throw new DaoException("product does not exist");
		nutriments = nutrimentMapper.selectNutrimentsFromProduct(product);

		for (final Nutriment nutriment : nutriments) {
			if (nutrimentIsQuality(nutriment))
				qualities.add(nutriment);
		}

		return qualities;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws DaoException
	 */
	@Override
	public List<Nutriment> getProductDefects(String barCode) throws DaoException {
		Product product = new Product();
		List<Nutriment> nutriments;
		final List<Nutriment> defects = new ArrayList<>();
		product = productMapper.getProductFromBarCode(barCode);
		if (product == null)
			throw new DaoException("product does not exist");
		nutriments = nutrimentMapper.selectNutrimentsFromProduct(product);

		for (final Nutriment nutriment : nutriments) {
			if (nutrimentIsDefect(nutriment))
				defects.add(nutriment);
		}

		return defects;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws DaoException
	 */
	@Override
	public Score getProductScore(String barCode) throws DaoException {
		Product product = new Product();
		List<Nutriment> nutriments;
		Integer score = 0;

		product = productMapper.getProductFromBarCode(barCode);
		if (product == null)
			throw new DaoException("product does not exist");
		nutriments = nutrimentMapper.selectNutrimentsFromProduct(product);

		for (final Nutriment nutriment : nutriments)
			score += getNutritionalScore(nutriment) * (nutriment.getComponent() ? -1 : 1);

		if (score < 0)
			return Score.TRES_BON;
		if (score < 3)
			return Score.BON;
		if (score < 11)
			return Score.MOYEN;
		if (score < 19)
			return Score.MAUVAIS;
		return Score.TRES_MAUVAIS;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Nutriment> getBasketQualities(String email, Integer basketId) {
		Basket basket = new Basket();
		List<BasketProduct> products;
		List<Nutriment> nutriments;
		Nutriment nutTmp;
		final HashMap<String, Nutriment> nutrimentsSummary = new HashMap<>();
		final HashMap<String, Nutriment> basketQualities = new HashMap<>();
		final HashMap<String, Integer> nutrimentsSummaryCount = new HashMap<>();

		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		products = getProductsFromBasket(basket);

		for (final BasketProduct product : products) {
			nutriments = nutrimentMapper.selectNutrimentsFromProduct(product.getProduct());
			for (final Nutriment nutriment : nutriments) {
				nutTmp = nutrimentsSummary.get(nutriment.getName());
				if (nutTmp == null) {
					nutTmp = new Nutriment();
					nutTmp.setName(nutriment.getName());
					nutTmp.setComponent(nutriment.getComponent());
					nutTmp.setValuefor100g(nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(), 1);
				} else {
					nutTmp.setValuefor100g(nutTmp.getValuefor100g() + nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(),
							nutrimentsSummaryCount.get(nutriment.getName()) + 1);
				}

				nutrimentsSummary.put(nutriment.getName(), nutTmp);
			}
		}

		for (final Entry<String, Nutriment> nutrimentEntry : nutrimentsSummary.entrySet()) {
			nutTmp = nutrimentEntry.getValue();
			nutTmp.setValuefor100g(nutTmp.getValuefor100g() / nutrimentsSummaryCount.get(nutTmp.getName()));
			if (nutrimentIsQuality(nutTmp))
				basketQualities.put(nutrimentEntry.getKey(), nutTmp);
		}

		return new ArrayList<>(basketQualities.values());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Nutriment> getBasketDefects(String email, Integer basketId) {
		Basket basket = new Basket();
		List<BasketProduct> products;
		List<Nutriment> nutriments;
		Nutriment nutTmp;
		final HashMap<String, Nutriment> nutrimentsSummary = new HashMap<>();
		final HashMap<String, Nutriment> basketDefects = new HashMap<>();
		final HashMap<String, Integer> nutrimentsSummaryCount = new HashMap<>();

		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		products = getProductsFromBasket(basket);

		for (final BasketProduct product : products) {
			nutriments = nutrimentMapper.selectNutrimentsFromProduct(product.getProduct());
			for (final Nutriment nutriment : nutriments) {
				nutTmp = nutrimentsSummary.get(nutriment.getName());
				if (nutTmp == null) {
					nutTmp = new Nutriment();
					nutTmp.setName(nutriment.getName());
					nutTmp.setComponent(nutriment.getComponent());
					nutTmp.setValuefor100g(nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(), 1);
				} else {
					nutTmp.setValuefor100g(nutTmp.getValuefor100g() + nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(),
							nutrimentsSummaryCount.get(nutriment.getName()) + 1);
				}

				nutrimentsSummary.put(nutriment.getName(), nutTmp);
			}
		}

		for (final Entry<String, Nutriment> nutrimentEntry : nutrimentsSummary.entrySet()) {
			nutTmp = nutrimentEntry.getValue();
			nutTmp.setValuefor100g(nutTmp.getValuefor100g() / nutrimentsSummaryCount.get(nutTmp.getName()));
			if (nutrimentIsDefect(nutTmp))
				basketDefects.put(nutrimentEntry.getKey(), nutTmp);
		}

		return new ArrayList<>(basketDefects.values());
	}

	/**
	 * Function calculating nutritionnal score of a nutriment
	 * 
	 * @param nutriment
	 * @return
	 */
	private Integer getNutritionalScore(Nutriment nutriment) {
		double step = 0;

		switch (nutriment.getName()) {
		case "energy_100g":
			step = 335;
			break;
		case "satured-fat_100g":
			step = 1;
			break;
		case "sugars_100g":
			step = 4.5;
			break;
		case "salt_100g":
			step = 0.09;
			break;
		case "fiber_100g":
			step = 0.9;
			break;
		case "proteins_100g":
			step = 1.6;
			break;
		}
		final Logger logger = LoggerFactory.getLogger(ProductService.class);
		logger.debug(nutriment.getName() + " " + Math.min((int) Math.floor(nutriment.getValuefor100g() / step), 10));
		return Math.min((int) Math.floor(nutriment.getValuefor100g() / step), nutriment.getComponent() ? 5 : 10);
	}

	/**
	 * Returns true if the given nutriment is considered as a quality
	 * 
	 * @param nutriment
	 * @return
	 */
	private Boolean nutrimentIsQuality(Nutriment nutriment) {
		return (nutriment.getComponent() && getNutritionalScore(nutriment) >= 2)
				|| (!nutriment.getComponent() && getNutritionalScore(nutriment) <= 3);
	}

	/**
	 * Returns true if the given nutriment is considered as a defect
	 * 
	 * @param nutriment
	 * @return
	 */
	private Boolean nutrimentIsDefect(Nutriment nutriment) {
		return (nutriment.getComponent() && getNutritionalScore(nutriment) <= 0)
				|| (!nutriment.getComponent() && getNutritionalScore(nutriment) >= 7);
	}

}
