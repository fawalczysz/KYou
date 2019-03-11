package fr.isima.kyou;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.apiaccess.OpenFoodGetter;
import fr.isima.kyou.beans.api.Root;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan({ "fr.isima.kyou" })
@EntityScan(basePackages = "fr.isima.kyou")
public class App {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(App.class, args);
		final OpenFoodGetter ofg = OpenFoodGetter.getInstance();
		try {
			final Root result = ofg.getData("3029330003533");

		} catch (NumberFormatException | IOException | JSONException | UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
