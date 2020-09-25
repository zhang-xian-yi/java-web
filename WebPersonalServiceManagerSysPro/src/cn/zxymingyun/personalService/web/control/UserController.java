package cn.zxymingyun.personalService.web.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@EnableAspectJAutoProxy
@RequestMapping(value="/userControl")
public class UserController 
{
	//private static final Logger web_logg = Log4j2Utils.getLogger("WebLogger");
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
	public void loginUser(HttpServletRequest request, HttpServletResponse response)
	{
		//获取request 中 所拥有的msg 对象

		//获取表单数据

		//调用业务逻辑

		//分发转向

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


	}
}
