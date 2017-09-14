package com.packageservice.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.packageservice.model.ProductModel;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductServiceTest {

	
    @InjectMocks
    private ProductService service;
    
    
    @Before
    public void clean() {
    	ProductService.setProducts(new ArrayList<ProductModel>());
    }
    
    private ProductModel buildProduct(String productId) {
    	ProductModel prod1 = new ProductModel();
    	prod1.setId(productId);
    	prod1.setName("name-"+productId);
    	prod1.setUsdPrice(100d);
    	
    	return prod1;
    }
    
    private ProductModel[] buildProductArray() {
    	ProductModel [] prods = new ProductModel[3];
    	
    	ProductModel prod1 = buildProduct("prId1");
    	ProductModel prod2 = buildProduct("prId2");
    	ProductModel prod3 = buildProduct("prId3");
    	
    	prods[0] = prod1;
    	prods[1] = prod2;
    	prods[2] = prod3;
    	
    	return prods;
    }
    
    
	@Test
	public void setProductsTest() {
		ProductModel[] prods = buildProductArray();
		
		service.setProducts(prods);
		
		List<ProductModel> list = ProductService.getProducts();
		assertEquals(3, list.size());
    }
	
	@Test
	public void addProductToListTest() {
		ProductModel[] prods = buildProductArray();
		
		service.setProducts(prods);
		
		List<ProductModel> list = ProductService.getProducts();
		assertEquals(3, list.size());
		
		ProductModel other = buildProduct("otherProduct");
		
		service.addProductToList(other);
		
		list = ProductService.getProducts();
		assertEquals(4, list.size());
		assertEquals("otherProduct", list.get(3).getId());
		
    }
	
	@Test
	public void getProductByIdTest() {
		ProductModel[] prods = buildProductArray();
		
		service.setProducts(prods);
		
		List<ProductModel> list = ProductService.getProducts();
		assertEquals(3, list.size());
		
		ProductModel other = buildProduct("productToFind");
		service.addProductToList(other);
		
		list = ProductService.getProducts();
		assertEquals(4, list.size());

		ProductModel retrieved = service.getProductById("productToFind");
		assertEquals("productToFind", retrieved.getId());

    }
	
	@Test
	public void getProductNonExistingByIdTest() {
		ProductModel[] prods = buildProductArray();
		
		service.setProducts(prods);
		
		List<ProductModel> list = ProductService.getProducts();
		assertEquals(3, list.size());
		
		ProductModel other = buildProduct("productToFind");
		service.addProductToList(other);
		
		list = ProductService.getProducts();
		assertEquals(4, list.size());

		ProductModel retrieved = service.getProductById("otherId");
		assertEquals(null, retrieved);

    }
}
