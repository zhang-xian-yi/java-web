package cn.tianfu.EuqipManageSystem.web.aspect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.core.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;
import cn.tianfu.EuqipManageSystem.entity.pojo.User;
import cn.tianfu.EuqipManageSystem.exception.ExistException;
import cn.tianfu.EuqipManageSystem.utils.Log4j2Utils;
import cn.tianfu.EuqipManageSystem.utils.MergeUtils;
import cn.tianfu.EuqipManageSystem.utils.SpringBeanFactory;

@Aspect
@Component
public class WebAspect 
{
	private static final Logger logg = Log4j2Utils.getLogger("WebLogger");
	
	/*用一个 返回为 void 方法体为空的 方法 来命名 切入点*/
	@Pointcut("execution(* cn.tianfu.EuqipManageSystem.web.control.UserController.*(..))")
	private void pointCut(){}
	
	@Around("execution(* cn.tianfu.EuqipManageSystem.web.control.UserController.*(..))")
	public Object AroundHandleUserControl(ProceedingJoinPoint point)throws Throwable
	{
		//begin
		Object[] params = point.getArgs(); 
		HttpServletRequest request = (HttpServletRequest)params[0];
		User user_request = (User)SpringBeanFactory.getBean("user");
		MergeUtils.mergeAttribute(user_request, request);
		logg.info("control method "+point.getSignature().getName()+"  invoke\n input the user: "+user_request);
		Object ret = null;
		try 
		{
			ret = point.proceed();
		} 
		catch (ServletException e) 
		{
			logg.warn("servlet exception" + e.getMessage());
		}
		catch (IOException e) 
		{
			// TODO: handle exception
			logg.warn("io exception" + e.getMessage());
		}
		catch (ExistException e) 
		{
			// TODO: handle exception
			logg.warn("user exist exception"+e.getMessage());
		}
		logg.info("control end method "+point.getSignature().getName()+"  invoke return: "+ret);
		return ret;
	}
	
	/*user control exception异常通知*/
	@AfterThrowing(value="pointCut()",throwing="e")
	public void afterThrowingHandleUserControl(JoinPoint point,Throwable e)
	{
		logg.error("user control error adviser: "+e.getMessage());
	}
	
	/*equip controller error adviser*/
	@Around("execution(* cn.tianfu.EuqipManageSystem.web.control.EquipController.*(..))")
	public Object AroundHandleEquipControl(ProceedingJoinPoint point)throws Throwable
	{
		//begin
		Object[] params = point.getArgs(); 
		HttpServletRequest request = (HttpServletRequest)params[0];
		Equipment equip_request = (Equipment)SpringBeanFactory.getBean("equip");
		MergeUtils.mergeAttribute(equip_request, request);
		logg.info("control method "+point.getSignature().getName()+"  invoke\n input the equip: "+equip_request);
		Object ret = null;
		try 
		{
			ret = point.proceed();
		} 
		catch (ServletException e) 
		{
			logg.warn("servlet exception" + e.getMessage());
		}
		catch (IOException e) 
		{
			// TODO: handle exception
			logg.warn("io exception" + e.getMessage());
		}
		catch (ExistException e) 
		{
			// TODO: handle exception
			logg.warn("user exist exception"+e.getMessage());
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logg.warn("system exception "+e.getMessage());
		}
		logg.info("control end method "+point.getSignature().getName()+"  invoke return: "+ret);
		return ret;
	}
	
	/*user control exception异常通知*/
	@AfterThrowing(value="execution(* cn.tianfu.EuqipManageSystem.web.control.EquipController.*(..))",throwing="e")
	public void afterThrowingHandleEquipControl(JoinPoint point,Throwable e)
	{
		logg.error("equip control error adviser: "+e.getMessage());
	}
}
