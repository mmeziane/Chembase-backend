package com.chembase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class ChembaseBackendApplicationTests {

	@Autowired
	private ApplicationContext applicationContext; // Inject ApplicationContext

	@Test
	void contextLoads() {
		// Test if the context loads correctly
		assertNotNull("Application context should not be null", applicationContext); // Check if the application context is loaded
	}

}
