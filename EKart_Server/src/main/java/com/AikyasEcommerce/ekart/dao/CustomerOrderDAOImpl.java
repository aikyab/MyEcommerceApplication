package com.AikyasEcommerce.ekart.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.AikyasEcommerce.ekart.entity.CustomerCartEntity;
import com.AikyasEcommerce.ekart.entity.CustomerEntity;
import com.AikyasEcommerce.ekart.entity.OrderEntity;
import com.AikyasEcommerce.ekart.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import com.AikyasEcommerce.ekart.model.Order;
import com.AikyasEcommerce.ekart.model.OrderStatus;
import com.AikyasEcommerce.ekart.model.PaymentThrough;
import com.AikyasEcommerce.ekart.model.Product;
import com.AikyasEcommerce.ekart.model.ProductCategory;

@Repository(value="customerOrderDAO")
public class CustomerOrderDAOImpl implements CustomerOrderDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void modifyOrderStatus(Integer orderId, OrderStatus orderStatus) {
		OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
		orderEntity.setOrderStatus(orderStatus);
	}
	
	@Override
	public void placeOrder(String customerEmailId, Integer addressId, PaymentThrough paymentThrough) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerEmailId);
		Integer nextOrderNum = null;
		List<OrderEntity> orderEntities = null;
		if(ce.getOrders()==null) {
			orderEntities = new LinkedList<OrderEntity>();
		}else {
			orderEntities = ce.getOrders();
		}
		Query query = entityManager.createQuery("Select max(oe.orderNumber) from OrderEntity oe");
		nextOrderNum = (Integer) query.getResultList().get(0);
		for(CustomerCartEntity cce: ce.getCustomerCarts()) {
			OrderEntity oe = new OrderEntity();
			oe.setAddressId(addressId);
			oe.setOrderNumber(nextOrderNum+1);
			oe.setDateOfOrder(LocalDateTime.now());
			oe.setDateOfDelivery(null);
			oe.setOrderStatus(OrderStatus.PLACED);
			oe.setPaymentThrough(paymentThrough);
			oe.setProductEntity(cce.getProductEntity());
			oe.setQuantity(cce.getQuantity());
			Double totalPrice = ((1-cce.getProductEntity().getDiscount()/100)*cce.getProductEntity().getPrice())*cce.getQuantity();
			oe.setTotalPrice(totalPrice);
			orderEntities.add(oe);
		}
		ce.setOrders(orderEntities);
		entityManager.persist(ce);
	}
	
	@Override
	public List<Order> getOrdersForProducts(List<Integer> productIds) {
		List<OrderEntity> allOrderEntities = new ArrayList<>();
		
		for (Integer productId : productIds) {
			Query query = entityManager.createQuery("select o from OrderEntity o where o.productEntity.productId = :productId");
			query.setParameter("productId", productId);
			@SuppressWarnings("unchecked")
			List<OrderEntity> orderEntities = query.getResultList();
			
			allOrderEntities.addAll(orderEntities);
		}
		
		List<Order> orders = new ArrayList<>();
		for (OrderEntity orderEntity : allOrderEntities) {
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
