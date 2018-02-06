package cn.easybuy.web.pre;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.easybuy.entity.News;
import cn.easybuy.entity.Product;
import cn.easybuy.service.news.NewsService;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.news.NewsServiceImpl;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.Params;
import cn.easybuy.utils.ProductCategoryVo;
import cn.easybuy.web.AbstractServlet;

/**
 * 商城主页
 * 从起始页index.jsp进入
 */
@WebServlet(urlPatterns = {"/Home"}, name = "Home")
public class HomeServlet extends AbstractServlet {

    private ProductService productService;
    private NewsService newsService;
    private ProductCategoryService productCategoryService;

    /**
     * 初始化获得实现类对象
     */
    public void init() throws ServletException {
    	
        productService = new ProductServiceImpl();
        newsService = new NewsServiceImpl();
        productCategoryService = new ProductCategoryServiceImpl();
    }

    /**
     * 显示商品分类并返回首页
     */
    public String index(HttpServletRequest request, HttpServletResponse response)throws Exception {
    	
        // 查询商品分类
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllProductCategoryList();
        
        List<News> news = newsService.getAllNews(new Pager(5, 5, 1));
        
        // 查询一级分类
        for (ProductCategoryVo vo : productCategoryVoList) {
            List<Product> productList = productService.getProductList(1, 8, null, vo.getProductCategory().getId(), 1);
            vo.setProductList(productList);
        }
        // 保存商品分类集合
        request.setAttribute("productCategoryVoList", productCategoryVoList);
        
        // 保存新闻集合
        request.setAttribute("news", news);
        
        // 返回首页
        return "/pre/index";
    }

    /**
     * 加载自身到内存
     */
    @Override
    public Class getServletClass() {
        return HomeServlet.class;
    }
}
