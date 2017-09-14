package com.packageservice.service;

/**
 * Class to manage the Products
 * @author vhblasco
 */

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.packageservice.model.ProductModel;

@Component
public class ProductService {
  
	private static final String EXTERNAL_ENDPOINT = "https://product-service.herokuapp.com/api/v1/products/";
	/*
	 * List of registered Products.
	 */
	private static List<ProductModel> products = new ArrayList<ProductModel>();
	
	
	/*
	 * Getters & Setters
	 */
	public static List<ProductModel> getProducts() {
		return products;
	}
	public static void setProducts(List<ProductModel> products) {
		ProductService.products = products;
	}

	/*
	 * Other methods
	 */

	/**
	 * Method to build the List<ProductModel> products from ProductModel[]
	 * @param arrayProducts
	 */
	public void setProducts(ProductModel[] arrayProducts) {
		 for (int i = 0; i < arrayProducts.length; i++) {
			 products.add(arrayProducts[i]);
		 }
	}

	/**
	 * Method to add one ProductModel to the product list
	 * @param product
	 */
	public void addProductToList(ProductModel product) {
		products.add(product);
	}
	
	/**
	 * Return a product from the list if it matches with the given productId
	 * else, return null
	 * @param productId
	 * @return ProductModel
	 */
	public  ProductModel getProductById(String productId) {
		Optional<ProductModel> product = products.stream()
				.filter(i -> null != i && i.getId().equals(productId))
				.findFirst();
		
		if (product.isPresent()) {
			return product.get();
		}else {
			return null;
		}
	}

	/**
	 * Method to get ALL the valid products. 
	 * To be executed at the beginning.
	 */
	public void getAllRemoteProducts() {
		 RestTemplate restTemplate = new RestTemplate();
		 HttpEntity<String> request = new HttpEntity<String>(buildAuthorizationHeader());
			ResponseEntity<ProductModel[]> responseEntity = 
					restTemplate.exchange(EXTERNAL_ENDPOINT, 
										HttpMethod.GET, 
										request, 
										ProductModel[].class);
			ProductModel[] objects = responseEntity.getBody();
			setProducts(objects);		
	}
	

	/**
	 * Method to get the product detail
	 * @param productId
	 * @return ProductModel
	 */
	public ProductModel getRemoteProductById(String productId) {
		 RestTemplate restTemplate = new RestTemplate();
		 HttpEntity<String> request = new HttpEntity<String>(buildAuthorizationHeader());
			ResponseEntity<ProductModel> responseEntity = 
					restTemplate.exchange(EXTERNAL_ENDPOINT + "/" + productId, 
										HttpMethod.GET, 
										request, 
										ProductModel.class);
			ProductModel object = responseEntity.getBody();
			return object;
	}
	
	/**
	 * Method to add the HTTP Authorization Basic header 
	 * @return HttpHeaders
	 */
	private HttpHeaders buildAuthorizationHeader() {
		
		String base64Creds = Base64.getEncoder().encodeToString(("user:pass").getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}

}
