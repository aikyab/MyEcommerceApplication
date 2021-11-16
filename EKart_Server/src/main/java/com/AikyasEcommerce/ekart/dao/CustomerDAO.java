package com.AikyasEcommerce.ekart.dao;

import java.util.List;

import com.AikyasEcommerce.ekart.model.Address;
import com.AikyasEcommerce.ekart.model.Customer;
import com.AikyasEcommerce.ekart.model.Order;

public interface CustomerDAO {
	public Boolean checkAvailabilityOfEmailId(String emailID);
	public Boolean checkAvailabilityOfPhoneNumber(String phoneNumber);
	public String registerNewCustomer(Customer customer);
	public String authenticateCustomer(String emailId, String password);
	public String getPasswordOfCustomer(String emailId) ;
	public Customer getCustomerByEmailId(String emailId);
	public Customer getCustomerByPhoneNumber(String phoneNumber);
	public void updateProfile(Customer customer);
	public void changePassword(String customerEmailId, String newHashedPassword);
	public Integer addShippingAddress(String customerEmailId, Address address);
	public void updateShippingAddress(Address address);
	public void deleteShippingAddress(String customerEmailId, Integer addressId);
	public Address getShippingAddress(Integer addressId) throws Exception;
	public List<Order> getOrdersForCustomer(String customerEmailId);
	
}
