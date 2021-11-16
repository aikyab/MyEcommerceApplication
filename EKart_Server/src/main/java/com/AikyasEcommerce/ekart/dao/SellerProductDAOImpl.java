package com.AikyasEcommerce.ekart.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.AikyasEcommerce.ekart.entity.ProductCategoryEntity;
import com.AikyasEcommerce.ekart.entity.ProductEntity;
import com.AikyasEcommerce.ekart.entity.SellerEntity;
import com.AikyasEcommerce.ekart.model.Product;
import org.springframework.stereotype.Repository;

@Repository(value="sellerProductDAO")
public class SellerProductDAOImpl implements SellerProductDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Integer addNewProduct(Product product) {
		ProductEntity pe = new ProductEntity();
		pe.setBrand(product.getBrand());
		ProductCategoryEntity pce = new ProductCategoryEntity();
		pce.setCategory(product.getCategory().getCategory());
		pe.setCategory(pce);
		pe.setDescription(product.getDescription());
		pe.setDiscount(product.getDiscount());
		pe.setName(product.getName());
		pe.setPrice(product.getPrice());
		pe.setQuantity(product.getQuantity());
		
		SellerEntity se = entityManager.find(SellerEntity.class, product.getSellerEmailId());
		List<ProductEntity> productEntities = se.getProductEntities();
		productEntities.add(pe);
		se.setProductEntities(productEntities);
		entityManager.persist(se);
		ProductEntity lastProduct = se.getProductEntities().get(se.getProductEntities().size()-1);
		return lastProduct.getProductId();
	}

	@Override
	public Product modifyProductDetails(Product product) {
		Query query = entityManager.createQuery("select p from ProductEntity p where p.productId = :productId");
		query.setParameter("productId", product.getProductId());
		
		ProductEntity productEntity = (ProductEntity) query.getResultList().get(0);
		
		productEntity.setDescription(product.getDescription());
		productEntity.setDiscount(product.getDiscount());
		productEntity.setName(product.getName());
		productEntity.setPrice(product.getPrice());
		productEntity.setQuantity(product.getQuantity());
		
		return product;
	}

	@Override
	public Integer removeProduct(String sellerEmailId, Integer productId) {
		SellerEntity se = entityManager.find(SellerEntity.class, sellerEmailId);
		List<ProductEntity> productEntities = se.getProductEntities();
		ProductEntity toRemove = null;
		for(ProductEntity pe:productEntities) {
			if(pe.getProductId()==productId) {
				toRemove = pe;
			}
		}
		productEntities.remove(toRemove);
		se.setProductEntities(productEntities);
		return productId;
	}

	@Override
	public List<String> getProductCategoryList() {
		
		Query query = entityManager.createQuery("select p from ProductCategoryEntity p");
		@SuppressWarnings("unchecked")
		List<ProductCategoryEntity> productCategoryEntityList= query.getResultList();
		
		List<String> productCategories = new ArrayList<>();
		for (ProductCategoryEntity productCategoryEntity : productCategoryEntityList) {
			productCategories.add(productCategoryEntity.getCategory());
		}
		return productCategories;

	}
	

}
