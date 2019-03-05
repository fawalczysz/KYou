package fr.isima.kyou.apiaccess;

import fr.isima.kyou.beans.Root;

public interface IApiGetter {

	Root getData(String id) throws Exception;
}
