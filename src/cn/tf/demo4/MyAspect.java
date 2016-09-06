package cn.tf.demo4;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		
		System.out.println("执行前");
		Object obj = mi.proceed();
		System.out.println("执行后");
		
		return obj;
	}

}
