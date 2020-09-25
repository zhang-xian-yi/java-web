package cn.tianfu.EuqipManageSystem.service.impl;

import java.util.jar.Attributes.Name;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import cn.tianfu.EuqipManageSystem.dao.mybatis.dao.UserMapper;
import cn.tianfu.EuqipManageSystem.entity.pojo.User;
import cn.tianfu.EuqipManageSystem.exception.ExistException;
import cn.tianfu.EuqipManageSystem.exception.ExistException.NAME;
import cn.tianfu.EuqipManageSystem.exception.ExistException.STATE;
import cn.tianfu.EuqipManageSystem.service.ServiceManager;
import cn.tianfu.EuqipManageSystem.utils.MergeUtils;
import cn.tianfu.EuqipManageSystem.utils.SingletonSessionFacUtils;

@Service("userManager")
//@EnableAspectJAutoProxy
public class UserManager implements ServiceManager
{
	/*
     * login 
     * param user
     * */
	@Override
    public Object getObj(Object _user_) throws ExistException
    {
		User user_ = (User)_user_;
    	SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
		UserMapper dao = session.getMapper(UserMapper.class);
    	User data_user = (User) dao.find(_user_);
    	session.close();

    	//if gei the user from database is null then return null
    	if(data_user == null)
    	{
    		ExistException exist = new ExistException(NAME.USER,STATE.NOEXIST,"the user is not exist");
    		throw exist;
			//System.out.println("data base user: is null");
    		//return STATE.NOEXIST;
    	}

    	//如果存在这个对象
    	//经过md5  加密之后的密码 值 如果相同 说明是同一个密码
		if(user_.getPassword().equals(data_user.getPassword()))
    	{
			MergeUtils.mergeAttribute((User)_user_, data_user);
    		return user_;
    	}
    	else 
    	{
			return null;
		}
    }

	
	@Override
	public Object updateObj(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteObj(Object obj) 
	{
		User user_ = (User)obj;
		SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
		UserMapper dao = session.getMapper(UserMapper.class);
    	//登录之后 注销用户 可以直接进行 因为之前已经验证过身份了
		Integer num = dao.delete(user_);
		session.commit();
    	session.close();
    	return num;
	}

	@Override
	public Object addObj(Object obj) throws ExistException
	{
		// TODO Auto-generated method stub
		User user = (User)obj; 
    	//default privaliage
    	user.setAuthority(1);
    	SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
    	UserMapper dao = session.getMapper(UserMapper.class);
    	//不存在user  add return >0 mean succ not 0 means failed
    	Integer num = dao.add(user);
    	session.commit();
		session.close();
    	return num;
	}
}
