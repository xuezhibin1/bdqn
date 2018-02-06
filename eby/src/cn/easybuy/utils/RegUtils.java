package cn.easybuy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 */
public class RegUtils {

	// 邮箱正则
	static String emailReg="^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
	
	// 手机正则
	static String mobileReg="^\\d{5,11}$";
	
	// 身份证号正则
	static String identityCodeReg="^\\w{18}$";
	
	/**
	 * 验证邮箱
	 */
	public static boolean checkEmail(String email){
		
		// 验证正则
		Pattern pattern=Pattern.compile(emailReg);		
		Matcher matcher=pattern.matcher(email);		
		System.out.println(matcher.matches());
		
		return matcher.matches();
	}
	
	/**
	 * 验证手机号
	 */
	public static boolean checkMobile(String mobile){
		
		// 验证正则
		Pattern pattern=Pattern.compile(mobileReg);
		Matcher matcher=pattern.matcher(mobile);
		System.out.println(matcher.matches());
		
		return matcher.matches();
	}
	
	/**
	 * 验证身份证号
	 */
	public static boolean checkIdentityCodeReg(String identityCode){
		
		// 验证正则
		Pattern pattern=Pattern.compile(identityCodeReg);
		Matcher matcher=pattern.matcher(identityCode);
		System.out.println(matcher.matches());
		
		return matcher.matches();
	}
	
}
