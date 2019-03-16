package fr.isima.kyou.apiaccess;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.isima.kyou.beans.api.Root;
import fr.isima.kyou.exceptions.ApiException;

/**
 * Service used in order to call OpenFoodFactsApi
 *
 */
@Service
public class OpenFoodGetter implements IApiGetter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Root getData(String id) throws ApiException {
		try {
			final String url = String.format("http://fr.openfoodfacts.org/api/v0/produit/%s.json", id);
			final HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json").asJson();
			return new Gson().fromJson(jsonResponse.getBody().toString(), Root.class);
		} catch (final UnirestException e) {
			throw new ApiException("Error occured while contacting api");
		}
	}

}
