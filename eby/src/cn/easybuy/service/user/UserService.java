package cn.easybuy.service.user;
import cn.easybuy.entity.User;
import java.util.List;

public interface UserService {

	public boolean add(User user) throws Exception;
	
	public boolean update(User user) throws Exception;
	
	public boolean deleteUserById(Integer userId);
	
	public User getUser(Integer userId,String loginName) throws Exception;
	
	public List<User> getUserList(Integer currentPageNo,Integer pageSize) throws Exception;
	
	public int count() throws Exception;
}
