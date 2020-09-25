package cn.tianfu.EuqipManageSystem.web.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.Logger;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tianfu.EuqipManageSystem.entity.bean.AJaxResponseMsg;
import cn.tianfu.EuqipManageSystem.entity.bean.AJaxResponseMsg.ResponseCode;
import cn.tianfu.EuqipManageSystem.entity.bean.UserForm;
import cn.tianfu.EuqipManageSystem.entity.pojo.User;
import cn.tianfu.EuqipManageSystem.exception.ExistException;
import cn.tianfu.EuqipManageSystem.exception.ExistException.STATE;
import cn.tianfu.EuqipManageSystem.service.ServiceManager;
import cn.tianfu.EuqipManageSystem.utils.Log4j2Utils;
import cn.tianfu.EuqipManageSystem.utils.MergeUtils;
import cn.tianfu.EuqipManageSystem.utils.SendDataUtils;
import cn.tianfu.EuqipManageSystem.utils.SpringBeanFactory;

@Controller
@EnableAspectJAutoProxy
@RequestMapping(value="/userControl")
public class UserController 
{
	private static final Logger web_logg = Log4j2Utils.getLogger("WebLogger");
	/**
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@GetMapping(value="/login")
	public void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,ExistException,Exception
	{
		//获取request 中 所拥有的msg 对象
		AJaxResponseMsg<Map<String, String>> msg = new AJaxResponseMsg<Map<String, String>>();
		Map<String, String> mapMsg = new HashMap<String, String>();
		//获取表单数据
		User user = (User)SpringBeanFactory.getBean("user");
		MergeUtils.mergeAttribute(user,request);
		//调用业务逻辑
		ServiceManager service = (ServiceManager) SpringBeanFactory.getBean("userManager");;	
		//分发转向
		Object ret = service.getObj(user);
		if(null != ret)
		{
			request.getSession().setAttribute("login_user", user);
			//System.out.println("set user : "+user.toString());
			msg.setCode(ResponseCode.LOGIN_SUCC);
			msg.setMsg("pass vaild");
			//讲路径封转
			mapMsg.put("path","/afterLogin/index.html");
			mapMsg.put("user_name", user.getUser_name());
			msg.setData(mapMsg);
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
		}
		else if(STATE.NOEXIST == (Integer)ret)
		{
			//密码不正确的情况下
			msg.setMsg("valid error");
			mapMsg.put("errorPost_box", "the user is not exist");
			msg.setCode(ResponseCode.LOGIN_FAILED);
			msg.setData(mapMsg);
			response.setStatus(500);
			SendDataUtils.flushAJAXData(msg, response);
		}
		else 
		{
			//密码不正确的情况下
			msg.setMsg("valid error");
			mapMsg.put("errorPass", "password is error");
			msg.setCode(ResponseCode.LOGIN_FAILED);
			msg.setData(mapMsg);
			response.setStatus(500);
			SendDataUtils.flushAJAXData(msg, response);
		}
	}
	/**
	 * register a user
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@PostMapping(value="/register")
	public void registerUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,Exception
	{
		AJaxResponseMsg<Map<String, String>> msg = new AJaxResponseMsg<Map<String, String>>();
		Map<String, String> mapMsg = new HashMap<String, String>();
		//调用业务逻辑  logic
		ServiceManager service = (ServiceManager) SpringBeanFactory.getBean("userManager");
		User user = (User) SpringBeanFactory.getBean("user"); 
    	MergeUtils.mergeAttribute(user, request);
		if(null != service.getObj(user))
		{
			// TODO: handle exception
			msg.setCode(ResponseCode.REGISTER_FAILED);
			msg.setMsg("valid exception");
			response.setStatus(500);
			mapMsg.put("errorPost_box", "user has exist");
			SendDataUtils.flushAJAXData(msg, response);
	    	return;
		}
		//分发转向
		if(0 < (Integer)service.addObj(user))
		{
			//注册成功重定向 清除所有的request  和response  数据
			msg.setCode(ResponseCode.REGISTER_SUCC);
			msg.setMsg("pass valid");
			mapMsg.put("path","/htmlComponent/registerSuccess.html");
			msg.setData(mapMsg);
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
		else 
		{
			//请求转发 转发 规定的request 数据
			msg.setCode(ResponseCode.REGISTER_FAILED);
			msg.setMsg("not pass valid");
			mapMsg.put("path","/htmlComponent/registerFailed.html");
			msg.setData(mapMsg);
			//保证 调用 正确的回调函数
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
	}
	@PutMapping(value="/update")
	public String updateUser()
	{
		System.out.println("update a user");
		return "index";
	}
	@GetMapping("exit")
	public void exitUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpSession session = request.getSession();
		session.removeAttribute("login_user");
		response.sendRedirect("/EquipManageSystem/index.html");
	}
	@DeleteMapping(value="/delete")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		//获取表单数据
		User user = (User) request.getSession().getAttribute("login_user");
		//调用业务逻辑
		ServiceManager service = (ServiceManager) SpringBeanFactory.getBean("userManager");
		//分发转向
		AJaxResponseMsg<Map<String, String>> msg = new AJaxResponseMsg<Map<String, String>>();
		Map<String, String> mapMsg = new HashMap<String, String>();
		if(0 < (Integer) service.deleteObj(user))
		{
			request.getSession().removeAttribute("login_user");
			//注册成功重定向 清除所有的request  和response  数据
			msg.setCode(ResponseCode.SUCCESS);
			msg.setMsg("pass valid");
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
		}
		else 
		{
			msg.setCode(ResponseCode.ERROR);
			msg.setMsg("not pass valid");
			mapMsg.put("errorLogout","注销失败 请询问后台管理员");
			msg.setData(mapMsg);
			//保证 调用 正确的回调函数
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
	}
}
