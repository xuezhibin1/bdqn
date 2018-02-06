package cn.easybuy.service.user;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cn.easybuy.dao.order.UserAddressDao;
import cn.easybuy.dao.order.UserAddressDaoImpl;
import cn.easybuy.dao.user.UserDao;
import cn.easybuy.dao.user.UserDaoImpl;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.MyBatisUtil;
import cn.easybuy.utils.Pager;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import cn.easybuy.entity.User;

public class UserServiceImpl implements UserService {
	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	/**
	 * 添加用户
	 * @throws Exception 
	 */
	@Override
	public boolean add(User user_new) throws Exception{
		SqlSession sqlSession = MyBatisUtil.createSqlSession();
		/*Connection connection = null;
		Integer count=0;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			
			// 添加用户
			count=userDao.add(user);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DataSourceUtil.closeConnection(connection);
			return  count>0;
		}*/
		User user = new User();
		user.setLoginName(user_new.getLoginName());
		
		// System.out.println(user.getLoginName());
		
		user.setUserName(user_new.getUserName());
		user.setPassword(user_new.getPassword());
		user.setSex(user_new.getSex());
		user.setIdentityCode(user_new.getIdentityCode());
		user.setEmail(user_new.getEmail());
		user.setMobile(user_new.getMobile());
		
		int row = sqlSession.getMapper(UserDao.class).add(user);
		
		sqlSession.commit();		
		
		MyBatisUtil.closeSqlSession(sqlSession);
		
		return row > 0;
	}

	/**
	 * 更新用户信息
	 * @throws Exception 
	 */
	@Override
	public boolean update(User user_new) throws Exception {
		
		/*Connection connection = null;
		Integer count=0;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			count=userDao.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DataSourceUtil.closeConnection(connection);
			return  count>0;
		}*/
		
		SqlSession sqlSession = MyBatisUtil.createSqlSession();
		
		int row = 0;
		
		User user = new User();
		
		user.setUserName(user_new.getUserName());
		user.setPassword(user_new.getPassword());
		user.setSex(user_new.getSex());
		user.setIdentityCode(user_new.getIdentityCode());
		user.setEmail(user_new.getEmail());
		user.setMobile(user_new.getMobile());
		user.setId(user_new.getId());
		user.setType(user_new.getType());
		
		row = sqlSession.getMapper(UserDao.class).update(user);
		
		sqlSession.commit();
		
		sqlSession.rollback();
		
		MyBatisUtil.closeSqlSession(sqlSession);
		
		return row > 0;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		Connection connection = null;
		Integer count=0;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			count=userDao.deleteUserById(userId+"");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DataSourceUtil.closeConnection(connection);
			return  count>0;
		}
	}

	/**
	 * 查询用户信息
	 * @throws Exception 
	 */
	@Override
	public User getUser(Integer userId, String loginName) throws Exception {
		
		/*Connection connection = null;
		User user=null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			user=userDao.getUser(userId,loginName);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DataSourceUtil.closeConnection(connection);
			return user;
		}*/
		User user = new User();
		SqlSession sqlSession = MyBatisUtil.createSqlSession();
		user = sqlSession.getMapper(UserDao.class).getUser(userId, loginName);
		
		MyBatisUtil.closeSqlSession(sqlSession);
		
		return user;
	}

	@Override
	public List<User> getUserList(Integer currentPageNo, Integer pageSize) throws Exception {
		/*Connection connection = null;
		List<User> userList=null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userList=userDao.getUserList(currentPageNo,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DataSourceUtil.closeConnection(connection);
			return userList;
		}*/
		
		SqlSession sqlSession = MyBatisUtil.createSqlSession();
		
		// 用户总数
		int total = count();
		
		Pager pager = new Pager(total, pageSize, currentPageNo);
		
		// 起始数
		currentPageNo = (pager.getCurrentPage() - 1) * pager.getRowPerPage();
		
		// 每页显示条数
		pageSize = pager.getRowPerPage();
		
		List<User> userList = sqlSession.getMapper(UserDao.class).getUserList(currentPageNo, pageSize);
	
		MyBatisUtil.closeSqlSession(sqlSession);
		
		return userList;
	}

	/**
	 * 获得用户数量                                                                                        
	 * @throws Exception 
	 */
	@Override
	public int count() throws Exception {
		
		/*Connection connection = null;
		Integer count=null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			count=userDao.count();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DataSourceUtil.closeConnection(connection);
			return count;
		}*/		
		
		SqlSession sqlSession = MyBatisUtil.createSqlSession();
		
		// 用户数量
		Integer count = sqlSession.getMapper(UserDao.class).count();
	
		MyBatisUtil.closeSqlSession(sqlSession);
		
		return count;
	}
}























