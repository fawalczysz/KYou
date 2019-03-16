package fr.isima.kyou;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * This app is designed as a backend allowing to add users, baskets and products
 * in order to get nutritionnal qualties and defects of inserted products. It
 * uses OpenFoodFacts Api, website here : https://fr.openfoodfacts.org/
 *
 * This app uses an SQLite database, combined with MyBatis Framework, and
 * exposes a Spring Rest api
 */
@SpringBootApplication
@ComponentScan({ "fr.isima.kyou" })
@EntityScan(basePackages = "fr.isima.kyou")
public class App {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(App.class, args);

	}

}
