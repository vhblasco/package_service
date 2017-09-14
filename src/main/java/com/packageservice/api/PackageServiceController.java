package com.packageservice.api;

/**
 * Rest Controller
 * API 
 * @author vhblasco
 */
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packageservice.model.PackageModel;
import com.packageservice.service.PackageService;

@RestController
public class PackageServiceController {

	
	@Autowired
	private PackageService service;
	
	/**
	 * GET Method.
	 * Retrieves all packages
	 * @param response
	 * @return List<PackageModel>
	 */
	@GetMapping("/packages")
	public List<PackageModel> getAllPackages(@RequestParam(value = "currency", defaultValue = "USD") String currency,
											 HttpServletResponse response){
				
		response.setStatus(org.springframework.http.HttpStatus.OK.value());
		return service.getAllPackages(currency.toUpperCase());
	}
	
	/**
	 * GET Method.
	 * Retrieves the package by its {packageId}
	 * @param packageId
	 * @param response
	 * @return PackageModel
	 */
	@GetMapping("/packages/{packageId}")
	public PackageModel getPackageDetail(@PathVariable String packageId,
			@RequestParam(value = "currency", defaultValue = "USD") String currency,
			HttpServletResponse response){
		
		PackageModel pkg = service.getPackageById(packageId, currency.toUpperCase());
		if (null == pkg) {
			response.setStatus(org.springframework.http.HttpStatus.NOT_FOUND.value());
		} else {
			response.setStatus(org.springframework.http.HttpStatus.OK.value());
		}
		
		return pkg;
			
					
	}
	
	/**
	 * PUT Method.
	 * Creates a new package if not exist.
	 * @param newPackage
	 * @param response
	 */
	@PutMapping("/packages/package")
	public void registerPackage(@NotEmpty @RequestBody PackageModel newPackage, HttpServletResponse response){
		
		if (!service.addPackage(newPackage)) {
			response.setStatus(org.springframework.http.HttpStatus.OK.value());
		} else {
			response.setStatus(org.springframework.http.HttpStatus.CREATED.value());
		}
	}
	
	/**
	 * POST Method.
	 * Updates the package if exists.
	 * If doesn't exists then creates it
	 * @param newPackage
	 * @param response
	 */
	@PostMapping("/packages/package")
	public void updatePackage(@NotEmpty @RequestBody PackageModel newPackage, HttpServletResponse response){
		
		if (!service.updatePackage(newPackage)) {
			response.setStatus(org.springframework.http.HttpStatus.CREATED.value());
		} else {
			response.setStatus(org.springframework.http.HttpStatus.OK.value());
		}
	}
	
	/**
	 * DETELE Method.
	 * Deleted the package if exist.
	 * @param packageId
	 * @param response
	 */
	@DeleteMapping("/packages/package/{packageId}")
	public void deletePackage(@NotEmpty @PathVariable String packageId, HttpServletResponse response){
		
		if (!service.deletePackageById(packageId)) {
			response.setStatus(org.springframework.http.HttpStatus.NOT_FOUND.value());
		} else {
			response.setStatus(org.springframework.http.HttpStatus.OK.value());
		}
	}
}
