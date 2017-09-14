package com.packageservice.model;

import java.util.Date;
import java.util.Map;

/**
 * Model to suport the currency
 * @author vhblasco
 *
 */
public class CurrencyModel {

	String base;
	Date date;
	Map<String, String> rates;
	
	/*
	 * Getters & Setters
	 */
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Map<String, String> getRates() {
		return rates;
	}
	public void setRates(Map<String, String> rates) {
		this.rates = rates;
	}
	
}
