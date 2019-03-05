package fr.isima.kyou.rest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.apiaccess.OpenFoodGetter;
import fr.isima.kyou.beans.Root;

@RestController
public class FirstExample {

	@GetMapping("/resourceGroup/{id}")
	public ResponseEntity getProductValue(@PathVariable String id) {
		Root result = new Root();
		System.out.println("test");
		try {
			result = OpenFoodGetter.getInstance().getData(id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | JSONException
				| UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}

}
