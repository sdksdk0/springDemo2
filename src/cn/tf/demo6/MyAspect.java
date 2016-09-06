package cn.tf.demo6;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
	
	@Before("myPointCut()")
	public void myBefore(JoinPoint  joinPoint){
		System.out.println("前置通知"+joinPoint.getSignature().getName());
	}
	
	@AfterReturning(value="execution(* cn.tf.demo6.*.*(..))",returning="xxx")
	public void myAfterReturning(JoinPoint joinPoint,Object xxx){
		System.out.println("后置通知, 返回值：" + xxx);
	}
	
	@Around("myPointCut()")
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		
		System.out.println("前");
		//必须执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	@AfterThrowing(value="myPointCut()",throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知, " + e.getMessage());
	}
	@After("myPointCut()")
	public void myAfter(){
		System.out.println("最终");
	}
	//用来声明切入点表达式，在通知中通过方法名来获得，相当于调用方法
	@Pointcut("execution(* cn.tf.demo6.*.*(..))")
	private void myPointCut(){
		
	}
}
