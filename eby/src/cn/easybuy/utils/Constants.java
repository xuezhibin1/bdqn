package cn.easybuy.utils;

/**
 * 常量工具
 */
public class Constants {
	
    /**
     * 操作成功或者失败
     */
    public static interface ReturnResult{
    	
        public static int SUCCESS=1;
        public static int FAIL=-1;
    }
    
    /**
     * 前后台用户
     */
    public static interface  UserType{
    	
        public static int PRE=0;
        public static int BACKEND=1;
    }
}
