package com.packageservice.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.packageservice.model.PackageModel;
import com.packageservice.model.ProductModel;
import com.packageservice.service.ForeignExchangeService;
import com.packageservice.service.PackageService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PackageServiceTest {

    @Mock
    private ForeignExchangeService currencyService;

    @InjectMocks
    private PackageService service;
    
    @Before
    public void clean() {
    	PackageService.setPackages(new ArrayList<PackageModel>());
    }

    
    
    private PackageModel buildPackage(String packageId) {
    	ProductModel prod1 = new ProductModel();
    	prod1.setId("id1");
    	prod1.setName("prod1");
    	prod1.setUsdPrice(100d);
    	
    	ProductModel prod2 = new ProductModel();
    	prod2.setId("id2");
    	prod2.setName("prod2");
    	prod2.setUsdPrice(200d);
    	
    	PackageModel pkg = new PackageModel();
    	pkg.setId(packageId);
    	pkg.setName("name-" + packageId);
    	pkg.setDescription("description-" + packageId);
    	
    	List<ProductModel> list = new ArrayList<ProductModel>();
    	list.add(prod1);
    	list.add(prod2);
    	pkg.setProducts(list);
    	
    	pkg.calculateUSDPrice();
    	assertEquals(3d, pkg.getPrice(),0);
    	
    	return pkg;
    }
    
	@Test
	public void getAllPackagesTestUSD() {
		PackageModel pkg = buildPackage("id1");
		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		
		PackageService.setPackages(listPkg);
		
		List<PackageModel> returnedList = service.getAllPackages("USD");
		assertEquals(listPkg.size(), returnedList.size());
		assertEquals(3d, returnedList.get(0).getPrice(),0);
				
	}

	@Test
	public void getAllPackagesTestOTHERCurrency() {
		PackageModel pkg = buildPackage("id1");
		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);

		PackageService.setPackages(listPkg);
		when(currencyService.getTodayForeignExchange("OTHER")).thenReturn("10");
		
		List<PackageModel> returnedList = service.getAllPackages("OTHER");
		assertEquals(listPkg.size(), returnedList.size());
		assertEquals(30d, returnedList.get(0).getPrice(),0);
				
	}
	
	
	@Test
	public void getPackagesByIdTestOTHERCurrency() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		when(currencyService.getTodayForeignExchange("OTHER")).thenReturn("5");
		
		PackageModel returnedPkg = service.getPackageById("idToFind", "OTHER");
		assertEquals(15d, returnedPkg.getPrice(),0);
				
	}
	
	@Test
	public void existPackageTest() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		
		boolean existingPackage = service.existPackage("idToFind");
		assertEquals(true, existingPackage);
	}
	
	@Test
	public void unexistPackageTest() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		
		boolean unexistingPackage = service.existPackage("notExistingId");
		assertEquals(false, unexistingPackage);		
	}
	
	
	@Test
	public void addPackageTest() {
		PackageModel pkg = buildPackage("id1");
		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);

		PackageService.setPackages(listPkg);
		when(currencyService.getTodayForeignExchange("OTHER")).thenReturn("10");
		
		List<PackageModel> returnedList = service.getAllPackages("OTHER");
		assertEquals(1, returnedList.size());
		assertEquals(30d, returnedList.get(0).getPrice(),0);
				
		PackageModel pkg2 = buildPackage("id2");
		service.addPackage(pkg2);
		returnedList = service.getAllPackages("OTHER");
		assertEquals(2, returnedList.size());
	}
	
	
	@Test
	public void updatePackageTest() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		
		PackageModel newPkg2 = buildPackage("idToFind");
		newPkg2.setPrice(4d);

		
		boolean updated = service.updatePackage(newPkg2);
		assertEquals(true, updated);
		
		PackageModel returnedPkg = service.getPackageById("idToFind", "USD");
		assertEquals(4d, returnedPkg.getPrice(),0);
		
	}
	
	@Test
	public void updatePackageNonExistingIdTest() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		
		PackageModel newPkg2 = buildPackage("otherId");
		newPkg2.setPrice(4d);

		
		boolean updated = service.updatePackage(newPkg2);
		assertEquals(false, updated);
		
		List<PackageModel> returnedList = service.getAllPackages("USD");
		assertEquals(3, returnedList.size());
		assertEquals(4d, returnedList.get(2).getPrice(),0);		
	}
	
	@Test
	public void deletePackageTest() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		
		
		boolean deleted = service.deletePackageById("idToFind");
		assertEquals(true, deleted);
		
		List<PackageModel> returnedList = service.getAllPackages("USD");
		assertEquals(1, returnedList.size());
	}
	
	@Test
	public void deletePackageNonExistingIdTest() {
		PackageModel pkg = buildPackage("id1");
		PackageModel pkg2 = buildPackage("idToFind");

		List<PackageModel> listPkg = new ArrayList<PackageModel>();
		listPkg.add(pkg);
		listPkg.add(pkg2);

		PackageService.setPackages(listPkg);
		
		
		boolean deleted = service.deletePackageById("otherId");
		assertEquals(false, deleted);
		
		List<PackageModel> returnedList = service.getAllPackages("USD");
		assertEquals(2, returnedList.size());
	}
}
