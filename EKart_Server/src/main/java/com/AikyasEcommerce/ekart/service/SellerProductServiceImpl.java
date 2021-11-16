package com.AikyasEcommerce.ekart.service;

import java.util.List;

import com.AikyasEcommerce.ekart.validator.SellerProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AikyasEcommerce.ekart.dao.SellerProductDAO;
import com.AikyasEcommerce.ekart.model.Product;

@Transactional
@Service(value="sellerProductService")
public class SellerProductServiceImpl implements SellerProductService{

	@Autowired
	private SellerProductDAO sellerProductDAO;

	
	@Override
	public Integer addNewProduct(Product product) throws Exception {
		SellerProductValidator.validateProductForAddNewProduct(product);

		Integer productId = sellerProductDAO.addNewProduct(product);
		
		return productId;

	}

	@Override
	public Product modifyProductDetails(Product product) throws Exception {
		SellerProductValidator.validateProductForModifyProductDetails(product);
		
		Product productX = sellerProductDAO.modifyProductDetails(product);
		
		return productX;
	}

	@Override
	public Integer removeProduct(Product product) throws Exception {
		return sellerProductDAO.removeProduct(product.getSellerEmailId(),product.getProductId());

	}

	@Override
	public List<String> getProductCategoryList() throws Exception {
		return sellerProductDAO.getProductCategoryList();

	}

}
