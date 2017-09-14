package com.packageservice.model;

/**
 * @author vhblasco
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductModel {
	String id;
	String name;
	double usdPrice;
	
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
	public double getUsdPrice() {
		return usdPrice;
	}
	public void setUsdPrice(double usdPrice) {
		this.usdPrice = usdPrice;
	}
}
