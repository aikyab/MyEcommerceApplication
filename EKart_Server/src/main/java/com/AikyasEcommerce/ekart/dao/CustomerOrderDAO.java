package com.AikyasEcommerce.ekart.dao;

import java.util.List;

import com.AikyasEcommerce.ekart.model.Order;
import com.AikyasEcommerce.ekart.model.OrderStatus;
import com.AikyasEcommerce.ekart.model.PaymentThrough;

public interface CustomerOrderDAO {
	public void modifyOrderStatus(Integer orderId, OrderStatus orderStatus);
	public List<Order> getOrdersForProducts(List<Integer> productIds);
	public void placeOrder(String customerEmailId, Integer addressId, PaymentThrough paymentThrough);

}
