package com.AikyasEcommerce.ekart.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.AikyasEcommerce.ekart.entity.AddressEntity;
import com.AikyasEcommerce.ekart.entity.CustomerCartEntity;
import com.AikyasEcommerce.ekart.entity.CustomerEntity;
import com.AikyasEcommerce.ekart.entity.OrderEntity;
import com.AikyasEcommerce.ekart.entity.ProductEntity;
import com.AikyasEcommerce.ekart.model.Customer;
import com.AikyasEcommerce.ekart.model.Address;
import com.AikyasEcommerce.ekart.model.CustomerCart;
import com.AikyasEcommerce.ekart.model.Order;
import com.AikyasEcommerce.ekart.model.Product;
import com.AikyasEcommerce.ekart.model.ProductCategory;

@Repository(value="customerDAO")
public class CustomerDAOImpl implements CustomerDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Boolean checkAvailabilityOfEmailId(String emailID) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, emailID);
		if(ce==null)
			return true;
		return false;
	}
	
	@Override
	public Boolean checkAvailabilityOfPhoneNumber(String phoneNumber) {
		String s = "Select c from CustomerEntity c where c.phoneNumber = :phn";
		Query query = entityManager.createQuery(s);
		query.setParameter("phn", phoneNumber);
		@SuppressWarnings("unchecked")
		List<CustomerEntity> ce = query.getResultList();
		if(ce.isEmpty())
			return true;
		return false;
	}

	@Override
	public String registerNewCustomer(Customer customer) {
		CustomerEntity ce = new CustomerEntity();
		ce.setName(customer.getName());
		ce.setEmailId(customer.getEmailId());
		ce.setPhoneNumber(customer.getPhoneNo());
		ce.setPassword(customer.getPassword());
		entityManager.persist(ce);
		return ce.getEmailId();
	}

	@Override
	public String authenticateCustomer(String emailId, String password) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, emailId);
		if(ce!=null) {
			if(ce.getPassword().equals(password))
				return ce.getEmailId();
		}
		return null;
	}

	@Override
	public String getPasswordOfCustomer(String emailId) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, emailId);
		if(ce!=null)
			return ce.getPassword();
		return null;
	}

	@Override
	public Customer getCustomerByEmailId(String emailId) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, emailId);
		Customer c = null;
		List<Address> listAddress = new ArrayList<Address>();
		if(ce!=null) {
			c = new Customer();
			c.setEmailId(emailId);
			c.setName(ce.getEmailId());
			c.setPassword(ce.getPassword());
			c.setPhoneNo(ce.getPhoneNumber());
			if(ce.getAddressEntities()!=null) {
				for(AddressEntity ae: ce.getAddressEntities()) {
					Address a = new Address();
					a.setAddressId(ae.getAddressId());
					a.setCity(ae.getCity());
					a.setContactNo(ae.getContactNumber());
					a.setLine1(ae.getLine1());
					a.setLine2(ae.getLine2());
					a.setPin(ae.getPin());
					a.setState(ae.getState());
					listAddress.add(a);
				}
			}
			c.setAddressess(listAddress);
			List<CustomerCart> customerCarts = new ArrayList<>();
			for (CustomerCartEntity customerCartEntity : ce.getCustomerCarts()) {
				CustomerCart cart = new CustomerCart();
				cart.setCartId(customerCartEntity.getCartId());
				cart.setQuantity(customerCartEntity.getQuantity());
					Product product = new Product();
					product.setBrand(customerCartEntity.getProductEntity().getBrand());
					ProductCategory pc = new ProductCategory();
					pc.setCategory(customerCartEntity.getProductEntity().getCategory().getCategory());
					product.setCategory(pc);
					product.setDescription(customerCartEntity.getProductEntity().getDescription());
					product.setDiscount(customerCartEntity.getProductEntity().getDiscount());
					product.setName(customerCartEntity.getProductEntity().getName());
					product.setPrice(customerCartEntity.getProductEntity().getPrice());
					product.setProductId(customerCartEntity.getProductEntity().getProductId());
					product.setQuantity(customerCartEntity.getProductEntity().getQuantity());
				
				cart.setProduct(product);
				
				customerCarts.add(cart);
			}
			c.setCustomerCarts(customerCarts);
			
		}
		
		return c;
	}
	

	@Override
	public Customer getCustomerByPhoneNumber(String phoneNumber) {
		String s = "Select c from CustomerEntity c where c.phoneNumber = :phn";
		Query query = entityManager.createQuery(s);
		query.setParameter("phn", phoneNumber);
		@SuppressWarnings("unchecked")
		List<CustomerEntity> list = query.getResultList();
		Customer c = null;
		if(!list.isEmpty()) {
			c = new Customer();
			CustomerEntity ce = list.get(0);
			c.setEmailId(ce.getEmailId());
			c.setName(ce.getEmailId());
			c.setPassword(ce.getPassword());
			c.setPhoneNo(ce.getPhoneNumber());
			List<Address> listAddress = new ArrayList<Address>();
			if(ce.getAddressEntities()!=null) {
				for(AddressEntity ae: ce.getAddressEntities()) {
					Address a = new Address();
					a.setAddressId(ae.getAddressId());
					a.setCity(ae.getCity());
					a.setContactNo(ae.getContactNumber());
					a.setLine1(ae.getLine1());
					a.setLine2(ae.getLine2());
					a.setPin(ae.getPin());
					a.setState(ae.getState());
					listAddress.add(a);
				}
			}
			c.setAddressess(listAddress);
			List<CustomerCart> customerCarts = new ArrayList<>();
			for (CustomerCartEntity customerCartEntity : ce.getCustomerCarts()) {	
				CustomerCart cart = new CustomerCart();
				cart.setCartId(customerCartEntity.getCartId());
				cart.setQuantity(customerCartEntity.getQuantity());
					Product product = new Product();
					product.setBrand(customerCartEntity.getProductEntity().getBrand());
					ProductCategory pc = new ProductCategory();
					pc.setCategory(customerCartEntity.getProductEntity().getCategory().getCategory());
					product.setCategory(pc);
					product.setDescription(customerCartEntity.getProductEntity().getDescription());
					product.setDiscount(customerCartEntity.getProductEntity().getDiscount());
					product.setName(customerCartEntity.getProductEntity().getName());
					product.setPrice(customerCartEntity.getProductEntity().getPrice());
					product.setProductId(customerCartEntity.getProductEntity().getProductId());
					product.setQuantity(customerCartEntity.getProductEntity().getQuantity());
				
				cart.setProduct(product);
				
				customerCarts.add(cart);
			}
			c.setCustomerCarts(customerCarts);
			
		
		return c;
		}
		return null;
	}

	@Override
	public void updateProfile(Customer customer) {
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customer.getEmailId().toLowerCase());
		
		customerEntity.setName(customer.getName());
		customerEntity.setPhoneNumber(customer.getPhoneNo());
		

		
	}

	@Override
	public void changePassword(String customerEmailId, String newHashedPassword) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerEmailId);
		ce.setPassword(newHashedPassword);
	}

	@Override
	public Integer addShippingAddress(String customerEmailId, Address address) {

		CustomerEntity customerEntity = null;
		
		Integer newAddressId = null;
		
		customerEntity = entityManager.find(CustomerEntity.class, customerEmailId);
		
		List<AddressEntity> AddressEntities = customerEntity.getAddressEntities();
		
		AddressEntity newShippingAddress = new AddressEntity();
		newShippingAddress.setLine1(address.getLine1());
		newShippingAddress.setLine2(address.getLine2());
		newShippingAddress.setCity(address.getCity());
		newShippingAddress.setContactNumber(address.getContactNo());
		newShippingAddress.setPin(address.getPin());
		newShippingAddress.setState(address.getState());
		
		AddressEntities.add(newShippingAddress);
		customerEntity.setAddressEntities(AddressEntities);
		
		entityManager.persist(customerEntity);
		
		
		List<AddressEntity> AddressEntitiesAfterAddition = customerEntity.getAddressEntities();
		
		AddressEntity newAddress = AddressEntitiesAfterAddition.get(AddressEntitiesAfterAddition.size()-1);
		newAddressId = newAddress.getAddressId();
		return newAddressId;
		}

	@Override
	public void updateShippingAddress(Address address) {
		AddressEntity addressEntity=entityManager.find(AddressEntity.class, address.getAddressId());
		addressEntity.setLine1(address.getLine1());
		addressEntity.setLine2(address.getLine2());
		addressEntity.setCity(address.getCity());
		addressEntity.setContactNumber(address.getContactNo());
		addressEntity.setPin(address.getPin());
		addressEntity.setState(address.getState());

	}

	@Override
	public void deleteShippingAddress(String customerEmailId, Integer addressId) {
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerEmailId);
	       List<AddressEntity> AddressEntities = customerEntity.getAddressEntities();
	       AddressEntity addressEntity = entityManager.find(AddressEntity.class, addressId);
	       
	       AddressEntities.remove(addressEntity);
	       entityManager.remove(addressEntity);
	}

	@Override
	public Address getShippingAddress(Integer addressId) throws Exception {
		AddressEntity addressEntity = entityManager.find(AddressEntity.class, addressId);
	       Address address = new Address();
	       address.setAddressId(addressEntity.getAddressId());
	       address.setLine1(addressEntity.getLine1());
	       address.setLine2(addressEntity.getLine2());
	       address.setCity(addressEntity.getCity());
	       address.setContactNo(addressEntity.getContactNumber());
	       address.setPin(addressEntity.getPin());
	       address.setState(addressEntity.getState());
	       
	       return address;
	}



	@Override
	public List<Order> getOrdersForCustomer(String customerEmailId) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerEmailId);
		
		List<Order> orders = new ArrayList<>();
		for (OrderEntity orderEntity : ce.getOrders()) {
			Order order = new Order();
			order.setAddressId(orderEntity.getAddressId());
			order.setDateOfOrder(orderEntity.getDateOfOrder());
			order.setOrderId(orderEntity.getOrderId());
			order.setOrderNumber(orderEntity.getOrderNumber());
			order.setOrderStatus(orderEntity.getOrderStatus());
				ProductEntity productEntity = orderEntity.getProductEntity();
				Product product = new Product();
				product.setBrand(productEntity.getBrand());
				ProductCategory pc = new ProductCategory();
				pc.setCategory(productEntity.getCategory().getCategory());
				product.setCategory(pc);
				product.setDescription(productEntity.getDescription());
				product.setDiscount(productEntity.getDiscount());
				product.setName(productEntity.getName());
				product.setPrice(productEntity.getPrice());
				product.setProductId(productEntity.getProductId());
				product.setQuantity(productEntity.getQuantity());
			order.setProduct(product);
			order.setQuantity(orderEntity.getQuantity());
			order.setTotalPrice(orderEntity.getTotalPrice());
			order.setPaymentThrough(orderEntity.getPaymentThrough());
			order.setDateOfDelivery(orderEntity.getDateOfDelivery());
			orders.add(order);
		}
		
		return orders;
	}

}
