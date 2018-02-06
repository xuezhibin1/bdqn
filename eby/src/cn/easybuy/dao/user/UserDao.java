package cn.easybuy.dao.user;

import java.sql.SQLException;
import java.util.List;

import cn.easybuy.dao.IBaseDao;
import cn.easybuy.entity.User;
import cn.easybuy.param.UserParam;

import org.apache.ibatis.annotations.Param;

/**
 * 用户业务的dao层
 */
public interface UserDao extends IBaseDao {

	/**
	 * 新增用户
	 */
	int add(User user_new) throws Exception;

	/**
	 * 修改用户信息
	 */
	int update(User user) throws Exception;

	/**
	 * 删除用户信息
	 */
	public int deleteUserById(String id) throws Exception;
	
	/**
	 * 分页显示用户信息                                                                                                                                                                                                                      
	 */
	public List<User> getUserList(@Param("currentPageNo")Integer currentPageNo,
								  @Param("pageSize")Integer pageSize)throws Exception;
	
	/**
	 * 获得用户数量
	 */
	public Integer count() throws Exception;
	
	/**
	 * 根据登录名查询用户信息
	 */
	public User getUser(@Param("id")Integer id,
						@Param("loginName")String loginName) throws Exception;
}
