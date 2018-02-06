package cn.easybuy.dao.user;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import cn.easybuy.entity.User;
import cn.easybuy.utils.MyBatisUtil;
import cn.easybuy.utils.Pager;

public class UserDaoTest {

	private Logger logger = Logger.getLogger(UserDaoTest.class);
	
	SqlSession sqlSession = MyBatisUtil.createSqlSession();	
	
	List<User> userList = new ArrayList<User>();
	
	User user = new User();
	
	/**
	 * 根据登录名查询用户信息
	 * @throws Exception 
	 */
	@Test
	public void testGetUser() throws Exception {
		
		Integer id = 19;
		
		String loginName = "liguangliang";
		
		user = sqlSession.getMapper(UserDao.class).getUser(id, loginName);
	
		logger.debug(user.getLoginName() + "   "
						+ user.getUserName());
	}
	
	/**
	 * 添加用户
	 * @throws Exception 
	 */
	@Test
	public void testAdd() throws Exception {
		
		user.setLoginName("sunjian");
		user.setUserName("孙剑");
		user.setPassword("111111");
		user.setSex(1);
		user.setIdentityCode("320911199106235339");
		user.setEmail("18851315521@139.com");
		user.setMobile("18851315521");
		
		int row = sqlSession.getMapper(UserDao.class).add(user);
		
		sqlSession.commit();
		
		sqlSession.rollback();
		
		MyBatisUtil.closeSqlSession(sqlSession);
		
		logger.debug(row);
	}
	
	/**
	 * 分页显示用户信息
	 * @throws Exception 
	 */
	@Test
	public void testGetUserList() throws Exception {
		
		SqlSession sqlSession = MyBatisUtil.createSqlSession();
		
		// 用户总数
		int total = 13;
		
		Integer pageSize = 10;
		
		Integer currentPageNo = 1;
		
		Pager pager = new Pager(total, pageSize, currentPageNo);
		
		// 起始数
		currentPageNo = (pager.getCurrentPage() - 1) * pager.getRowPerPage();
		
		// 每页显示条数
		pageSize = pager.getRowPerPage();
		
		List<User> userList = sqlSession.getMapper(UserDao.class).getUserList(currentPageNo, pageSize);
	
		MyBatisUtil.closeSqlSession(sqlSession);
		
		for (User user : userList) {
			
			logger.debug(user.getLoginName() + "   "
					+ user.getUserName());
		}
	}
	
	/**
	 * 更新用户信息
	 * @throws Exception 
	 */
	@Test
	public void testUpdate() throws Exception {
		
SqlSession sqlSession = MyBatisUtil.createSqlSession();
		
		int row = 0;
		
		User user = new User();
		
		user.setUserName("test");
		user.setPassword("12121212");
		user.setSex(1);
		user.setIdentityCode("21212121212");
		user.setEmail("12122");
		user.setMobile("1212121");
		user.setId(23);
		user.setType(0);
		
		row = sqlSession.getMapper(UserDao.class).update(user);
		
		sqlSession.commit();
		
		sqlSession.rollback();
		
		MyBatisUtil.closeSqlSession(sqlSession);
		
		logger.debug(row);
	}
}

























