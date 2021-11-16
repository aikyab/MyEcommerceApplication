package com.AikyasEcommerce.ekart.model;

import java.util.List;

public class Customer {
	private String emailId;
	private String name;
	private String password;
	private String newPassword;
	private String confirmNewPassword;
	private String phoneNo;
	private List<Address> addressess;
	private List<CustomerCart> customerCarts;
	private List<Order> orders;
	private String errorMessage;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public List<Address> getAddressess() {
		return addressess;
	}
	public void setAddressess(List<Address> addressess) {
		this.addressess = addressess;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<CustomerCart> getCustomerCarts() {
		return customerCarts;
	}
	public void setCustomerCarts(List<CustomerCart> customerCarts) {
		this.customerCarts = customerCarts;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
