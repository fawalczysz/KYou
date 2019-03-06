package fr.isima.kyou.services.interfaces;

import org.springframework.web.bind.annotation.PathVariable;

import fr.isima.kyou.beans.Root;

public interface IProductService {

	void selectAll();

	Root getProductValue(@PathVariable String id);
}
