package cn.tf.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanFactory {
	
	public static Object getBean(){
		final UserService  userService=new UserServiceImpl();
		
		//切面类
		final MyAspect myAspect=new MyAspect();
		
		//生成代理类
		
		
		
		return Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader(), userService.getClass().getInterfaces(), 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						//前通知
						myAspect.before();
						
						//执行目标类的方法
						Object obj=method.invoke(userService,args );
						myAspect.after();
						
						//后通知
						
						return obj;
					}
				});		
	}
	
	

}
