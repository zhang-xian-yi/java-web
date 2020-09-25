package cn.tianfu.EuqipManageSystem.entity.bean;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.tianfu.EuqipManageSystem.entity.pojo.User;


@Component("userForm")
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class UserForm 
{
	@Resource(name="user")
	private User user;
	private Map<String,String> message = new HashMap<String, String>();
	
	//判断是否为标准输入

	/*
	邮箱：  邮箱符合正则标准 
	用户名：必须输入，且3~8位的字母组成<br>
	密码：必须输入，3~8位的数组组成<br>
	确认密码：和密码保持一致<br>  利用js 完成
	性别：  必须式男 和女
	电话：必须输入，必须式数字
	自我介绍 可以乱写 也可以不填
	*
	*/
	
	/*这里过滤校验 和 js 校验 可以配合 
	 * 复杂的校验过程 必须写在过滤器中*/
	
	public Boolean vaildData()
	{
		//校验邮箱
		//注意 字符串形式的开始符号 和结束符号 
		//不要写在字符串形式的正则表达式中
		if("".equals(user.getPost_box()))
    	{
    		message.put("errorPost_box", "The post_box can not be empty");
    	}
    	else if(!user.getPost_box().matches("[1-9][0-9]{5,10}@qq\\.[a-zA-Z]+"))//正则表达 限制字符个数
    	{
    		message.put("errorPost_box", "Incorrect email format only supports qq email");
    	}
		
		//is user name vaild
		//用户名的校验
    	if("".equals(user.getUser_name()))
    	{
    		message.put("errorName", "The username cannot be empty");
    	}
    	else if(!user.getUser_name().matches("\\w{8,16}"))//正则表达 限制字符个数
    	{
    		message.put("errorName", "User names must be entered in 8 to 16 characters");
    	}
    	
    	//密码正则 必须为 数组和字符的组合  
    	//(?!^\d+$)  排除全数字
    	//(?!^[A-z]+$)  排除全字母
    	//密码的校验
    	
    	if("".equals(user.getPassword()))
    	{
    		message.put("errorPassword", "The password cannot be empty");
    	}
    	else if(!user.getPassword().matches("(?!^\\d+$)\\w{8,16}"))
    	{
    		message.put("errorPassword", "The password must contain a minimum of 8 digits of alphanumeric combinations");
    	}
    	
    	//如果 错误消息中有数据 代不合法
		return message.isEmpty();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, String> getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "UserForm [user=" + user + ", message=" + message + "]";
	}

}
