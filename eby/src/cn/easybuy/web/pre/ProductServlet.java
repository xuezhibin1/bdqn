package cn.easybuy.web.pre;

import cn.easybuy.entity.Product;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.ProductCategoryVo;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 */
@WebServlet(urlPatterns = {"/Product"}, name = "Product")
public class ProductServlet extends AbstractServlet {

    private ProductService productService;
    private ProductCategoryService productCategoryService;


    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        productCategoryService=new ProductCategoryServiceImpl();
    }

    /**
     * 查询商品列表
     */
    public String queryProductList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String category = request.getParameter("category");
        String levelStr = request.getParameter("level");
        String currentPageStr = request.getParameter("currentPage");
        String keyWord = request.getParameter("keyWord");
        //获取页大小
        String pageSizeStr = request.getParameter("pageSize");
        int rowPerPage = EmptyUtils.isEmpty(pageSizeStr) ? 20:Integer.parseInt(pageSizeStr);
        int currentPage = EmptyUtils.isEmpty(currentPageStr) ? 1 : Integer.parseInt(currentPageStr);
        int  level=EmptyUtils.isNotEmpty(levelStr)?Integer.parseInt(levelStr):0;
        int total = productService.count(keyWord, EmptyUtils.isEmpty(category)?null:Integer.valueOf(category), level);
        Pager pager = new Pager(total, rowPerPage, currentPage);
        pager.setUrl("/Product?action=queryProductList&level="+level+"&category="+(EmptyUtils.isEmpty(category)?"":category));
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllProductCategoryList();
        List<Product> productList = productService.getProductList(currentPage, rowPerPage, keyWord, EmptyUtils.isEmpty(category)?null:Integer.valueOf(category), level);
        request.setAttribute("productList", productList);
        request.setAttribute("pager", pager);
        request.setAttribute("total", total);
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("productCategoryVoList", productCategoryVoList);
        return "/pre/product/queryProductList";
    }
    /**
     *
     * @param request
     * @param response
     * @return
     */
    public String queryProductDeatil(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	// 获得页面编号
        String id = request.getParameter("id");
        
        // 根据编号获得商品信息
        Product product = productService.getProductById(Integer.valueOf(id));
        
        // 获得全部商品分类信息
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllProductCategoryList();
        
        // 保存商品
        request.setAttribute("product", product);
        
        // 保存商品分类
        request.setAttribute("productCategoryVoList", productCategoryVoList);
        
        addRecentProduct(request,product);
        
        return "/pre/product/productDeatil";
    }
    /**
     * 查询最近浏览商品
     */
    private List<Product> queryRecentProducts(HttpServletRequest request)throws Exception{
    	
        HttpSession session=request.getSession();
        
        // 获得页面浏览的商品信息
        List<Product> recentProducts= (List<Product>) session.getAttribute("recentProducts");
        
        if(EmptyUtils.isEmpty(recentProducts)){
            recentProducts=new ArrayList<Product>();
        }
        
        return recentProducts;
    }
    /**
     * 添加最近浏览商品
     */
    private void addRecentProduct(HttpServletRequest request,Product product)throws Exception{
    	
        HttpSession session=request.getSession();
        
        // 获得最近浏览商品信息
        List<Product> recentProducts=queryRecentProducts(request);
        
        //判断是否满了
        if(recentProducts.size()>0 &&  recentProducts.size()==10){
        	
          // 移除最先浏览的
          recentProducts.remove(0);
        }
        recentProducts.add(recentProducts.size(),product);
        
        // 保存最近浏览商品
        session.setAttribute("recentProducts",recentProducts);
    }

    @Override
    public Class getServletClass() {
        return ProductServlet.class;
    }
}
