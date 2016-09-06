package cn.tf.demo3;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 切面类，需要实现接口，确定为什么样的通知。
 * * MethodInterceptor 环绕通知
 */
public class MyAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		System.out.println("前");
		//执行目标方法
		Object obj = mi.proceed();
		System.out.println("后");
		return obj;
	}
}
