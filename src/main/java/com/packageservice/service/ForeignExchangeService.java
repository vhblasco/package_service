package com.packageservice.service;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Class to retrieve the current ForeignExchange ratio
 * @author vhblasco
 */

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.packageservice.model.CurrencyModel;

@Component
public class ForeignExchangeService {
	
	/*
	 * Endpoint
	 */
	private static final String FOREIGN_EXCHANGE_RATIOS_ENDPOINT = "http://api.fixer.io/latest?base=USD";
	
	/*
	 * List of retrieved Foreign Exchange ratios.
	 */
	private static CurrencyModel currency;
	

	/*
	 * Getters & Setters
	 */
	public static CurrencyModel getCurrency() {
		return currency;
	}

	public static void setCurrency(CurrencyModel currency) {
		ForeignExchangeService.currency = currency;
	}

	
	/*
	 * Other methods
	 */

	/**
	 * Method to retrieve the current foreign exchage
	 */
	public void getTodayForeignExchange() {
		 RestTemplate restTemplate = new RestTemplate();
		 ForeignExchangeService.currency = restTemplate.getForObject(FOREIGN_EXCHANGE_RATIOS_ENDPOINT, CurrencyModel.class);
	}
	
	/**
	 * Method to keep the foreign exchage updated
	 * @param currencyStr
	 * @return String
	 */
	public String getTodayForeignExchange(String currencyStr) {
		LocalDate now = LocalDate.now();
		LocalDate lastUpdate = currency.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (now.isAfter(lastUpdate)) {
			getTodayForeignExchange();
		}
		
		return currency.getRates().get(currencyStr);
	}
	
}
