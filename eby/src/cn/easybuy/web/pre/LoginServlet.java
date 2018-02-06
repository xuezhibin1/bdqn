package cn.easybuy.web.pre;
import cn.easybuy.entity.User;
import cn.easybuy.service.user.UserService;
import cn.easybuy.service.user.UserServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.utils.SecurityUtils;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 */
@WebServlet(urlPatterns = { "/Login" }, name = "Login")
public class LoginServlet extends AbstractServlet{
	
    // 用户业务类
    private UserService userService;

    /**
     * 初始化用户实现类
     */
    public void init() throws ServletException {
    	
        userService = new UserServiceImpl();
    }
    
    /**
     * 加载自身到内存
     */
    @Override
    public Class getServletClass() {
    	
        return LoginServlet.class;
    }
    /**
     * 跳转到登陆界面
     */
    public String toLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
        return "/pre/login";
    }
    
    /**
     * 登陆的方法
     */
    public ReturnResult login(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	
        ReturnResult result=new ReturnResult();
        
        // 获取login.jsp的登录信息
        String loginName=request.getParameter("loginName");
        String password=request.getParameter("password");
        
        User user=userService.getUser(null, loginName);
        
        // 输入了空值或者未注册的
        if(EmptyUtils.isEmpty(user)){
        	
            result.returnFail("用户不存在");
        }
        
        // 已注册用户
        else{
        	
           // 密码正确
           if(user.getPassword().equals(SecurityUtils.md5Hex(password))){
        	   
               // 保存用户信息
               request.getSession().setAttribute("loginUser", user);
               
               result.returnSuccess("登陆成功");
           }
           
           // 密码错误
           else{
        	   
               result.returnFail("密码错误");
           }
        }
        
        // 返回登录结果
        return result;
    }
    /**
     * 注销的方法
     */
    public String loginOut(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	
        ReturnResult result=new ReturnResult();
        
        try {
        	
        	// 获得用户登录信息
            User user=(User)request.getSession().getAttribute("loginUser");
            
            // 移除用户登录信息
            request.getSession().removeAttribute("loginUser");
            
            // 清除购物车
            request.getSession().removeAttribute("cart");
            request.getSession().removeAttribute("cart2");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.returnSuccess("注销成功");
        
        // 返回登录界面
        return "/pre/login";
    }
}
