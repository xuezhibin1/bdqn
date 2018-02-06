package cn.easybuy.service.product;

import cn.easybuy.entity.ProductCategory;
import cn.easybuy.param.ProductCategoryParam;
import cn.easybuy.utils.Params;
import cn.easybuy.utils.ProductCategoryVo;

import java.util.List;

/**
 * 商品种类业务类
 */
public interface ProductCategoryService {
	
    /**
     * 根据id查询商品分类
     */
    public ProductCategory getById(Integer id);
    
    /**
     * 查询商品分类列表
     */
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params);
    
    /**
     * 查询数目
     */
    public int queryProductCategoryCount(ProductCategoryParam params);
    
    /**
     * 修改商品分类
     */
    public void modifyProductCategory(ProductCategory productCategory);
    
    /**
     * 添加商品分类
     */
    public void addProductCategory(ProductCategory productCategory);
    
    /**
     * 根据id删除
     */
    public void deleteById(Integer id);
    
    /**
     * 查询全部的商品分类
     */
    public List<ProductCategoryVo> queryAllProductCategoryList();
    
    /**
     * 根据sql查询商品分类
     */
    public List<ProductCategory> queryProductCategorylistBySql(ProductCategoryParam params);
}
