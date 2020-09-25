package cn.tianfu.EuqipManageSystem.service.aspect;


import java.lang.reflect.Method;

import org.apache.logging.log4j.core.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import cn.tianfu.EuqipManageSystem.entity.pojo.Equipment;
import cn.tianfu.EuqipManageSystem.exception.ExistException;
import cn.tianfu.EuqipManageSystem.exception.ExistException.NAME;
import cn.tianfu.EuqipManageSystem.exception.ExistException.STATE;
import cn.tianfu.EuqipManageSystem.utils.Log4j2Utils;

@Aspect
@Component
public class ServiceAspect 
{
	private static final Logger logg = Log4j2Utils.getLogger("ServiceLogger");
	@Around("execution(* cn.tianfu.EuqipManageSystem.service.impl.*.*(..))")
	public Object AroundHandleEquipService(ProceedingJoinPoint point)throws Throwable
	{
		Method method = ((MethodSignature)point.getSignature()).getMethod();
		//begin
		Object obj = point.getArgs()[0];
		logg.info(method+" start  invoke\t input the args: "+obj);
		Integer num = 0;
		try 
		{
			num =  (Integer) point.proceed();
		} 
		catch (ExistException e) 
		{
			// TODO: handle exception
			switch (e.getName()) 
			{
			case NAME.EQUIP:
				{
					if(e.getState() == STATE.EXIST)
					{
						//add exception
						logg.warn("add the equipment has exist ");
					}
					break;
				}
				
			case NAME.USER:
				{
					if(e.getState() == STATE.EXIST)
					{
						//register exception
						logg.warn("register the user has existed");
					}
					else if(e.getState() == STATE.NOEXIST) 
					{
						//login exception
						logg.warn("the user is not exist");
						return num;
					}
					break;
				}
			default:
				break;
			}
		}
		catch (Exception e) 
		{
			
			// TODO: handle exception
			logg.error("method "+method+"  error  invoke");
		}
		logg.info(method+"end  invoke\t return num =  "+num);
		return num;
	}
	
	/*user control exception异常通知*/
	@AfterThrowing(value="execution(* cn.tianfu.EuqipManageSystem.service.impl.*.*(..))",throwing="e")
	public void afterThrowingHandleEquipService(JoinPoint point,Throwable e)
	{
		Method method = ((MethodSignature)point.getSignature()).getMethod();
		logg.error(method+"invoke error \n"+"service equip Manager error adviser: "+e.getMessage());
	}
}
