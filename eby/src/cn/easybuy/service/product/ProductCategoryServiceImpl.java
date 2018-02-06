package cn.easybuy.service.product;

import cn.easybuy.dao.product.ProductCategoryDao;
import cn.easybuy.dao.product.ProductCategoryDaoImpl;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.param.ProductCategoryParam;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Params;
import cn.easybuy.utils.ProductCategoryVo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类业务实现类
 */
public class ProductCategoryServiceImpl implements ProductCategoryService {
    /**
     *
     * @param id
     * @return
     */
    @Override
    public ProductCategory getById(Integer id) {
        Connection connection = null;
        ProductCategory productCategory = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategory =productCategoryDao.queryProductCategoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return productCategory;
    }

    /**
     * 查询子分类
     */
    @Override
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params) {
        Connection connection = null;
        List<ProductCategory> rtn = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            
            // 查询子分类
            rtn = productCategoryDao.queryProductCategorylist(params);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    public List<ProductCategory> queryProductCategorylistBySql(ProductCategoryParam params) {
        Connection connection = null;
        List<ProductCategory> rtn = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategorylist(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public int queryProductCategoryCount(ProductCategoryParam params) {
        Connection connection = null;
        int rtn = 0;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategoryCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
        return rtn;
    }

    /**
     * 修改商品分类
     */
    @Override
    public void modifyProductCategory(ProductCategory productCategory) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            
            // 修改商品分类信息
            productCategoryDao.update(productCategory);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }
    /**
     * 新增商品分类
     */
    @Override
    public void addProductCategory(ProductCategory productCategory) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            
            // 新增商品分类信息
            productCategoryDao.save(productCategory);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }
    /**
     * 根据Id删除商品
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        Connection connection = null;
        try {
            connection = DataSourceUtil.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }
    
    /**
     * 查询全部的商品分类
     */
    @Override
    public List<ProductCategoryVo> queryAllProductCategoryList() {
    	
        // 查询全部分类的集合
        List<ProductCategoryVo> productCategory1VoList = new ArrayList<ProductCategoryVo>();
        
        // 查询一级分类
        List<ProductCategory> productCategory1List = getProductCategories(null);
        
        // 遍历一级分类查询二级分类
        for (ProductCategory product1Category : productCategory1List) {
        	
            //封装一级分类
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();
            
            productCategoryVo.setProductCategory(product1Category);
            
            // 一级分类子集合
            List<ProductCategoryVo> productCategoryVo1ChildList = new ArrayList<ProductCategoryVo>();
            
            //根据一级分类编号查询二级分类
            List<ProductCategory> productCategory2List = getProductCategories(product1Category.getId());
            
            // 遍历二级分类
            for (ProductCategory productCategory2 : productCategory2List) {
            	
                ProductCategoryVo productCategoryVo2 = new ProductCategoryVo();
                
                // 将二级分类对象放入集合
                productCategoryVo1ChildList.add(productCategoryVo2);
                
                productCategoryVo2.setProductCategory(productCategory2);
                
                // 二级分类子集合
                List<ProductCategoryVo> productCategoryVo2ChildList = new ArrayList<ProductCategoryVo>();
                
                // 将二级分类的子集合存入二级分类对象
                productCategoryVo2.setProductCategoryVoList(productCategoryVo2ChildList);
                
                //根据二级分类编号查询三级分类的集合
                List<ProductCategory> productCategory3List = getProductCategories(productCategory2.getId());
                
                // 遍历三级分类集合
                for (ProductCategory productCategory3 : productCategory3List) {
                	
                    ProductCategoryVo productCategoryVo3 = new ProductCategoryVo();
                    
                    productCategoryVo3.setProductCategory(productCategory3);
                    
                    // 将三级分类对象存入二级分类子集合
                    productCategoryVo2ChildList.add(productCategoryVo3);
                }
            }
            
            // 将一级分类的子集合放入一级分类
            productCategoryVo.setProductCategoryVoList(productCategoryVo1ChildList);
            
            productCategory1VoList.add(productCategoryVo);
        }
        
        // 返回一级分类集合
        return productCategory1VoList;
    }
    /**
     * 根据父类编号查询所有子商品分类
     */
    private List<ProductCategory> getProductCategories(Integer parentId) {
    	
        Connection connection = null;
        
        List<ProductCategory> productCategoryList = null;
        
        try {
        	
        	// 获得数据库连接
            connection = DataSourceUtil.openConnection();
            
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            
            ProductCategoryParam params = new ProductCategoryParam();
            
            // 上一级类编号不为空
            if (EmptyUtils.isNotEmpty(parentId)) {
            	
            	// 设置上一级类编号
            	params.setParentId(parentId);
            	
            // 上一级编号为空
            } else {
            	
            	params.setParentId(0);
            }
            
            // 根据父类编号查询所属商品分类
            productCategoryList = productCategoryDao.queryProductCategorylist(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtil.closeConnection(connection);
            return productCategoryList;
        }
    }
}
