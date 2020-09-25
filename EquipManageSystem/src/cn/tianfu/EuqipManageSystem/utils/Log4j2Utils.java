package cn.tianfu.EuqipManageSystem.utils;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class Log4j2Utils 
{
	private static ConfigurationSource source = null;
	static
	{
		URL url = Log4j2Utils.class.getResource("/config/log4j2.xml");
		try 
		{
			source = new ConfigurationSource(new FileInputStream(new File( url.getPath() )  ),url);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Logger getLogger(String name)
	{
		Configurator.initialize(null,source);
		return (Logger) LogManager.getLogger(name);
	}
}

