package cn.easybuy.utils;

import cn.easybuy.entity.Order;
import cn.easybuy.entity.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车工具类
 */
public class ShoppingCart implements Serializable{
	
	public List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
	
	private Double sum;

	//获取购物车中所有商品
	public List<ShoppingCartItem> getItems() {
		
		return items;
	}	
	
	/**
	 * 添加商品
	 */
	public ReturnResult addItem(Product product, Integer quantity) {
		
		ReturnResult result=new ReturnResult();
		
		int flag=0;
		
		// 遍历购物车内已经存在的商品
		for(int i=0;i<items.size();i++){
			
			// 判断购物车中是否已有此商品，如果有则累计数量
			if((items.get(i).getProduct().getId()).equals(product.getId())){
				
				// 超出商品实际数量
				if(items.get(i).getQuantity()+quantity>product.getStock()){
					
					return result.returnFail("商品数量不足");
				}
				else{
					
					// 购物车内商品实际数量=已存在数量+新增数量
					items.get(i).setQuantity(items.get(i).getQuantity()+quantity);
					
					flag=1;
				}
			}
		}
		// 超出商品实际数量
		if(quantity>product.getStock()){
			return result.returnFail("商品数量不足");
		}
		if(flag==0){
			items.add(new ShoppingCartItem(product, quantity));
		}
		
		// 返回添加成功
		return result.returnSuccess();
	}

	//移除一个商品
	public void removeItem(int index) {
		items.remove(index);
	}

	//修改数量
	public void modifyQuantity(int index, Integer quantity) {
		items.get(index).setQuantity(quantity);
	}

	//计算总价格
	public float getTotalCost() {
		float sum = 0;
		
		// 遍历购物车内全部商品
		for (ShoppingCartItem item : items) {
			
			sum = sum + item.getCost();
		}
		return sum;
	}

	public void setItems(List<ShoppingCartItem> items) {
		this.items = items;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
}
