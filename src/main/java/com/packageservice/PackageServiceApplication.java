package com.packageservice;

/**
 * @author vhblasco
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.packageservice.service.ForeignExchangeService;
import com.packageservice.service.ProductService;

@SpringBootApplication
public class PackageServiceApplication {

	/**
	 * Main function. Starting point
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Load the context
		ConfigurableApplicationContext context = SpringApplication.run(PackageServiceApplication.class, args);
		
		// We will execute these methods just at the beginning
		// to initialize the Products and the current foreign exchange
		context.getBean(ProductService.class).getAllRemoteProducts();
		context.getBean(ForeignExchangeService.class).getTodayForeignExchange();
		
	}
	
}
