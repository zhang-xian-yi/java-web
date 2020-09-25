package cn.tianfu.EuqipManageSystem.web.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.core.Logger;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tianfu.EuqipManageSystem.entity.bean.AJaxResponseMsg;
import cn.tianfu.EuqipManageSystem.entity.bean.AJaxResponseMsg.ResponseCode;
import cn.tianfu.EuqipManageSystem.entity.bean.AsyncRefreshRequest;
import cn.tianfu.EuqipManageSystem.entity.bean.PageBean;
import cn.tianfu.EuqipManageSystem.entity.bean.PageInfo;
import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;
import cn.tianfu.EuqipManageSystem.service.ServiceManager;
import cn.tianfu.EuqipManageSystem.service.impl.EquipManager;
import cn.tianfu.EuqipManageSystem.utils.Log4j2Utils;
import cn.tianfu.EuqipManageSystem.utils.MergeUtils;
import cn.tianfu.EuqipManageSystem.utils.ParseRequestUtils;
import cn.tianfu.EuqipManageSystem.utils.SendDataUtils;
import cn.tianfu.EuqipManageSystem.utils.SpringBeanFactory;
import net.sf.json.JSONObject;


@Controller
@EnableAspectJAutoProxy
@RequestMapping(value="/equipControl")
public class EquipController 
{
	private static final Logger web_logger = Log4j2Utils.getLogger("WebLogger");
	/*
	 * name get alll Equipment By many Condition
	 * para request
	 * para response
	 * */
	@GetMapping("/getAllEquipments")
	public void getAllEquipments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//获取传入的页面信息
		//spring 申请的 要报错啊  可能是没有扫描道
		PageInfo page_info = new PageInfo();
		MergeUtils.mergeAttribute(page_info, request);
		//调用 业务逻辑
		ServiceManager service = (ServiceManager)SpringBeanFactory.getBean("equipManager");
		PageBean<Equipment> pageBean = ((EquipManager)service).getAllEquipsByPageinfo(page_info);
		//跳转页面
		AJaxResponseMsg<PageBean<Equipment>> msg = new AJaxResponseMsg<PageBean<Equipment>>();
		SendDataUtils.flushResponse2Client(pageBean,msg,response);
	}
	
	@GetMapping(value="/getAllEquipsWithCommand")
	public void getAllEquipmentsWithCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AsyncRefreshRequest asyncRequest = new AsyncRefreshRequest();
		//封转对象 
		MergeUtils.mergeAttribute(asyncRequest, request);
		//调用 业务逻辑
		ServiceManager service = (ServiceManager)SpringBeanFactory.getBean("equipManager");
		PageBean<Equipment> pageBean = ((EquipManager)service).findallByAsyncRequest(asyncRequest);
		//跳转页面
		AJaxResponseMsg<PageBean<Equipment>> msg = new AJaxResponseMsg<PageBean<Equipment>>();
		//刷新数据到前端去
		SendDataUtils.flushResponse2Client(pageBean,msg,response);
	}
	/*
	 * add  a Equipment
	 * para request
	 * para response
	 * */
	@PostMapping("/addEquipment")
	public void addEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,Exception
	{
		//get the encoding
		AJaxResponseMsg<Map<String, String>> msg = new AJaxResponseMsg<Map<String, String>>();
		Map<String, String> mapMsg = new HashMap<String, String>();
		Equipment equip = (Equipment)SpringBeanFactory.getBean("equip");
		MergeUtils.mergeAttribute(equip, request);
		//解析上传文件 
		ParseRequestUtils.parseUploadFile(equip,request,response);
		ServiceManager service = (ServiceManager)SpringBeanFactory.getBean("equipManager");
		String ret = (String)service.addObj(equip);
		if("succ".equals(ret))
		{
			msg.setCode(ResponseCode.ADD_EQUIP_SUCC);
			msg.setMsg("pass valid");
			//讲路径封转
			mapMsg.put("path","/afterLogin/responseHTML/addSucc.html");
			msg.setData(mapMsg);
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
		else 
		{
			msg.setCode(ResponseCode.ADD_EQUIP_FAILED);
			msg.setMsg("not pass valid");
			//讲路径封转
			mapMsg.put("path","/afterLogin/responseHTML/addFailed.html");
			msg.setData(mapMsg);
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
	}
	/*
	 * name update a Equipment
	 * para request
	 * para response
	 * */
	@PutMapping("/updateEquipment")
	public void updateEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,Exception
	{
		//get the encoding
		Equipment equip = (Equipment)SpringBeanFactory.getBean("equip");
		MergeUtils.mergeAttribute(equip, request);
		//解析上传文件 
		ParseRequestUtils.parseUploadFile(equip,request,response);
		ServiceManager service = (ServiceManager)SpringBeanFactory.getBean("equipManager");
		//System.out.println("uppdate equip "+equip);
		Integer ret = (Integer) service.updateObj(equip);
		//如果更新条数 > 0 更新完成
		AJaxResponseMsg<Map<String, String>> msg = new AJaxResponseMsg<Map<String, String>>();
		if(ret > 0 )
		{
			msg.setCode(ResponseCode.SUCCESS);
			msg.setMsg("update succ");
			//讲路径封转
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
		else
		{
			msg.setCode(ResponseCode.ERROR);
			msg.setMsg("update error");
			//讲路径封转
			response.setStatus(500);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
	}
	/*
	 * name delete Equipment 
	 * para request
	 * para response
	 * */
	@CrossOrigin
	@DeleteMapping("/deleteEquipment")
	public void deleteEquipment(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		Equipment equip = (Equipment)SpringBeanFactory.getBean("equip");
		equip.setName(request.getParameter("name"));
		web_logger.debug("control request delete name: "+request.getParameter("name")+" parse equip: name"+equip.getName());
		ServiceManager service = (ServiceManager)SpringBeanFactory.getBean("equipManager");
		Integer ret = (Integer) service.deleteObj(equip);
		AJaxResponseMsg<Map<String, String>> msg = new AJaxResponseMsg<Map<String, String>>();
		if( ret > 0 )
		{
			msg.setCode(ResponseCode.SUCCESS);
			msg.setMsg("delete succ");
			//讲路径封转
			response.setStatus(200);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
		else 
		{
			msg.setCode(ResponseCode.ERROR);
			msg.setMsg("delete error");
			//讲路径封转
			response.setStatus(500);
			SendDataUtils.flushAJAXData(msg, response);
			return;
		}
	}
	
	/*
	 * download a euqipment file  
	 * para request
	 * para response
	 * */
	@GetMapping("/download")
	public void downloadEquipmentFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("text/html; charset=UTF-8"); 
		// TODO Auto-generated method stub
		//根据路径获取文件输入流
		String path =request.getParameter("path");
		FileInputStream fis = new FileInputStream(path);
		//创建字节输出流
		ServletOutputStream sobs= response.getOutputStream();
		//获取文件名字  
		String file_name = path.substring(path.lastIndexOf("\\")+1);
		//设置文件名字的编码  用于确保安装
		//将不安全的名字字符 转换成可以识别的字符 避免出现无法识别的的响应
		file_name = URLEncoder.encode(file_name, "UTF-8");
		//通知客户端 下载文件而非展现
		response.setHeader("content-disposition", "attachment;filename="+file_name);
		response.setHeader("content-type", "image/jpeg");
		//执行输出操作
		int len = 1;
		byte[] buffer = new byte[1024];
		while((len = fis.read(buffer)) != -1)
		{
			sobs.write(buffer,0,len);
		}
		//关闭资源
		sobs.close();
		fis.close();
	}
}
