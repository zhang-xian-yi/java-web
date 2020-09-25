package cn.tianfu.EuqipManageSystem.web.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dsna.util.images.ValidateCode;

@Controller
@RequestMapping(value="/func")
public class FuncController 
{
	@GetMapping(value="/getCaptcha")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response)throws IOException
	{
		ValidateCode vc = new ValidateCode(110, 25, 4, 9);
		//将验证码写入session 中
		request.getSession().setAttribute("captcha", vc.getCode());
		vc.write(response.getOutputStream());
	}
}
