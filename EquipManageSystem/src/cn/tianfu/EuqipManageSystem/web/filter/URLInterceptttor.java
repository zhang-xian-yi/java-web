package cn.tianfu.EuqipManageSystem.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.tianfu.EuqipManageSystem.entity.pojo.User;

public class URLInterceptttor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();
		if(url.indexOf("/afterLogin") == -1)
		{
			return true;
		}
		
		//如果要进入登陆后的界面
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login_user");
		if(user != null && (! user.isEmpty() ))
		{
			//验证是否有访问 核心页面的权限
			if(url.indexOf("/admin") != -1)
			{
				if(user.getAuthority() != 9)
				{
					request.setAttribute("errorMessage", "user must be administer , error privilage too lowwer");
					request.getRequestDispatcher("/afterLogin/index.html").forward(request, response);
					return false;
				}
				else 
				{
					return true;
				}
			}
			// pass the request 
			return true;
		}
		//if the session user is null then
		request.setAttribute("errorMessage", "用户未登录");
		response.sendRedirect("index.html");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

}
