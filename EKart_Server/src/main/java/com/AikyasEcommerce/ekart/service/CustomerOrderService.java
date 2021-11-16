package com.AikyasEcommerce.ekart.service;

import java.util.List;

import com.AikyasEcommerce.ekart.model.Order;

public interface CustomerOrderService {
	
	public void modifyOrderStatus(Integer orderId, String orderStatus) throws Exception;
	public List<Order> viewOrders(String sellerEmailId) throws Exception;
	public void placeOrder(String customerEmailId,Integer addressId,String paymentMethod);

}
