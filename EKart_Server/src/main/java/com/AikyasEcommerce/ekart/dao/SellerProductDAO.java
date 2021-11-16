package com.AikyasEcommerce.ekart.dao;

import java.util.List;

import com.AikyasEcommerce.ekart.model.Product;

public interface SellerProductDAO {
	public Integer addNewProduct(Product product);
	
	public Product modifyProductDetails(Product product);
	
	public Integer removeProduct(String sellerEmailId, Integer productId);
	
	public List<String> getProductCategoryList();

}
