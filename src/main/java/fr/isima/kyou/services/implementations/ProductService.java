package fr.isima.kyou.services.implementations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.apiaccess.OpenFoodGetter;
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
	public Product insertProductFromAPI(String barCode) {
		Product			product		= productMapper.getProductFromBarCode(barCode);
		Nutriment		nutriment	= new Nutriment();
		Nutriments		nutriments	= new Nutriments();
		OpenFoodGetter	ofg			= OpenFoodGetter.getInstance();
		
		try { nutriments = ofg.getData(barCode).getProduct().getNutriments(); }
		catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | JSONException | UnirestException e) { e.printStackTrace(); }
		
		if (product != null) return product;
		
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
		
		if(nutriments.getSaturatedFat100g() != null) {
			nutriment.setName("satured-fat_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(nutriments.getSaturatedFat100g());
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(nutriments.getSugars100g() != null) {
			nutriment.setName("sugars_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(nutriments.getSugars100g());
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(nutriments.getSalt100g() != null) {
			nutriment.setName("salt_100g");
			nutriment.setComponent(false);
			nutriment.setValuefor100g(nutriments.getSalt100g());
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(nutriments.getFiber100g() != null) {
			nutriment.setName("fiber_100g");
			nutriment.setComponent(true);
			nutriment.setValuefor100g(nutriments.getFiber100g());
			nutrimentMapper.addNutriment(nutriment);
		}
		
		if(nutriments.getProteins100g() != null) {
			nutriment.setName("proteins_100g");
			nutriment.setComponent(true);
			nutriment.setValuefor100g(nutriments.getProteins100g());
			nutrimentMapper.addNutriment(nutriment);
		}
		
		return product;
	}

	@Override
	public BasketProduct addProductInBasket(String email, String barCode, Integer basketId) throws DaoException {
		final User user = new User();
		user.setEmail(email);
		final Basket basket = basketMapper.selectBasketFromIdAndUser(basketId, email);

		if (basket == null)
			throw new DaoException("Basket Does not exist");

		Product product = productMapper.getProductFromBarCode(barCode);
		if (product == null) product = insertProductFromAPI(barCode);

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
