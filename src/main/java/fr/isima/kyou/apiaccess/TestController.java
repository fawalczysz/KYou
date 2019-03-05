package fr.isima.kyou.apiaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	ITest test;

	@GetMapping("/test")
	public void testMethod() {
		test.test();
	}

}
