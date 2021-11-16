package com.AikyasEcommerce.ekart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AikyasEcommerce.ekart.dao.CustomerProductDAO;
import com.AikyasEcommerce.ekart.model.Product;

@Transactional
@Service(value="customerProductService")
public class CustomerProductServiceImpl implements CustomerProductService{
	
	@Autowired
	private CustomerProductDAO customerProductDAO;

	@Override
	public List<Product> getAllProducts() throws Exception {
		List<Product> products = null;
		products = customerProductDAO.getAllProducts();
		return products;
	}
	
	
	
}
