package cn.easybuy.web.pre;

import cn.easybuy.entity.User;
import cn.easybuy.service.user.UserService;
import cn.easybuy.service.user.UserServiceImpl;
import cn.easybuy.utils.Constants;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.RegUtils;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.utils.SecurityUtils;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册
 */
@WebServlet(urlPatterns = {"/Register"}, name = "Register")
public class RegisterServlet extends AbstractServlet {

    private UserService userService;

    /**
     * 初始化实现类
     */
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    /**
     * 加载自身到内存
     */
    @Override
    public Class getServletClass() {
        return RegisterServlet.class;
    }

    /**
     * 跳到注册页面
     */
    public String toRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/pre/register";
    }

    /**
     * 保存注册用户信息
     */
    public ReturnResult saveUserToDatabase(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReturnResult result = new ReturnResult();
        try {
            User user = new User();
            
            // 获得注册的登录名
            String loginName = request.getParameter("loginName");
            
            // 获得注册的登录性别
            String sex = request.getParameter("sex");
            
            // 验证登录名是否已经被注册
            User oldUser = userService.getUser(null, loginName);
            
            // 登录名已经存在
            if (EmptyUtils.isNotEmpty(oldUser)) {
            	
                result.returnFail("用户已经存在");
                return result;
            }
            
            // 将注册信息放入实体类中
            user.setLoginName(request.getParameter("loginName"));
            user.setUserName(request.getParameter("userName"));
            user.setSex(EmptyUtils.isEmpty(sex) ? 1 : 0);
            user.setPassword(SecurityUtils.md5Hex(request.getParameter("password")));
            user.setIdentityCode(request.getParameter("identityCode"));
            user.setEmail(request.getParameter("email"));
            user.setMobile(request.getParameter("mobile"));
            user.setType(Constants.UserType.PRE);
            
            // 验证格式
            result=checkUser(user);
            
            // 是否验证通过
            if(result.getStatus()==Constants.ReturnResult.SUCCESS){
            	
            	 // 添加用户并判断
            	 if(!userService.add(user)){
            		 
                	 return result.returnFail("注册失败！");
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.returnSuccess("注册成功");
        return result;
    }
    
    /**
     * 验证格式
     */
    private ReturnResult checkUser(User user){
    	
    	ReturnResult result = new ReturnResult();
    	
    	boolean flag=true;
    	
    	if(EmptyUtils.isNotEmpty(user.getMobile())){
    		
    		if(!RegUtils.checkMobile(user.getMobile())){
    			return result.returnFail("手机格式不正确");
    		}
    	}
    	
    	if(EmptyUtils.isNotEmpty(user.getIdentityCode())){
    		if(!RegUtils.checkIdentityCodeReg(user.getIdentityCode())){
    			return result.returnFail("身份证号码不正确");
    		}
    	}
    	
    	if(EmptyUtils.isNotEmpty(user.getEmail())){
    		if(!RegUtils.checkEmail(user.getEmail())){
    			return result.returnFail("邮箱格式不正确");
    		}
    	}
    	return result.returnSuccess();
    }
}
