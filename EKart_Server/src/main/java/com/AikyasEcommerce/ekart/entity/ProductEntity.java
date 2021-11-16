package com.AikyasEcommerce.ekart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;



@Entity
@Table(name="ek_product")
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	private String name;
	private String description;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "category")
	private ProductCategoryEntity category;
	private String brand;
	private Double price;
	private Double discount;
	private Integer quantity;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_email_id")
	private SellerEntity sellerEntity;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public ProductCategoryEntity getCategory() {
		return category;
	}
	public void setCategory(ProductCategoryEntity category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public SellerEntity getSellerEntity() {
		return sellerEntity;
	}
	public void setSellerEntity(SellerEntity sellerEntity) {
		this.sellerEntity = sellerEntity;
	}
}
