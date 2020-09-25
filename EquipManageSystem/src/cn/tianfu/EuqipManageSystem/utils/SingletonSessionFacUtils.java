package cn.tianfu.EuqipManageSystem.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SingletonSessionFacUtils 
{
	private static SqlSessionFactory sqlSessionFactory = null;
	
	//私有构造函数 防止显式生成对象
	private SingletonSessionFacUtils()
	{
		
	}
	
	public static SqlSessionFactory getInstance()
	{
		try 
		{
			synchronized (SingletonSessionFacUtils.class) 
			{
				if(null == sqlSessionFactory)
				{
					InputStream is = Resources.getResourceAsStream("config/mybatis-config.xml");
					//根据配置文件创建会话工厂
					sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO: handle exception
		}
		
		return sqlSessionFactory;
	}
}
