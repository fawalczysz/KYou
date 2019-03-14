package fr.isima.kyou.services.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.dao.Basket;
import fr.isima.kyou.beans.dao.BasketProduct;
import fr.isima.kyou.beans.dao.Nutriment;
import fr.isima.kyou.beans.dao.Product;
import fr.isima.kyou.beans.dao.User;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.BasketProductMapper;
import fr.isima.kyou.dbaccess.mybatis.dao.NutrimentMapper;
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

	@Autowired
	private NutrimentMapper nutrimentMapper;

	@Override
	public List<BasketProduct> getProductsFromBasket(Basket basket) {
		return basketProductMapper.selectProductsFromBasket(basket);
	}
	
	@Override
	public void createProduct(String barCode, Double energyFor100g, Double saturedFatFor100g, Double sugarsFor100g,
			Double saltFor100g, Double fiberFor100g, Double proteinsFor100g) {
		
		Product product = productMapper.getProductFromBarCode(barCode);
		Nutriment nutriment	 = new Nutriment();
		
		if (product == null) {
			product = new Product();
			product.setBarCode(barCode);
			final Integer id = productMapper.addProduct(product);
			product.setId(id);
		}

		nutriment.setProduct(product);
		
		if (energyFor100g != null) {
			nutriment.setName("energy_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(energyFor100g);
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(saturedFatFor100g != null) {
			nutriment.setName("satured-fat_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(saturedFatFor100g);
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(sugarsFor100g != null) {
			nutriment.setName("sugars_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(sugarsFor100g);
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(saltFor100g != null) {
			nutriment.setName("salt_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(saltFor100g);
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(fiberFor100g != null) {
			nutriment.setName("fiber_100g");
			nutriment.setComponent(true);
			nutriment.setValuefor100g(fiberFor100g);
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(proteinsFor100g != null) {
			nutriment.setName("proteins_100g");
			nutriment.setComponent(true);
			nutriment.setValuefor100g(proteinsFor100g);
			nutrimentMapper.addNutriment(nutriment);
		}
	}

	@Override
	public BasketProduct addProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		final User user = new User();
		user.setEmail(email);
		final Basket basket = basketMapper.selectBasketFromIdAndUser(basketId, email);

		if (basket == null)
			throw new DaoException("Basket Does not exist");

		Product product = productMapper.getProductFromBarCode(barCode);
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
		Product product = null;
		Basket basket = null;
		BasketProduct bp = null;

		product = productMapper.getProductFromBarCode(barCode);
		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		bp = basketProductMapper.selectProductFromBasket(basket, product);

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
		Product product = new Product();
		Basket basket = new Basket();
		BasketProduct bp = new BasketProduct();

		product = productMapper.getProductFromBarCode(barCode);
		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		bp = basketProductMapper.selectProductFromBasket(basket, product);

		checkMandatories(product, basket, bp, email, barCode, basketId);
		basketProductMapper.removeProductFromBasketProduct(bp, product);
	}

	private void checkMandatories(Product product, Basket basket, BasketProduct bp, String email, String barCode,
			Integer basketId) throws DaoException {

		if (product == null)
			throw new DaoException("Product does not exist");

		if (basket == null)
			throw new DaoException("Basket Does not exist");

		if (bp == null)
			throw new DaoException("Product does not exist in Basket");

	}

	@Override
	public List<Nutriment> getProductQualities(String barCode) {
		Product product = new Product();
		List<Nutriment> nutriments;
		List<Nutriment> qualities = new ArrayList<>();
		product = productMapper.getProductFromBarCode(barCode);
		nutriments = nutrimentMapper.selectNutrimentsFromProduct(product);
		
		for(Nutriment nutriment : nutriments) {
			if(nutrimentIsQuality(nutriment)) qualities.add(nutriment);
		}
		
		return qualities;
	}

	@Override
	public List<Nutriment> getProductDefects(String barCode) {
		Product product = new Product();
		List<Nutriment> nutriments;
		List<Nutriment> defects = new ArrayList<>();
		product = productMapper.getProductFromBarCode(barCode);
		nutriments = nutrimentMapper.selectNutrimentsFromProduct(product);
		
		for(Nutriment nutriment : nutriments) {
			if(nutrimentIsDefect(nutriment)) defects.add(nutriment);
		}
		
		return defects;
	}

	@Override
	public String getProductScore(String barCode) {
		Product product = new Product();
		List<Nutriment> nutriments;
		Integer score = 0;
		
		product = productMapper.getProductFromBarCode(barCode);
		nutriments = nutrimentMapper.selectNutrimentsFromProduct(product);
		
		for(Nutriment nutriment : nutriments) score += getNutritionalScore(nutriment) * (nutriment.getComponent() ? -1 : 1);
		
		if (score < 0)  return "Très bon";
		if (score < 3)  return "Bon";
		if (score < 11) return "Moyen";
		if (score < 19) return "Mauvais";
		return "Très mauvais";
		
	}

	@Override
	public List<Nutriment> getBasketQualities(String email, Integer basketId) {
		Basket basket = new Basket();
		List<BasketProduct> products;
		List<Nutriment> nutriments;
		Nutriment nutTmp;
		HashMap<String, Nutriment> nutrimentsSummary = new HashMap<>();
		HashMap<String, Nutriment> basketQualities = new HashMap<>();
		HashMap<String, Integer> nutrimentsSummaryCount = new HashMap<>();
		
		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		products = getProductsFromBasket(basket);
		
		for(BasketProduct product : products) {
			nutriments = nutrimentMapper.selectNutrimentsFromProduct(product.getProduct());
			for (Nutriment nutriment : nutriments)
			{
				nutTmp = nutrimentsSummary.get(nutriment.getName());
				if (nutTmp == null) {
					nutTmp = new Nutriment();
					nutTmp.setName(nutriment.getName());
					nutTmp.setComponent(nutriment.getComponent());
					nutTmp.setValuefor100g(nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(), 1);
				}
				else {
					nutTmp.setValuefor100g(nutTmp.getValuefor100g() + nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(), nutrimentsSummaryCount.get(nutriment.getName()) + 1);
				}
				
				nutrimentsSummary.put(nutriment.getName(), nutTmp);
			}
		}
		
		for (Entry<String, Nutriment> nutrimentEntry : nutrimentsSummary.entrySet()) {
			nutTmp = nutrimentEntry.getValue();
			nutTmp.setValuefor100g(nutTmp.getValuefor100g() / nutrimentsSummaryCount.get(nutTmp.getName()));
			if (nutrimentIsQuality(nutTmp)) basketQualities.put(nutrimentEntry.getKey(), nutTmp);
		}
		
		return new ArrayList<>(basketQualities.values());
	}

	@Override
	public List<Nutriment> getBasketDefects(String email, Integer basketId) {
		Basket basket = new Basket();
		List<BasketProduct> products;
		List<Nutriment> nutriments;
		Nutriment nutTmp;
		HashMap<String, Nutriment> nutrimentsSummary = new HashMap<>();
		HashMap<String, Nutriment> basketDefects = new HashMap<>();
		HashMap<String, Integer> nutrimentsSummaryCount = new HashMap<>();
		
		basket = basketMapper.selectBasketFromIdAndUser(basketId, email);
		products = getProductsFromBasket(basket);
		
		for(BasketProduct product : products) {
			nutriments = nutrimentMapper.selectNutrimentsFromProduct(product.getProduct());
			for (Nutriment nutriment : nutriments)
			{
				nutTmp = nutrimentsSummary.get(nutriment.getName());
				if (nutTmp == null) {
					nutTmp = new Nutriment();
					nutTmp.setName(nutriment.getName());
					nutTmp.setComponent(nutriment.getComponent());
					nutTmp.setValuefor100g(nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(), 1);
				}
				else {
					nutTmp.setValuefor100g(nutTmp.getValuefor100g() + nutriment.getValuefor100g());
					nutrimentsSummaryCount.put(nutriment.getName(), nutrimentsSummaryCount.get(nutriment.getName()) + 1);
				}
				
				nutrimentsSummary.put(nutriment.getName(), nutTmp);
			}
		}
		
		for (Entry<String, Nutriment> nutrimentEntry : nutrimentsSummary.entrySet()) {
			nutTmp = nutrimentEntry.getValue();
			nutTmp.setValuefor100g(nutTmp.getValuefor100g() / nutrimentsSummaryCount.get(nutTmp.getName()));
			if (nutrimentIsDefect(nutTmp)) basketDefects.put(nutrimentEntry.getKey(), nutTmp);
		}
		
		return new ArrayList<>(basketDefects.values());
	}

	private Integer getNutritionalScore(Nutriment nutriment) {
		double step = 0;
		
		switch (nutriment.getName())
		{
			case "energy_100g" :	  step = 335; break;
			case "satured-fat_100g" : step = 1;   break;
			case "sugars_100g" :	  step = 4.5; break;
			case "salt_100g" :		  step = 0.09;  break;
			case "fiber_100g" :		  step = 0.9; break;
			case "proteins_100g" :	  step = 1.6; break;
		}
		Logger logger = LoggerFactory.getLogger(ProductService.class);
		logger.debug(nutriment.getName() + " " + Math.min((int)Math.floor(nutriment.getValuefor100g() / step), 10));
		return Math.min((int)Math.floor(nutriment.getValuefor100g() / step), nutriment.getComponent() ? 5 : 10);
	}
	
	private Boolean nutrimentIsQuality(Nutriment nutriment) {
		return (nutriment.getComponent() && getNutritionalScore(nutriment) >= 2) ||
			  (!nutriment.getComponent() && getNutritionalScore(nutriment) <= 3);
	}
	
	private Boolean nutrimentIsDefect(Nutriment nutriment) {
		return (nutriment.getComponent() && getNutritionalScore(nutriment) <= 0) ||
			  (!nutriment.getComponent() && getNutritionalScore(nutriment) >= 7);
	}

}
