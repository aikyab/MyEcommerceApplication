package com.AikyasEcommerce.ekart.service;

import com.AikyasEcommerce.ekart.model.Seller;

public interface SellerService {
	public Seller authenticateSeller(String emailId, String password) throws Exception;

	public String registerNewSeller(Seller seller) throws Exception ;
	
	public void updateProfile(Seller seller) throws Exception;
	
	public void changePassword(Seller seller) throws Exception;

}
