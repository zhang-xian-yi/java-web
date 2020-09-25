package cn.tianfu.EuqipManageSystem.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;

public class ParseRequestUtils 
{
	//parse  upload file item
	public static void parseUploadFile(Equipment equip,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 要执行文件上传的操作
		// 判断表单是否支持文件上传。即：enctype="multipart/form-data"
		if(! ServletFileUpload.isMultipartContent(request)) 
		{
			//throw new RuntimeException("your form is not multipart/form-data");
		}
		// 创建一个DiskFileItemfactory工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File("D:\\temp"));// 指定临时文件的存储目录
		// 创建一个ServletFileUpload核心对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解决上传表单项乱码问题
		sfu.setHeaderEncoding("UTF-8");
		// 限制上传文件的大小
		sfu.setFileSizeMax(1024*1024*2);//表示3M大小
		
		//获取 request 中 所有的 表单项
		List<FileItem> items = (List<FileItem>) sfu.parseRequest(request);
		for(FileItem item: items)
		{
			//得到上传文件的表单项
			if(!item.isFormField())
			{
				parseUploadFile(item,equip,request);
			}
			else 
			{
				parseFormItem(item,equip);
			}
		}
	}
	
	private static void parseFormItem(FileItem fileitem,Equipment equip)
	{
		try 
		{
			String fieldname = fileitem.getFieldName();// 字段名
			String fieldvalue = fileitem.getString("UTF-8");// 字段值
			fieldvalue = new String(fieldvalue.getBytes("iso-8859-1"),"utf-8");
			switch (fieldname) 
			{
			case "equip_name":
				equip.setName(fieldvalue);
				break;
			case "state":
				if("working".equalsIgnoreCase(fieldvalue))
				{
					equip.setState("WORKING");
				}
				else if("ended".equalsIgnoreCase(fieldvalue))
				{
					equip.setState("ENDED");
				}
				else if("broken".equalsIgnoreCase(fieldvalue))
				{
					equip.setState("BROKEN");
				}
				break;
			case "category":
				URLEncoder.encode(fieldvalue, "UTF-8");
				equip.setCategory(fieldvalue);
				break;
			default:
				break;
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void parseUploadFile(FileItem fileitem,Equipment equip,HttpServletRequest request) throws Exception
	{
		//获取绝对的路径
		String directoryRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
		//既是文件 也是路径 是保存的路径
		File storeDirectory = new File(directoryRealPath);
		//如果该路径不存在 直接创建
		if(! storeDirectory.exists())
		{
			storeDirectory.mkdirs();
		}
		//得到 上传的文件名字
		String fileName = fileitem.getName();
		//处理文件名
		if(fileName != null)
		{
			// filename.substring(filename.lastIndexOf(File.separator)+1);
			fileName = FilenameUtils.getName(fileName);// 效果同上
		}
		else 
		{
			//没有上传文件直接返回
			return;
		}
		//同名文件的覆盖问题
		fileName = UUID.randomUUID() + "_" + fileName;
		//打散目录
		String childDirectory = DirectoryUtils.makeChildDirectory(storeDirectory, fileName);
		// 在storeDirectory下，创建完整目录下的文件
		File file = new File(storeDirectory, childDirectory+ File.separator + fileName); // 绝对目录/日期目录/文件名
		// 通过文件输出流将上传的文件保存到磁盘
		//通过文件输出流 将文件存储在服务器的磁盘上
		fileitem.write(new File(storeDirectory, childDirectory+ File.separator + fileName));
		//删除临时文件
		fileitem.delete();
		//空文件目录 需要删除？
		
		//设置equip 的 文件路径
		String file_path = file.getAbsolutePath();
		file_path = file_path.replaceAll("\\\\", "/");
		equip.setFile_path(file_path);
	}
}
