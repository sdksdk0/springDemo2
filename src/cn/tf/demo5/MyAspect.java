package cn.tf.demo5;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


public class MyAspect {
	
	public void myBefore(JoinPoint  joinPoint){
		System.out.println("前置通知"+joinPoint.getSignature().getName());
	}
	
	public void myAfterReturning(JoinPoint joinPoint,Object xxx){
		System.out.println("后置通知, 返回值：" + xxx);
	}
	
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		
		System.out.println("前");
		//必须执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知, " + e.getMessage());
	}
	
	public void myAfter(){
		System.out.println("最终");
	}
	
	

}
