package fr.isima.kyou.apiaccess;

import fr.isima.kyou.beans.api.Root;
import fr.isima.kyou.exceptions.ApiException;

public interface IApiGetter {
	/**
	 * get raw data converted from JSON for a speficic product using its barCode
	 * 
	 * @param id
	 * @return
	 * @throws ApiException
	 */
	Root getData(String id) throws ApiException;
}
