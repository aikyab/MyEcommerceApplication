package com.AikyasEcommerce.ekart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AikyasEcommerce.ekart.dao.CustomerDAO;
import com.AikyasEcommerce.ekart.dao.CustomerOrderDAO;
import com.AikyasEcommerce.ekart.model.Order;
import com.AikyasEcommerce.ekart.model.OrderStatus;
import com.AikyasEcommerce.ekart.model.PaymentThrough;

@Transactional
@Service(value="customerOrderService")
public class CustomerOrderServiceImpl implements CustomerOrderService{

	@Autowired
	private CustomerOrderDAO customerOrderDAO;
	
	@Autowired
	private CustomerDAO customerDAO;

	
	@Override
	public void modifyOrderStatus(Integer orderId, String orderStatus) throws Exception {
		customerOrderDAO.modifyOrderStatus(orderId, OrderStatus.valueOf(orderStatus));

	}

	@Override
	public List<Order> viewOrders(String customerEmailId) throws Exception {
		List<Order> ordersFromDB = customerDAO.getOrdersForCustomer(customerEmailId);
		if(ordersFromDB.isEmpty()){
			throw new Exception("CustomerOrderService.NO_ORDERS_PLACED_YET");
		}
		
		return ordersFromDB;

	}

	@Override
	public void placeOrder(String customerEmailId, Integer addressId, String paymentMethod){
		PaymentThrough paymentThrough = PaymentThrough.valueOf(paymentMethod);
		
		customerOrderDAO.placeOrder(customerEmailId, addressId, paymentThrough);
		
	}

}