package cn.easybuy.web.backend;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.param.ProductCategoryParam;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.utils.Constants;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.Params;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理商品分类
 */
@WebServlet(urlPatterns = { "/admin/productCategory" }, name = "adminProductCategory")
public class AdminProductCategoryServlet extends AbstractServlet{

    private ProductCategoryService productCategoryService;
    
    private ProductService productService;

    /**
     * 初始化实现类
     */
    public void init() throws ServletException {
        this.productCategoryService = new ProductCategoryServiceImpl();
        this.productService=new ProductServiceImpl();
    }
    
    /**
     * 订单的主页面
     */
    public String index(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	
        //获取当前页数
        String currentPageStr = request.getParameter("currentPage");
        
        //获取每页显示条数
        String pageSize = request.getParameter("pageSize");
        
        // 默认
        int rowPerPage  = EmptyUtils.isEmpty(pageSize)?8:Integer.parseInt(pageSize);
        int currentPage = EmptyUtils.isEmpty(currentPageStr)?1:Integer.parseInt(currentPageStr);
        
        ProductCategoryParam params =new ProductCategoryParam();
        
        // 商品分类总数
        int total=productCategoryService.queryProductCategoryCount(params);
        
        Pager pager=new Pager(total,rowPerPage,currentPage);
        
        params.openPage((pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
        
        pager.setUrl("/admin/productCategory?action=index");
        
        List<ProductCategory> productCategoryList=productCategoryService.queryProductCategorylistBySql(params);
        
        // 保存信息
        request.setAttribute("productCategoryList", productCategoryList);
        request.setAttribute("pager", pager);
        
        request.setAttribute("menu", 4);
        
        return "/backend/productCategory/productCategoryList";
    }
    
    /**
     * 分类添加商品
     */
    public String toAddProductCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	
        //查询一级分类 全部
        List<ProductCategory> productCategoryList=null;
        
        ProductCategoryParam params =new ProductCategoryParam();
        
        // 设为一级分类
        params.setType(1);
        
        productCategoryList=productCategoryService.queryProductCategoryList(params);
        
        // 保存一级分类
        request.setAttribute("productCategoryList1",productCategoryList);
        
        request.setAttribute("productCategory",new ProductCategory());
        
        // 跳转到添加商品分类的页面
        return "/backend/productCategory/toAddProductCategory";
    }
    /**
     * 修改商品分类
     */
    public String toUpdateProductCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String id=request.getParameter("id");
        ProductCategory productCategory=productCategoryService.getById(Integer.parseInt(id));
        List<ProductCategory> productCategoryList1=null;
        List<ProductCategory> productCategoryList2=null;
        List<ProductCategory> productCategoryList3=null;
        request.setAttribute("productCategory",productCategory);
        //判断分类级别
        if(productCategory.getType()>=1){
        	ProductCategoryParam params =new ProductCategoryParam();
        	params.setType(1);
            productCategoryList1=productCategoryService.queryProductCategoryList(params);
        }
        if(productCategory.getType()>=2){
        	ProductCategoryParam params =new ProductCategoryParam();
        	params.setType(2);
            productCategoryList2=productCategoryService.queryProductCategoryList(params);
            request.setAttribute("parentProductCategory",productCategoryService.getById(productCategory.getParentId()));
        }
        if(productCategory.getType()>=3){
            List<ProductCategory> productCategoryList=null;
            ProductCategoryParam params =new ProductCategoryParam();
            params.setType(3);
            productCategoryList3=productCategoryService.queryProductCategoryList(params);
        }
        request.setAttribute("productCategoryList1",productCategoryList1);
        request.setAttribute("productCategoryList2",productCategoryList2);
        request.setAttribute("productCategoryList3",productCategoryList3);
        return "/backend/productCategory/toAddProductCategory";
    }

    /**
     * 查询子分类
     */
    public ReturnResult queryProductCategoryList(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	
        ReturnResult result=new ReturnResult();
        
        // 获得父级分类编号
        String parentId=request.getParameter("parentId");
        
        List<ProductCategory> productCategoryList=null;
        
        ProductCategoryParam params =new ProductCategoryParam();
        
        // 设置父级分类编号
        params.setParentId(EmptyUtils.isEmpty(parentId)?0:Integer.parseInt(parentId));
        
        productCategoryList=productCategoryService.queryProductCategoryList(params);
        
        // 查询成功
        result.setStatus(Constants.ReturnResult.SUCCESS);
        
        result.setData(productCategoryList);
        
        return result;
    }
    
    /**
     * 修改商品分类
     */
    public ReturnResult modifyProductCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
        ReturnResult result=new ReturnResult();
        Integer parentId=0;
        
        // 获得商品编号
        String id=request.getParameter("id");
        
        // 获得一级分类
        String productCategoryLevel1=request.getParameter("productCategoryLevel1");
        
        // 获得二级分类
        String productCategoryLevel2=request.getParameter("productCategoryLevel2");
        
        String name=request.getParameter("name");
        
        // 获得分类级别
        String type=request.getParameter("type");
        
        // 一级分类
        if(type.equals("1")){
            parentId =0;
        }
        
        // 二级分类
        else if(type.equals("2")){
            parentId =Integer.parseInt(productCategoryLevel1);
        }
        
        // 三级分类
        else{
            parentId =Integer.parseInt(productCategoryLevel2);
        }
        
        ProductCategory productCategory  =new ProductCategory();
        
        // 设置分类
        productCategory.setId(Integer.parseInt(id));
        productCategory.setParentId(parentId);
        productCategory.setName(name);
        productCategory.setType(Integer.parseInt(type));
        
        productCategoryService.modifyProductCategory(productCategory);
        
        result.returnSuccess();
        
        // 修改成功
        return result;
    }
    
    /**
     * 添加商品分类
     */
    public ReturnResult addProductCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	
        ReturnResult result=new ReturnResult();
        
        // 父级分类编号
        Integer parentId=0;
        
        //获取分类级别
        String type=request.getParameter("type");
        
        // 获得一级分类
        String productCategoryLevel1=request.getParameter("productCategoryLevel1");
        
        // 获得二级分类
        String productCategoryLevel2=request.getParameter("productCategoryLevel2");
        
        // 获得分类名称
        String name=request.getParameter("name");
        
        // 一级分类
        if(type.equals("1")){
            parentId =0;
        }
        
        // 二级分类
        else if(type.equals("2")){
            parentId =Integer.parseInt(productCategoryLevel1);
        }
        
        // 三级分类
        else{
            parentId =Integer.parseInt(productCategoryLevel2);
        }
        ProductCategory productCategory =new ProductCategory();
        
        // 添加商品分类信息
        productCategory.setName(name);
        productCategory.setParentId(parentId);
        productCategory.setType(Integer.parseInt(type));
        productCategory.setIconClass("");
        
        // 新增商品分类信息
        productCategoryService.addProductCategory(productCategory);
        
        // 添加成功
        result.returnSuccess();
        
        return result;
    }
    /**
     * 删除商品分类
     */
    public ReturnResult deleteProductCategory(HttpServletRequest request,HttpServletResponse response)throws Exception{
        ReturnResult result=new ReturnResult();
        //获取分类id
        String id=request.getParameter("id");
        String type=request.getParameter("type");
        //查询是否有子类
        ProductCategoryParam productCategoryParam=new ProductCategoryParam();
        productCategoryParam.setParentId(Integer.parseInt(id));
        int count=productCategoryService.queryProductCategoryCount(productCategoryParam);
        if(count>0){
        	return result.returnFail("已经存在子分类，不能删除");
        }
        //查询是否有商品
        count=productService.count(null,Integer.parseInt(id),null);
        if(count>0){
        	return result.returnFail("已经存在商品，不能删除");
        }
        productCategoryService.deleteById(Integer.parseInt(id));
        result.returnSuccess();
        return result;
    }

    @Override
    public Class getServletClass() {
        return AdminProductCategoryServlet.class;
    }
}
