package com.packageservice.service;

/**
 * @author vhblasco
 */

import java.util.List;

import com.packageservice.model.PackageModel;

public interface PackageServiceI {

	public boolean existPackage(String packageId);
	public List<PackageModel> getAllPackages(String currency);
	public PackageModel getPackageById(String packageId, String currency);
	public boolean addPackage(PackageModel newPackage);
	public boolean updatePackage(PackageModel newPackage);
	public boolean deletePackageById(String packageId); 
}
