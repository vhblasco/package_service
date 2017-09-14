package com.packageservice.service;

/**
 * Class to manage the Packages
 * @author vhblasco
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packageservice.model.PackageModel;
import com.packageservice.model.ProductModel;

@Component
public class PackageService implements PackageServiceI{

	/*
	 * List of registered packages.
	 */
	private static List<PackageModel> packages = new ArrayList<PackageModel>();
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ForeignExchangeService currencyService;

	
	public static List<PackageModel> getPackages() {
		return packages;
	}

	public static void setPackages(List<PackageModel> packages) {
		PackageService.packages = packages;
	}

	/**
	 * Get all packages in the given currency
	 */
	@Override
	public List<PackageModel> getAllPackages(String currency) {
		
		if("USD".equals(currency))
			return packages;
				
		String exchangeRate = currencyService.getTodayForeignExchange(currency);
		
		if (null != exchangeRate) {
			List<PackageModel> packagesCloned = clonePackages();
			
			if (null != packagesCloned) {
				for(PackageModel item: packagesCloned) {
					item.calculatePrice(exchangeRate);
				}
			}
			
			return packagesCloned;
		}
	    
		return null;
	}
	
	/**
	 * Method to know if the package exists or not
	 */
	@Override
	public boolean existPackage(String packageId) {
		Optional<PackageModel> pkg = packages.stream()
								.filter(i -> null != i && i.getId().equals(packageId))
								.findFirst();
		if (pkg.isPresent()) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Get Package by the given packageId
	 */
	@Override
	public PackageModel getPackageById(String packageId, String currency) {
		
		// If the currency is the USD (the one by default)
		// Then just return the package
		if("USD".equals(currency)) {
			Optional<PackageModel> pkgOptional = packages.stream()
					.filter(i -> null != i && i.getId().equals(packageId))
					.findFirst();
			if (pkgOptional.isPresent()) {
				PackageModel pkg = pkgOptional.get();
				return pkg;
			}
			
			return null;
		}
			
		// If other currency, then find the current exchange
		String exchangeRate = currencyService.getTodayForeignExchange(currency);
		if(null != exchangeRate) {

			Optional<PackageModel> pkgOptional = packages.stream()
									.filter(i -> null != i && i.getId().equals(packageId))
									.findFirst();
			if (pkgOptional.isPresent()) {
				PackageModel pkg = pkgOptional.get();
				PackageModel newItem = (PackageModel) clonePackage(pkg);
				if (null != newItem) {
					newItem.calculatePrice(exchangeRate);
					return newItem;
				}
			}
		} 
		
		// We arrive here if the currency doesn't exists or it the package doesn't exists
		return null;
		
	}
	
	/**
	 * Method to CLONE the package list.
	 * Useful to change the currency. 
	 * @return List<PackageModel>
	 */
	private List<PackageModel> clonePackages() {
		List<PackageModel> clone = new ArrayList<PackageModel>();
		try {
			for(PackageModel item: packages) {
				
				PackageModel newItem = (PackageModel) item.clone();
				clone.add(newItem);
				
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
	
	/**
	 * Method to CLONE the given package.
	 * Useful to change the currency.
	 * @return PackageModel
	 */
	private PackageModel clonePackage(PackageModel original) {
		try {
			
			PackageModel clone = (PackageModel) original.clone();
			return clone;
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to add a new package to the list
	 */
	@Override
	public boolean addPackage(PackageModel newPackage) {
		
		if (!existPackage(newPackage.getId())) {
			
			if (null != newPackage.getProductsIds()) {
				for (String productId: newPackage.getProductsIds()) {
					ProductModel product = productService.getProductById(productId);
					if (null != product) {
						newPackage.setProduct(product);
					} else {
						product = productService.getRemoteProductById(productId);
						newPackage.setProduct(product);
						
						productService.addProductToList(product);
					}	
				}
				
				newPackage.calculateUSDPrice();
			}
			packages.add(newPackage);
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Method to update an existing package
	 */
	@Override
	public boolean updatePackage(PackageModel newPackage) {
		
		if (!existPackage(newPackage.getId())) {
			packages.add(newPackage);
			return false;
		} else {
			deletePackageById(newPackage.getId());
			addPackage(newPackage);
			return true;
		}
	}

	/**
	 * Method to delete a package by its id.
	 */
	@Override
	public boolean deletePackageById(String packageId) {

	    for (Iterator<PackageModel> it = packages.iterator(); it.hasNext();) {
	    	PackageModel item = it.next();
	        if (packageId.equals(item.getId())) {
	            it.remove();
	            return true;
	        }
	    }
	    return false;
	}


}
