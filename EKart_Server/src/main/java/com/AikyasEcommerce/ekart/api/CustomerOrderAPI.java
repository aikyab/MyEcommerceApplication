package com.AikyasEcommerce.ekart.api;

import java.util.ArrayList;
import java.util.List;

import com.AikyasEcommerce.ekart.model.Order;
import com.AikyasEcommerce.ekart.model.OrderStatus;
import com.AikyasEcommerce.ekart.service.CustomerOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("CustomerOrderAPI")
public class CustomerOrderAPI {

	@Autowired
	private CustomerOrderService customerOrderService;
	
	@Autowired
	private Environment environment;
	
	static Logger logger = LogManager.getLogger(CustomerAPI.class.getName());
	
	@PostMapping(value = "placeOrder/{customerEmailId}/{addressId}/{paymentOption}")
	public ResponseEntity<String> placeNewOrder(@PathVariable("customerEmailId") String customerEmailId, @PathVariable("addressId") Integer addressId,@PathVariable("paymentOption") String paymentOption){
		try {
		customerOrderService.placeOrder(customerEmailId, addressId, paymentOption);
		
		String orderPlaceSuccessMsg = environment.getProperty("CustomerOrderAPI.ORDER_PLACED_SUCCESSFULLY");
		
		return new ResponseEntity<String>(orderPlaceSuccessMsg, HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()));
		}
	}
	
	
	@PostMapping(value = "viewOrders/{customerEmailId}")
	public ResponseEntity<List<Order>> viewOrders(@PathVariable String customerEmailId) throws Exception {
		try
		{
			logger.info("FETCHING ALL ORDERS MADE FOR CUSTOMER : "+customerEmailId);
			
			List<Order> orders = customerOrderService.viewOrders(customerEmailId);
			
			return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
			
		}
		catch (Exception e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	@PostMapping(value = "updateOrderStatus/{orderId}/{orderStatus}")
	public ResponseEntity<String> updateOrderStatus(@PathVariable("orderId") Integer orderId, @PathVariable("orderStatus") String orderStatus) throws Exception {
		try
		{
			customerOrderService.modifyOrderStatus(orderId, orderStatus);
			
			String modificationSuccessMsg = environment.getProperty("CustomerOrderAPI.ORDER_STATUS_UPDATE_SUCCESS");
			return new ResponseEntity<String>(modificationSuccessMsg, HttpStatus.OK);
			
		}
		catch (Exception e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	
	@GetMapping(value = "getAllOrderStatus")
	public ResponseEntity<List<String>> getAllOrderStatus() {
		try{
			
		
			List<String> orderStatusList = new ArrayList<>();
			
			OrderStatus[]orderStatus = OrderStatus.values();
			for (OrderStatus orderStatus2 : orderStatus) {
				orderStatusList.add(orderStatus2.toString());
			}
			
			return new ResponseEntity<List<String>>(orderStatusList, HttpStatus.OK);
		}
		catch (Exception e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
}
