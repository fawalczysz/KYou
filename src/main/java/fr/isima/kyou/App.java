package fr.isima.kyou;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.apiaccess.ITest;
import fr.isima.kyou.apiaccess.OpenFoodGetter;
import fr.isima.kyou.beans.Root;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {

	@Autowired
	static ITest test;

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SpringApplication.run(App.class, args);
		final OpenFoodGetter ofg = OpenFoodGetter.getInstance();
		try {
			final Root result = ofg.getData("3029330003533");
			// test.test();

		} catch (NumberFormatException | IOException | JSONException | UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
