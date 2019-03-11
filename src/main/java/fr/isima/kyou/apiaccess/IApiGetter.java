package fr.isima.kyou.apiaccess;

import fr.isima.kyou.beans.api.Root;

public interface IApiGetter {

	Root getData(String id) throws Exception;
}
