/**
 * 登录的方法
 */
function login(){
	
	// 获得页面输入的信息
    var loginName=$("#loginName").val();
    var password=$("#password").val();
    
    $.ajax({
    	
    	// LoginServlet路径
        url:contextPath+"/Login",
        
        method:"post",
        
        data:{	
        		loginName	:	loginName,        
        		password	:	password,
        		action		:	"login"
        },        
        
        success:function(jsonStr){
            var result=eval("("+jsonStr+")");
            if(result.status==1){
            	
            	// 登录成功后跳转首页
                window.location.href=contextPath+"/Home?action=index";
                
            }else{
                showMessage(result.message)
            }
        }
    })
}