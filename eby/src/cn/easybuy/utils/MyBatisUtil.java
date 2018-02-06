package cn.easybuy.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

	private static SqlSessionFactory factory;
	
	// 创建对象时直接初始化
	static {
		
		// 获取"mybatis-config.xml"输入流
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream("mybatis-config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// 创建SqlSessionFactory对象完成对配置文件的读取
		factory = new SqlSessionFactoryBuilder().build(is);
	}
	
	/**
	 * sql会话
	 */
	public static SqlSession createSqlSession() {
		
		return factory.openSession(false);
	}
	
	/**
	 * 关闭sql
	 */
	public static void closeSqlSession(SqlSession sqlSession) {
		
		if (sqlSession != null) {
			
			sqlSession.close();
		}
	}
}
