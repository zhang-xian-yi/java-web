package cn.tianfu.EuqipManageSystem.utils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactory 
{
	private static ApplicationContext applicationContext;
	/*类加载的时候 被执行 且 只被执行一次 */
	static
	{
		applicationContext = new ClassPathXmlApplicationContext("config/spring-beans-config.xml");
	}
	/*防止意外出现 设置初始化方法*/
	private static ApplicationContext getInstance()
	{
		if(applicationContext == null)
		{
			synchronized (SpringBeanFactory.class) 
			{
				if(null == applicationContext)
				{
					applicationContext = new ClassPathXmlApplicationContext("config/spring-beans-config.xml");
					return applicationContext;
				}
			}
		}
		return applicationContext;
	}
	
	public static Object getBean(String beanId)
	{
		//一般不可能出现 除非文件找不到
		if(applicationContext == null)
		{
			getInstance();
		}
		return applicationContext.getBean(beanId);
	}
}
