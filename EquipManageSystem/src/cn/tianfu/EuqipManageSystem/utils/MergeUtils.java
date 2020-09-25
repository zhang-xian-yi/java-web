package cn.tianfu.EuqipManageSystem.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.core.Logger;

import cn.tianfu.EuqipManageSystem.entity.bean.AsyncRefreshRequest;
import cn.tianfu.EuqipManageSystem.entity.bean.PageInfo;
import cn.tianfu.EuqipManageSystem.entity.bean.UserForm;
import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;
import cn.tianfu.EuqipManageSystem.entity.pojo.User;

public class MergeUtils 
{
	/*
	 * param user request
	 * merge
	 * */
	public static void mergeAttribute(UserForm userform,HttpServletRequest request)
	{
		User user = (User)SpringBeanFactory.getBean("user");
		user.setPost_box(request.getParameter("post_box"));
		user.setUser_name(request.getParameter("user_name"));
		user.setPassword(request.getParameter("password"));
		user.setSex(request.getParameter("sex"));
		user.setTelephone(request.getParameter("telephone"));
		user.setIntroduce_self(request.getParameter("introduce_self"));
		userform.setUser(user);
		
	}
	
	public static void mergeAttribute(Equipment equip,HttpServletRequest request)
	{
		//因为上传文件格式的原因 导致request  不能获取到真真意义上的参数 故采跳转方式
		equip.setName(request.getParameter("name"));
		equip.setLogin_time(new Date());
		//设置默认登出时间 不然解析 会报空指针异常
		long currentTime = System.currentTimeMillis() + 3 * 60 * 60 * 1000;
		Date date = new Date(currentTime);
		equip.setLogout_time(date);
	}
	
	public static void mergeAttribute(User user,HttpServletRequest request)
	{
		user.setPost_box(request.getParameter("post_box"));
		//将密码加密
		user.setPost_box(request.getParameter("post_box"));
		user.setUser_name(request.getParameter("user_name"));
		user.setPassword(MD5Utils.getMD5(request.getParameter("password") ) );
		user.setSex(request.getParameter("sex"));
		user.setTelephone(request.getParameter("telephone"));
		user.setIntroduce_self(request.getParameter("introduce_self"));
	}
	
	public static void mergeAttribute(User user,User data_user)
	{
		user.setPost_box(data_user.getPost_box());
		user.setUser_name(data_user.getUser_name());
		//user.setSex(data_user.getSex());
		//user.setTelephone(data_user.getTelephone());
		//user.setIntroduce_self(data_user.getIntroduce_self());
		user.setAuthority(data_user.getAuthority());
	}
	
	public static void mergeAttribute(User user,ResultSet result)throws SQLException
	{
		user.setPost_box(result.getString("post_box"));
		user.setUser_name(result.getString("user_name"));
		user.setPassword(result.getString("password"));
		user.setSex(result.getString("sex"));
		user.setTelephone(result.getString("telephone"));
		user.setAuthority(Integer.parseInt(result.getString("anthority")));
		user.setIntroduce_self(result.getString("introduce_self"));
	}
	
	public static void mergeAttribute(Equipment equip,ResultSet result)throws SQLException, ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		equip.setName(result.getString("name"));
		equip.setLogin_time(sdf.parse(result.getString("login_time")));
		equip.setLogout_time(sdf.parse(result.getString("logout_time")));
		
		String data_state = result.getString("state");
		if("working".equalsIgnoreCase(data_state))
		{
			equip.setState("WORKING");
		}
		else if("ended".equalsIgnoreCase(data_state))
		{
			equip.setState("END");
		}
		else if("broken".equalsIgnoreCase(data_state))
		{
			equip.setState("BROKEN");
		}
		
		equip.setFile_path(result.getString("file_path"));
		equip.setCategory(result.getString("category"));
	}

	public static void mergeAttribute(List<Equipment> equips, ResultSet result) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		while(result.next())
		{
			Equipment equip = new Equipment();
			mergeAttribute(equip, result);
			equips.add(equip);
		}
	}
	
	public static void mergeAttribute(PageInfo page_info,ServletRequest request)
	{
		page_info.setPage_curr(Integer.parseInt(request.getParameter("page_curr")));
		page_info.setPage_num(Integer.parseInt(request.getParameter("page_num")));
		page_info.setPage_size(Integer.parseInt(request.getParameter("page_size")));
		page_info.setObj_cursor_start((page_info.getPage_curr()-1) * page_info.getPage_size() );
	}
	
	public static void mergeAttribute(AsyncRefreshRequest asyncRespon,ServletRequest request)
	{
		PageInfo info = new PageInfo();
		
		info.setPage_curr(Integer.parseInt(request.getParameter("page_curr")));
		info.setPage_num(Integer.parseInt(request.getParameter("page_num")));
		info.setPage_size(Integer.parseInt(request.getParameter("page_size")));
		info.setObj_cursor_start((info.getPage_curr()-1) * info.getPage_size() );
		
		asyncRespon.setPage_info(info);
		
		asyncRespon.setE_name(request.getParameter("e_name"));
		asyncRespon.setE_status(request.getParameter("e_status"));
		asyncRespon.setE_login_time(request.getParameter("e_login_time"));
		asyncRespon.setE_logout_time(request.getParameter("e_logout_time"));
		
	}
}
