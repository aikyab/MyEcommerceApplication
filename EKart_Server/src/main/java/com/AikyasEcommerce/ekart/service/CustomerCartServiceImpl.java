package com.AikyasEcommerce.ekart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AikyasEcommerce.ekart.dao.CustomerCartDAO;
import com.AikyasEcommerce.ekart.model.CustomerCart;
import com.AikyasEcommerce.ekart.model.Product;

@Transactional
@Service(value="customerCartService")
public class CustomerCartServiceImpl implements CustomerCartService{

	@Autowired
	private CustomerCartDAO customerCartDAO;

	@Override
	public void addProductToCart(String customerEmailId, CustomerCart customerCart) throws Exception {
		List<CustomerCart> customerCartsFromDB = customerCartDAO.getCustomerCarts(customerEmailId);
		
		for (CustomerCart customerCartFromDB : customerCartsFromDB) {

			if(customerCartFromDB.getProduct().getProductId().equals(customerCart.getProduct().getProductId()))
				throw new Exception("CustomerCartService.PRODUCT_PRESENT_IN_CART");
		}
		
		Product productFromDao = customerCartDAO.getProductById(customerCart.getProduct().getProductId());
		if(productFromDao.getQuantity() < customerCart.getQuantity())
			throw new Exception("CustomerCartService.INSUFFICIENT_STOCK");
		
		customerCartDAO.addProductToCart(customerEmailId, customerCart);

	
	}

	@Override
	public List<CustomerCart> getCustomerCarts(String customerEmailId) throws Exception {
		List<CustomerCart> customerCartList = customerCartDAO.getCustomerCarts(customerEmailId);
		if(customerCartList==null || customerCartList.isEmpty())
			throw new Exception("CustomerCartService.NO_PRODUCT_ADDED_TO_CART");
		return customerCartList;
		
	}

	@Override
	public void modifyQuantityOfProductInCart(Integer cartId, Integer quantity, Integer productId) throws Exception {
		Product productFromDB  = customerCartDAO.getProductById(productId);
		if(quantity>productFromDB.getQuantity())
			throw new Exception("CustomerCartService.INSUFFICIENT_STOCK");
		customerCartDAO.modifyQuantityOfProductInCart(cartId, quantity);

	}

	@Override
	public void deleteProductFromCart(String customerEmailId, Integer cartId) {
		customerCartDAO.deleteProductFromCart(customerEmailId, cartId);
	}
	
}
