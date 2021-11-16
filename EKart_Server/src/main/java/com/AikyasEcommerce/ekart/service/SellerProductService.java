package com.AikyasEcommerce.ekart.service;

import java.util.List;

import com.AikyasEcommerce.ekart.model.Product;

public interface SellerProductService {
	
	public Integer addNewProduct(Product product) throws Exception;
	
	public Product modifyProductDetails(Product product) throws Exception;
	
	public Integer removeProduct(Product product) throws Exception;
	
	public List<String> getProductCategoryList() throws Exception;

}
