package cn.tianfu.EuqipManageSystem.exception;

public class ExistException extends Exception
{
	private Integer name;
	private Integer state;
	
	
	
	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	

	public interface NAME
	{
		public static final int USER = 1;
		public static final int EQUIP = 2;
	}
	public interface STATE
	{
		public static final int EXIST = 3;
		public static final int NOEXIST = 0;
	}
	
	public ExistException(String message, Throwable cause) 
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExistException(Integer name,Integer state_,String message) 
	{
		super(message);
		this.name = name;
		this.state = state_;
		// TODO Auto-generated constructor stub
	}

	public ExistException(Throwable cause) 
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
