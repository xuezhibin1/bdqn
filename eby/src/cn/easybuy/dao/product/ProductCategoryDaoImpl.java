package cn.easybuy.dao.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.dao.product.ProductCategoryDao;
import cn.easybuy.entity.Product;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.entity.User;
import cn.easybuy.param.ProductCategoryParam;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Params;

/**
 * 商品分类实现类
 */
public class ProductCategoryDaoImpl extends BaseDaoImpl implements ProductCategoryDao {

	public ProductCategoryDaoImpl(Connection connection) {
		super(connection);
	}

	/**
	 * 将数据库信息存入实体类对象中
	 */
	@Override
	public ProductCategory tableToClass(ResultSet rs) throws Exception {
		
		ProductCategory productCategory = new ProductCategory();
		
		productCategory.setId(rs.getInt("id"));
		productCategory.setName(rs.getString("name"));
		productCategory.setParentId(rs.getInt("parentId"));
		productCategory.setType(rs.getInt("type"));
		productCategory.setIconClass(rs.getString("iconClass"));
		
		return productCategory;
	}
	
	/**
	 * 将集合中的信息放入商品分类实体类
	 */
	public ProductCategory mapToClass(Map map) throws Exception {
		
		ProductCategory productCategory = new ProductCategory();
		
		// 获得集合中的信息
		Object idObject=map.get("id");
		Object nameObject=map.get("name");
		Object parentIdObject=map.get("parentId");
		Object typeObject=map.get("type");
		Object iconClassObject=map.get("iconClass");
		Object parentNameObject=map.get("parentName");
		
		// 将集合中的信息放入商品分类实体类
		productCategory.setId(EmptyUtils.isEmpty(idObject)?null:(Integer)idObject);
		productCategory.setName(EmptyUtils.isEmpty(nameObject)?null:(String)nameObject);
		productCategory.setParentId(EmptyUtils.isEmpty(parentIdObject)?null:(Integer)parentIdObject);
		productCategory.setType(EmptyUtils.isEmpty(typeObject)?null:(Integer)typeObject);
		productCategory.setIconClass(EmptyUtils.isEmpty(iconClassObject)?null:(String)iconClassObject);
		productCategory.setParentName(EmptyUtils.isEmpty(parentNameObject)?null:(String)parentNameObject);
		
		return productCategory;
	}
	
	/**
	 * 根据父类编号和类名查询出所有子商品类
	 */
	public List<ProductCategory> queryAllProductCategorylist(ProductCategoryParam params){
		
		List<ProductCategory> list=new ArrayList<ProductCategory>();
		List<Object> paramsList=new ArrayList<Object>();
		StringBuffer sqlBuffer=new StringBuffer("SELECT epc1.*,epc2.name as parentName FROM easybuy_product_category epc1 LEFT JOIN easybuy_product_category epc2 ON epc1.parentId=epc2.id where 1=1 ");
		ResultSet resultSet=null;
		try{
			if(EmptyUtils.isNotEmpty(params.getName())){
				sqlBuffer.append(" and name like ? ");
				
				// 父类名
				paramsList.add("%"+params.getName()+"%");
			}
			if(EmptyUtils.isNotEmpty(params.getParentId())){
				sqlBuffer.append(" and parentId = ? ");
				
				// 父类编号
				paramsList.add(params.getParentId());
			}
			if(EmptyUtils.isNotEmpty(params.getSort())){
				sqlBuffer.append(" order by " + params.getSort()+" ");
			}
			
			if(params.isPage()){
				
				// 类名分页
				sqlBuffer.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
			}
			resultSet=this.executeQuery(sqlBuffer.toString(),paramsList.toArray());
			
			// 获得结果集结构
			ResultSetMetaData md=resultSet.getMetaData();
			
			Map<String,Object> rowData=new HashMap<String,Object>();
			
			// 获得结果集列数
			int count=md.getColumnCount();
			
			for(int i=1;i<=count;i++){
				rowData.put(md.getColumnLabel(i),resultSet.getObject(i));
			}
			list.add(mapToClass(rowData));
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}		
		return list;
	}

	@Override
	public void deleteById(Integer id){
		String sql = " delete from easybuy_product_category where id = ? ";
		Object params[] = new Object[] { id };
		this.executeUpdate(sql.toString(), params);	
	}

	/**
	 * 查询子分类
	 */
	@Override
	public List<ProductCategory> queryProductCategorylist(ProductCategoryParam params) {
		
		List<Object> paramsList=new ArrayList<Object>();   
		List<ProductCategory> productList=new ArrayList<ProductCategory>();
		
		// 查询分类
		StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where 1=1 ");
		
		ResultSet resultSet=null;
		try {
			if(EmptyUtils.isNotEmpty(params.getName())){
				sql.append(" and name like ? ");
				
				// 商品类名
				paramsList.add("%"+params.getName()+"%");
			}
			if(EmptyUtils.isNotEmpty(params.getParentId())){
				sql.append(" and parentId = ? ");
				
				// 父类编号
				paramsList.add(params.getParentId());
			}
			if(EmptyUtils.isNotEmpty(params.getType())){
				sql.append(" and type = ? ");
				
				// 类级别
				paramsList.add(params.getType());
			}
			if(params.isPage()){
				
				// 分页
				sql.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
			}
			resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
			while (resultSet.next()) {
				
				// 提取结果集信息至实体类对象
				ProductCategory productCategory = this.tableToClass(resultSet);
				
				productList.add(productCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return productList;
	}
	
	public Integer queryProductCategoryCount(ProductCategoryParam params){
		List<Object> paramsList=new ArrayList<Object>();   
		Integer count=0;
		StringBuffer sql=new StringBuffer("SELECT count(*) count FROM easybuy_product_category where 1=1 ");
		if(EmptyUtils.isNotEmpty(params.getName())){
			sql.append(" and name like ? ");
			paramsList.add("%"+params.getName()+"%");
		}
		if(EmptyUtils.isNotEmpty(params.getParentId())){
			sql.append(" and parentId = ? ");
			paramsList.add(params.getParentId());
		}
		ResultSet resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
		try {
			while (resultSet.next()) {
				count=resultSet.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return count;
	}
	
	public ProductCategory queryProductCategoryById(Integer id){
		List<Object> paramsList=new ArrayList<Object>();   
		ProductCategory productCategory=null;
		StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where id = ? ");
		ResultSet resultSet=this.executeQuery(sql.toString(),new Object[]{id});
		try {
			while (resultSet.next()) {
				productCategory = this.tableToClass(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return productCategory;
	}
	
	/**
	 * 新增商品分类信息
	 */
	public Integer save(ProductCategory productCategory)  {
		
    	Integer id=0;
    	
    	try {
    		String sql=" INSERT into easybuy_product_category(name,parentId,type,iconClass) values(?,?,?,?) ";
            
    		Object param[]=new Object[]{productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass()};
            
    		// 设置主键编号
            id=this.executeInsert(sql,param);
           
            // 将编号存入分类对象
            productCategory.setId(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	this.closeResource();
        }
    	return id;
    }

	/**
	 * 修改商品分类
	 */
	@Override
	public void update(ProductCategory productCategory) {
		try {
        	Object[] params = new Object[] {productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass(),productCategory.getId()};
        	
        	// 根据商品编号修改商品信息
        	String sql = " UPDATE easybuy_product_category SET name=?,parentId=?,type=?,iconClass=? WHERE id =?  ";
    		
        	this.executeUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	this.closeResource();
        }		
	}
	
	
}
