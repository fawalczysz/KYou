package fr.isima.kyou;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.apiaccess.OpenFoodGetter;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
    {
        SpringApplication.run(App.class, args);
        OpenFoodGetter ofg = new OpenFoodGetter();
        try {
			ofg.getData(new Long("3029330003533"));
		} catch (NumberFormatException | IOException | JSONException | UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
}
