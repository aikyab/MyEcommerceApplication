package com.AikyasEcommerce.ekart.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.AikyasEcommerce.ekart.entity.ProductEntity;
import com.AikyasEcommerce.ekart.model.Product;
import com.AikyasEcommerce.ekart.model.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository(value="customerProductDAO")
public class CustomerProductDAOImpl implements CustomerProductDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Product> getAllProducts() {
		Query query = entityManager.createQuery("select p from ProductEntity p");
		
		@SuppressWarnings("unchecked")
		List<ProductEntity> productEntityList= query.getResultList();
		
		List<Product> listOfProducts = new ArrayList<Product>();
		for (ProductEntity productEntity : productEntityList) {
			Product product = new Product();
			product.setBrand(productEntity.getBrand());
			ProductCategory pc = new ProductCategory();
			pc.setCategory(productEntity.getCategory().getCategory());
			product.setCategory(pc);			
			product.setDescription(productEntity.getDescription());
			product.setName(productEntity.getName());
			product.setPrice(productEntity.getPrice());
			product.setProductId(productEntity.getProductId());
			product.setQuantity(productEntity.getQuantity());
			product.setDiscount(productEntity.getDiscount());

			listOfProducts.add(product);
		}
		return listOfProducts;

	}
	
	
}
