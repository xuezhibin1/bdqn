package cn.easybuy.utils;

import cn.easybuy.entity.Product;

import java.io.Serializable;

/**
 * 购物车商品工具
 */
public class ShoppingCartItem implements Serializable{
	
	// 商品
	private Product product;
	
	// 数量
	private Integer quantity;
	
	// 购物车内单个商品的总价格
	private float cost;

	public ShoppingCartItem(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
		
		// 计算购物车内单个商品的总价格
		this.cost = product.getPrice() * quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		this.cost = product.getPrice() * quantity;
	}

	public Product getProduct() {
		return product;
	}

	public float getCost() {
		return cost;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
