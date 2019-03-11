package fr.isima.kyou.apiaccess;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.beans.api.Root;

public class OpenFoodGetter implements IApiGetter {

	private static OpenFoodGetter instance;

	private OpenFoodGetter() {

	}

	public static OpenFoodGetter getInstance() {
		if (instance == null) {
			instance = new OpenFoodGetter();
		}
		return instance;
	}

	@Override
	public Root getData(String id) throws IOException, JSONException, UnirestException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		final String url = String.format("http://fr.openfoodfacts.org/api/v0/produit/%s.json", id);
		final HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json").asJson();
		return new Gson().fromJson(jsonResponse.getBody().toString(), Root.class);
	}

}
