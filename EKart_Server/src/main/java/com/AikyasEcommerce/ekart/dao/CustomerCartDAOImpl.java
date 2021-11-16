package com.AikyasEcommerce.ekart.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.AikyasEcommerce.ekart.entity.CustomerCartEntity;
import com.AikyasEcommerce.ekart.entity.CustomerEntity;
import com.AikyasEcommerce.ekart.entity.ProductEntity;
import com.AikyasEcommerce.ekart.model.CustomerCart;
import com.AikyasEcommerce.ekart.model.Product;
import com.AikyasEcommerce.ekart.model.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository(value="customerCartDAO")
public class CustomerCartDAOImpl implements CustomerCartDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addProductToCart(String customerEmailId, CustomerCart customerCart) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerEmailId);
		CustomerCartEntity cce = new CustomerCartEntity();
		ProductEntity pe = entityManager.find(ProductEntity.class, customerCart.getProduct().getProductId());
		cce.setProductEntity(pe);
		cce.setQuantity(customerCart.getQuantity());
		List<CustomerCartEntity> list = ce.getCustomerCarts();
		list.add(cce);
		ce.setCustomerCarts(list);
		entityManager.persist(ce);
	}

	@Override
	public List<CustomerCart> getCustomerCarts(String customerEmailId) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerEmailId);
		List<CustomerCart> listCarts = new LinkedList<CustomerCart>();
		for(CustomerCartEntity cce: ce.getCustomerCarts()) {
			CustomerCart cc = new CustomerCart();
			cc.setCartId(cce.getCartId());
			Product p = new Product();
			p.setBrand(cce.getProductEntity().getBrand());
			ProductCategory pc = new ProductCategory();
			pc.setCategory(cce.getProductEntity().getCategory().getCategory());
			p.setCategory(pc);
			p.setDescription(cce.getProductEntity().getDescription());
			p.setDiscount(cce.getProductEntity().getDiscount());
			p.setName(cce.getProductEntity().getName());
			p.setPrice(cce.getProductEntity().getPrice());
			p.setProductId(cce.getProductEntity().getProductId());
			p.setQuantity(cce.getProductEntity().getQuantity());
			cc.setProduct(p);
			cc.setQuantity(cce.getQuantity());
			listCarts.add(cc);
		}
		return listCarts;
	}

	@Override
	public void modifyQuantityOfProductInCart(Integer cartId, Integer quantity) {
		CustomerCartEntity cce = entityManager.find(CustomerCartEntity.class, cartId);
		cce.setQuantity(quantity);
	}

	@Override
	public void deleteProductFromCart(String customerEmailId, Integer cartId) {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerEmailId);
		List<CustomerCartEntity> carts = ce.getCustomerCarts();
		CustomerCartEntity ccx = null;
		for(CustomerCartEntity cce:carts) {
			if(cce.getCartId().equals(cartId)) {
				ccx = cce;
			}
		}
		carts.remove(ccx);
		ce.setCustomerCarts(carts);
		CustomerCartEntity cartEntity = entityManager.find(CustomerCartEntity.class, cartId);
		entityManager.remove(cartEntity);
	}

	@Override
	public Product getProductById(Integer id) {
		ProductEntity pe = entityManager.find(ProductEntity.class, id);
		Product p = null;
		if(pe!=null) {
		p= new Product();
		p.setBrand(pe.getBrand());
		ProductCategory pc = new ProductCategory();
		pc.setCategory(pe.getCategory().getCategory());
		p.setCategory(pc);
		p.setDescription(pe.getDescription());
		p.setDiscount(pe.getDiscount());
		p.setName(pe.getName());
		p.setPrice(pe.getPrice());
		p.setProductId(pe.getProductId());
		p.setQuantity(pe.getQuantity());
		}
		return p;
	}
	

}
