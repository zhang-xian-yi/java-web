package cn.tianfu.EuqipManageSystem.entity.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("asyncRefreshRequest")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class AsyncRefreshRequest 
{
	private PageInfo page_info;
	private String e_name;
	private String e_status;
	private String e_login_time;
	private String e_logout_time;
	
	public AsyncRefreshRequest()
	{
		this.page_info = null;
		this.e_login_time = null;
		this.e_logout_time = null;
		this.e_name = null;
		this.e_status = null;
	}
	
	public PageInfo getPage_info() {
		return page_info;
	}
	public void setPage_info(PageInfo page_info) {
		this.page_info = page_info;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getE_status() {
		return e_status;
	}
	public void setE_status(String e_status) {
		this.e_status = e_status;
	}
	public String getE_login_time() {
		return e_login_time;
	}
	public void setE_login_time(String e_login_time) {
		this.e_login_time = e_login_time;
	}
	public String getE_logout_time() {
		return e_logout_time;
	}
	public void setE_logout_time(String e_logout_time) {
		this.e_logout_time = e_logout_time;
	}
	@Override
	public String toString() {
		return "AsyncRefreshResponse [page_info=" + page_info + ", e_name=" + e_name + ", e_status=" + e_status
				+ ", e_login_time=" + e_login_time + ", e_logout_time=" + e_logout_time + "]";
	}
}
