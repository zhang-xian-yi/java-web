package cn.tianfu.EuqipManageSystem.service.impl;

import java.io.File;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.core.Logger;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import cn.tianfu.EuqipManageSystem.dao.mybatis.dao.EquipmentMapper;
import cn.tianfu.EuqipManageSystem.entity.bean.AsyncRefreshRequest;
import cn.tianfu.EuqipManageSystem.entity.bean.PageBean;
import cn.tianfu.EuqipManageSystem.entity.bean.PageInfo;
import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;
import cn.tianfu.EuqipManageSystem.exception.ExistException;
import cn.tianfu.EuqipManageSystem.exception.ExistException.NAME;
import cn.tianfu.EuqipManageSystem.exception.ExistException.STATE;
import cn.tianfu.EuqipManageSystem.service.ServiceManager;
import cn.tianfu.EuqipManageSystem.utils.Log4j2Utils;
import cn.tianfu.EuqipManageSystem.utils.SingletonSessionFacUtils;

@Service("equipManager")
public class EquipManager implements ServiceManager 
{
	private static final Logger service_logg = Log4j2Utils.getLogger("ServiceLogger");
	@Override
	public Object getObj(Object obj) throws Exception 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public PageBean<Equipment> getAllEquipsByPageinfo(PageInfo page_info)
	{
		//由于代码完全和 findALL  一样 仍然都是 根据页面信息查数据
		// TODO Auto-generated method stub
		SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
    	EquipmentMapper dao = session.getMapper(EquipmentMapper.class);
    	Integer data_num = dao.getCount();
    	List<Equipment> list =  dao.findAll(page_info);
		session.close();
		//封转pageBean 属性
		PageBean<Equipment> pageBean = new PageBean<Equipment>();
		pageBean.setData(list);
		pageBean.setData_count(data_num);
		pageBean.setPage_curr(page_info.getPage_curr());
		//数据总数 / 页面长度 = 总页数 （向上取整 会 +1 （如果有余数））
		pageBean.setPage_num((int)Math.ceil((data_num * 1.0)/page_info.getPage_size()));
		pageBean.setPage_size(page_info.getPage_size());
		//System.out.println(pageBean);
		return pageBean;
	}
	@Override
	public Object updateObj(Object obj) {
		// TODO Auto-generated method stub
		int num = 0;
		SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
    	EquipmentMapper dao = session.getMapper(EquipmentMapper.class);
    	//删除文件
    	Equipment temp = (Equipment) dao.find(obj);
    	service_logg.debug("service- the update equip: name   "+((Equipment)obj).getName());
    	if(temp.getFile_path() != null && ! "".equals(temp.getFile_path()))
    	{
    		File d_file = new File(temp.getFile_path());
        	if(d_file.exists())
        	{
        		d_file.delete();
        	}
    	}
    	//核心代码
		num =  dao.update(obj);
		session.commit();
		session.close();
		service_logg.debug("service:---the update return value: num:  "+num);
		return num;
	}

	@Override
	public Object deleteObj(Object obj) {
		// TODO Auto-generated method stub
		service_logg.debug("service- the delete equip: name"+((Equipment)obj).getName());
		int num = 0;
		SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
    	EquipmentMapper dao = session.getMapper(EquipmentMapper.class);
    	//删除文件
    	Equipment temp = (Equipment) dao.find(obj);
    	/*
    	 * 现一个好办法 用于删除 那些无用的空目录 虽然以后也有可能需要使用 
    	Integer index = temp.getFile_path().indexOf("/WEB-INF/upload/");
    	String file_direct = temp.getImage_path().substring(index);
    	System.out.println("file_delete_direct: "+file_direct);
    	*/
    	if(temp.getFile_path() != null || "".equals(temp.getFile_path()))
    	{
    		File d_file = new File(temp.getFile_path());
        	if(d_file.exists())
        	{
        		d_file.delete();
        	}
    	}
    	//核心代码
		num =  dao.delete(obj);
		session.commit();
		session.close();
		service_logg.debug("service:---the delete return value: num:  "+num);
		return num;
	}

	@Override
	public Object addObj(Object obj) throws ExistException {
		// TODO Auto-generated method stub
		service_logg.debug("service:---the add equip :  "+obj.toString());
		SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
    	EquipmentMapper dao = session.getMapper(EquipmentMapper.class);
		Equipment equip = (Equipment) dao.find(obj);
		if(null == equip)
		{
			//如果没有
			if(dao.add(obj) > 0)
			{
				session.commit();
				session.close();
				return "succ";
			}
		}
		
		if(! equip.isEmpty())
		{
			session.close();
			throw new ExistException(NAME.EQUIP,STATE.EXIST,"the equip has exist");
		}
		
		session.close();
		return "failed";
	}
	
	public PageBean<Equipment> findallByAsyncRequest(AsyncRefreshRequest asyncRequest) 
	{
		// TODO Auto-generated method stub
		SqlSession session = SingletonSessionFacUtils.getInstance().openSession();
    	EquipmentMapper dao = session.getMapper(EquipmentMapper.class);
    	//为了防止空指针 直接赋初值
    	Integer data_num = dao.getCountByCommand(asyncRequest);
    	List<Equipment> list =  dao.findAllByCommand(asyncRequest);
		session.close();
		if(data_num == null)
		{
			//如果出现 返回值 为空 即没有任何数据符合查询条件 
			data_num = 0;
		}
		//封转pageBean 属性
		PageBean<Equipment> pageBean = new PageBean<Equipment>();
		pageBean.setData(list);
		pageBean.setData_count(data_num);
		pageBean.setPage_curr(asyncRequest.getPage_info().getPage_curr());
		//数据总数 / 页面长度 = 总页数 （向上取整 会 +1 （如果有余数））
		int num = (int)Math.ceil( (data_num * 1.0)/asyncRequest.getPage_info().getPage_size());
		if(num == 0)
		{
			pageBean.setPage_num(1);
		}
		pageBean.setPage_num(num);
		pageBean.setPage_size(asyncRequest.getPage_info().getPage_size());
		//System.out.println("asyncResponse: "+pageBean);
		return pageBean;
	}

}
