package cn.tianfu.EuqipManageSystem.dao.mybatis.dao;

import java.util.List;

import cn.tianfu.EuqipManageSystem.entity.bean.AsyncRefreshRequest;
import cn.tianfu.EuqipManageSystem.entity.bean.PageInfo;
import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;


public interface EquipmentMapper 
{
	
	/**
	 * @description: 如果想要执行配置文件中的某天sql语句,就可以定义于配置文件中id相同的方法名
	 * @param: Message message 是配置文件中对应sql中需要的parameterType
	 * @return: 返回类型即接口返回类型对应配置文件sql申明的返回类型决定
	 * */
	public Integer add(Object obj);
	public Integer delete(Object obj);
	public Object find(Object obj);
	public Integer update(Object obj);
	//通过传入的对象 主键值 获取 数据库中的对象
	public List<Equipment> findAll(PageInfo info);
	public List<Equipment> findAllByCommand(AsyncRefreshRequest asyncRequest);
	//根据要求查出的记录总数 用于分页
	public Integer getCountByCommand(AsyncRefreshRequest asyncRequest);
	public Integer getCount();
}
