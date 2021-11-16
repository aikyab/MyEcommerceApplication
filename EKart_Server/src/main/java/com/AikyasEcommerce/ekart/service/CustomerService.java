package com.AikyasEcommerce.ekart.service;

import com.AikyasEcommerce.ekart.model.Customer;
import com.AikyasEcommerce.ekart.model.Address;

public interface CustomerService {
	public Customer authenticateCustomer(String emailId, String password) throws Exception;
	
	public String registerNewCustomer(Customer customer) throws Exception ;

	public void updateProfile(Customer customer) throws Exception;

	public void changePassword(String customerEmailId, String currentPassword, String newPassword) throws Exception;
	
	public Integer addShippingAddress(String customerEmailId, Address address) throws Exception;
	
	public void updateShippingAddress(Address address) throws Exception;
	
	public void deleteShippingAddress(String customerEmailId, Integer addressId) throws Exception;
	
	public Address getShippingAddress(Integer addressId) throws Exception;

}
