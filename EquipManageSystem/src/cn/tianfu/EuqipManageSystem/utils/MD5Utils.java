package cn.tianfu.EuqipManageSystem.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils 
{
	//change data to md5 data
    public final static String getMD5(String plainText)
    {
    	String md5code = null;
    	
    	if(plainText != null)
    	{
    		byte[] secretBytes = null;
    		try 
    		{
    			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
    		} 
    		catch (NoSuchAlgorithmException e) 
    		{
    			throw new RuntimeException("没有md5这个算法！");
    		}
    		
    		md5code = new BigInteger(1, secretBytes).toString(16);
    		for (int i = 0; i < 32 - md5code.length(); i++) 
    		{
    			md5code = "0" + md5code;
    		}
    	}
    	return md5code;
    }
}
