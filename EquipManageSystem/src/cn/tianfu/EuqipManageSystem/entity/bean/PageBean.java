package cn.tianfu.EuqipManageSystem.entity.bean;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
@Component("pageBean")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PageBean<T> 
{
	private int page_curr;
	private int page_size;
	private int page_num;
	private int data_count;
	private List<T> data;
	public int getPage_curr() {
		return page_curr;
	}
	public void setPage_curr(int page_curr) {
		this.page_curr = page_curr;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public int getPage_num() {
		return page_num;
	}
	public void setPage_num(int page_num) {
		this.page_num = page_num;
	}
	public int getData_count() {
		return data_count;
	}
	public void setData_count(int data_count) {
		this.data_count = data_count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public PageBean() {
		super();
	}
	
	public boolean isEmpty()
	{
		if(data.isEmpty())
		{
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "PageBean [page_curr=" + page_curr + ", page_size=" + page_size + ", page_num=" + page_num
				+ ", data_count=" + data_count + ", data=" + data.toString() + "]";
	}
	
	
}
