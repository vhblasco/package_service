package com.packageservice.model;

/**
 * @author vhblasco
 */

import java.util.ArrayList;
import java.util.List;

public class PackageModel implements Cloneable{

	private String id;
	private String name;
	private String description;
	private double price;
	private List<ProductModel> products;
	private List<String> productsIds;
	
	/*
	 * Getters & Setters
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<ProductModel> getProducts() {
		return products;
	}
	public void setProducts(List<ProductModel> products) {
		this.products = products;
	}	
	public List<String> getProductsIds() {
		return productsIds;
	}
	public void setProductsIds(List<String> productsIds) {
		this.productsIds = productsIds;
	}
	
	/*
	 * Extra methods 
	 * */
	
	/*
	 * Set an extra product in the list of products
	 */
	public void setProduct(ProductModel product) {
		if (null == products) {
			products = new ArrayList<ProductModel>();
		}
		
		products.add(product);
	}
	
	/*
	 * Calculate the price in USD currency
	 */
	public double calculateUSDPrice() {
		
		double price = products.stream()
		.map(i -> i.getUsdPrice())
		.mapToLong(Double::longValue).sum();
		
		// Because products prices are in cents
		price = price / 100;
		
		this.price = price;
		return price;
	}
	
	/*
	 * Calculate price in other currency
	 * Base Price UDS 
	 */
	public double calculatePrice(String exchangeRate) {
		
		double price = this.getPrice() * Double.valueOf(exchangeRate).doubleValue();
		this.setPrice(price);
		
		return this.getPrice();
	}
	
	/*
	 * Method to clone the package
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return (PackageModel) super.clone();
	}
		
	
	
}
