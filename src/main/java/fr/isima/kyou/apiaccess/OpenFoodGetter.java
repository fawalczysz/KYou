package fr.isima.kyou.apiaccess;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.beans.Root;

public class OpenFoodGetter implements IApiGetter{

	public void getData(long id) throws IOException, JSONException, UnirestException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String url = String.format("http://fr.openfoodfacts.org/api/v0/produit/%d.json", id);
	    HttpResponse<JsonNode> jsonResponse =  
	    		Unirest.get(url)
	    	      .header("accept", "application/json")
	    	      .asJson();
	    System.out.println(jsonResponse.toString());


	}
	
	

}
