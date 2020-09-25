package cn.tianfu.EuqipManageSystem.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import cn.tianfu.EuqipManageSystem.entity.bean.AJaxResponseMsg;
import cn.tianfu.EuqipManageSystem.entity.bean.AJaxResponseMsg.ResponseCode;
import cn.tianfu.EuqipManageSystem.entity.bean.PageBean;
import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;
import net.sf.json.JSONObject;

public class SendDataUtils 
{
	public static void flushAJAXData(AJaxResponseMsg<Map<String, String>> msg,ServletResponse response)throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json;charset=UTF-8");
		JSONObject responMsg = JSONObject.fromObject(msg);
		try 
	    {
			//将json 包 推送到前台去
			out.println(responMsg.toString());
	    	//这里直接结束 验证失败 直接推送
	    	return;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			out.flush();
			if(out != null)
			{
				out.close();
			}
		}
	}
	
	//由于重载 不能出现 相同类型的参数 同样排列 所以 这种代码完全一模一样的方式 
	//可以采用 将ajaxResponse 的实体类 写一个父类
	public static void flushAJAXSelectData(AJaxResponseMsg<PageBean<Equipment>> msg,ServletResponse response)throws IOException, ServletException
	{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json;charset=UTF-8");
		JSONObject responMsg = JSONObject.fromObject(msg);
		try 
	    {
			//将json 包 推送到前台去
	    	out.println(responMsg.toString());
	    	//System.out.println("response select json = "+responMsg.toString());
	    	return;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			out.flush();
			if(out != null)
			{
				out.close();
			}
		}
	}
	
	public static void flushResponse2Client(PageBean<Equipment> pageBean,AJaxResponseMsg<PageBean<Equipment>> msg,HttpServletResponse response)throws ServletException, IOException 
	{
		if(! pageBean.isEmpty())
		{
			msg.setCode(ResponseCode.SELECT_EQUIP_SUCC);
			msg.setMsg("select succ");
			//讲路径封转
			msg.setData(pageBean);
			response.setStatus(200);
			SendDataUtils.flushAJAXSelectData(msg, response);
		}
		else 
		{
			msg.setCode(ResponseCode.SELECT_EQUIP_FAILED);
			msg.setMsg("select failed");
			//讲路径封转
			msg.setData(pageBean);
			response.setStatus(500);
			SendDataUtils.flushAJAXSelectData(msg, response);
		}
	}
}
