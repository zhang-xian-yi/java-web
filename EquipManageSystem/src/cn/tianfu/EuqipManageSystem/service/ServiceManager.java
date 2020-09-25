package cn.tianfu.EuqipManageSystem.service;

import cn.tianfu.EuqipManageSystem.exception.ExistException;

public interface ServiceManager 
{
	/*
	 * get a obj from database;
	 * parm obj user or euipq
	 * */
	public Object getObj(Object obj)throws Exception;
	/*
	 * update a obj from database;
	 * parm obj user or euipq
	 * */
	public Object updateObj(Object obj);
	/*
	 * delete a obj from database;
	 * parm obj user or euipq
	 * */
	public Object deleteObj(Object obj);
	/*
	 * add a obj from database;
	 * parm obj user or euipq
	 * */
	public Object addObj(Object obj) throws ExistException;
}
