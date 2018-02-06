package cn.easybuy.web.pre;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * 商品种类
 */
@WebServlet(urlPatterns = {"/productCategory"},name = "productCategory")
public class ProductCategoryServlet extends AbstractServlet{

    private ProductService productService;

    /**
     * 初始化
     */
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
    }

    /**
     * 加载自身类
     */
    public Class getServletClass() {
        return ProductCategoryServlet.class;
    }
}
