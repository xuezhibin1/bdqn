package cn.easybuy.service.order;
import java.util.List;
import cn.easybuy.entity.Order;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.User;
import cn.easybuy.utils.ShoppingCart;

/**
 * OrderService接口方法
 */
public interface OrderService {
	
	public Order payShoppingCart(ShoppingCart cart, User user,String address);
	
	public List<Order> getOrderList(Integer userId,
									Integer currentPageNo,
									Integer pageSize);

    public int count(Integer userId);
    
    public List<OrderDetail> getOrderDetailList(Integer orderId);

}
