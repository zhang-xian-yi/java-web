package cn.tianfu.EuqipManageSystem.utils;

import java.io.File;

import com.mysql.cj.result.Field;

public class DirectoryUtils 
{
	// 目录打散
	public static String makeChildDirectory(File storeDirectory, String filename) {
		int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码
		String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符
														// abdsaf2131safsd
		String childDirectory = code.charAt(0) + File.separator
				+ code.charAt(1); // a/b
		// 创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}
	
	//下面的三种文件删除方式极为常用所以积累下来！
	//1. 删除目录下所有文件
	//2. 删除当前目录中所有目录文件
	//3. 删除所有空目录
        /**
	 * 删除目录下所有文件
	 * 
	 * @param dir
	 * @return
	 */
	public static void deleteDir(File dir) 
	{
		if (dir.isDirectory()) 
		{
			File[] files = dir.listFiles();
			for (File file : files) 
			{
				file.delete();
			}
		} 
		else 
		{
			dir.delete();
		}
	}
 
	/**
	 * 删除当前目录中所有目录文件
	 * 
	 * @param dir
	 */
	public static void deleteAllDir(File dir) 
	{
		File[] files = dir.listFiles();
		if (files != null) 
		{
			for (File file : files) 
			{
				if (file.isDirectory()) 
				{
					deleteDir(file);
				}
			}
		}
	}
       /**
     * @Title: delNullDir  
     * @Description: 删除空目录  
     * @param filePath  文件路径（递归调用时发生改变）
     * @param initFilePath  文件路径（递归调用时路径不发生改变，用于判断传入的根路径） 
     * @return void    返回类型  
     * @throws
     */
	/*
    public static void delNullDir(String filePath,String initFilePath) 
    {
        File file=new File(filePath);
        logger.debug("文件目录路径："+filePath);
        if(file.isDirectory())
        {
            logger.debug("文件："+file.getPath());
            File[] files=file.listFiles();
            if(files.length==0){
                try {
                    String initFileAbsolutePath=(new File(initFilePath)).getAbsolutePath();
                    if(initFileAbsolutePath.equals(file.getAbsolutePath())){
                        return;
                    }else{
                        org.apache.commons.io.FileUtils.deleteDirectory(file);
                        logger.debug("删除文件目录为:"+file.getPath());
                         String parentPath= file.getParentFile().getAbsolutePath();
                         delNullDir(parentPath,initFilePath);
                    }
                } catch (IOException e) {
                    logger.error(e);
                }
            }else{
                for(int i=0;i<files.length;i++){
                    filePath=file.getAbsolutePath()+"/"+files[i].getName();
                    delNullDir(filePath,initFilePath);
                }
            }
        }
    }
    */
}
