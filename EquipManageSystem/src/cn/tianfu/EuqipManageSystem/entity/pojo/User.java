package cn.tianfu.EuqipManageSystem.entity.pojo;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="user")
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*uuod  主键*/
	private String      post_box    	  = null;
	private String      user_name   	  = null;
	private String 	    password    	  = null;
	private String      sex        		  = null;
	private String      telephone         = null;
	private String      introduce_self    = null;
	/*权限等级 默认最低*/
	private Integer     authority         = 0;
	
	public User(String post_box, String user_name, String password, String sex, String telephone,
			String introduce_self, Integer authority) 
	{
		super();
		this.post_box = post_box;
		this.user_name = user_name;
		this.password = password;
		this.sex = sex;
		this.telephone = telephone;
		this.introduce_self = introduce_self;
		this.authority = authority;
	}
	

	public User() 
	{
		super();
		this.post_box = null;
		this.user_name = null;
		this.password = null;
		this.sex = null;
		this.telephone = null;
		this.introduce_self = null;
		this.authority = 0;
	}


	/*get and set method*/
	public String getPost_box() {
		return post_box;
	}

	public void setPost_box(String post_box) {
		this.post_box = post_box;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIntroduce_self() {
		return introduce_self;
	}

	public void setIntroduce_self(String introduce_self) {
		this.introduce_self = introduce_self;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	
	/*tostring*/
	@Override
	public String toString() {
		return "User [post_box=" + post_box + ", user_name=" + user_name + ", password=" + password
				+ ", sex=" + sex + ", telephone=" + telephone + ", introduce_self=" + introduce_self + ", authority="
				+ authority + "]";
	}
	
	public boolean isEmpty()
	{
		//如果邮箱为空 那么 user 为空
		return post_box == null;
	}
}
