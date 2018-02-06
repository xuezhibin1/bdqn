package cn.easybuy.dao.product;

import cn.easybuy.dao.IBaseDao;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.entity.User;
import cn.easybuy.param.OrderDetailParam;
import cn.easybuy.param.ProductCategoryParam;
import cn.easybuy.utils.Params;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 商品分类Dao接口
 */
public interface ProductCategoryDao extends IBaseDao {

	//删除商品分类
	void deleteById(Integer parseLong);
	
	public List<ProductCategory> queryProductCategorylist(ProductCategoryParam param);

	public List<ProductCategory> queryAllProductCategorylist(ProductCategoryParam param);
	
	public ProductCategory queryProductCategoryById(Integer id);
	
	public Integer save(ProductCategory productCategory) ;
	
	public Integer queryProductCategoryCount(ProductCategoryParam param);
	
	public void update(ProductCategory productCategory) ;
}
