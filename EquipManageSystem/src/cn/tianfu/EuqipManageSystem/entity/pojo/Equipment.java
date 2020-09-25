package cn.tianfu.EuqipManageSystem.entity.pojo;

import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("equip")
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class Equipment 
{
	private String name;
	private Date  login_time;
	private Date  logout_time;
	private String state;
	private String file_path;
	private String category;
	public Equipment(String name, Date login_time, Date logout_time, String state)
	{
		super();
		this.name = name;
		this.login_time = login_time;
		this.logout_time = logout_time;
		this.state = state;
	}
	
	public Equipment() {
		super();
		this.name = null;
		this.login_time = null;
		this.logout_time = null;
		this.state = null;
		this.file_path = null;
	}

	@Override
	public String toString() {
		return "Equipment [name=" + name + ", login_time=" + login_time + ", logout_time=" + logout_time + ", state="
				+ state + ", file_path=" + file_path + ", category=" + category + "]";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public Date getLogout_time() {
		return logout_time;
	}

	public void setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public boolean isEmpty() 
	{
		return name == null;
	}
}
