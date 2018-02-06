package cn.easybuy.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 判断是否是空的工具类
 */
public class EmptyUtils {

	/**
	 * 判断是否是空
	 */
	public static boolean isEmpty(Object obj) {

		if (obj == null)
			return true;

		// 不是字符串
		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		// 是集合
		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		// 是键值对
		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		// 是数组
		if (obj instanceof Object[]) {
			
			Object[] object = (Object[]) obj;

			// 数组长度为0
			if (object.length == 0) {
				return true;
			}
			
			boolean empty = true;
			
			// 遍历数组
			for (int i = 0; i < object.length; i++) {
				
				// 数组中没有空值
				if (!isEmpty(object[i])) {
					
					empty = false;
					
					break;
				}
			}
			
			// 返回是空值
			return empty;
		}
		
		// 返回不是空值
		return false;
	}

	public static boolean isNotEmpty(Object obj) {
		
		return !isEmpty(obj);
	}

	private boolean validPropertyEmpty(Object... args) {
		
		for (int i = 0; i < args.length; i++) {
			if (EmptyUtils.isEmpty(args[i])) {
				return true;
			}
		}
		return false;
	}
}
