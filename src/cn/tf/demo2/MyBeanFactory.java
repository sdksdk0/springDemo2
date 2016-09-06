package cn.tf.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class MyBeanFactory {
	
	public static Object getBean(){
		final BookService  bookService=new BookService();
		//切面类
		final MyAspect myAspect=new MyAspect();
		//生成代理类。代理类是目标类的子类
		//核心类
		Enhancer enhancer=new Enhancer();
		//确定父类
		enhancer.setSuperclass(bookService.getClass());
		//设置处理
		enhancer.setCallback(new MethodInterceptor() {
			
			@Override
			public Object intercept(Object proxy, Method method, Object[] args,
					MethodProxy methodProxy) throws Throwable {
				//执行目标类的方法
				myAspect.before();
				Object obj=method.invoke(bookService, args);
				methodProxy.invokeSuper(proxy, args);
				myAspect.after();
				return obj;
			}
		});	
		//创建代理类
		Object proxyObj=enhancer.create();
		return proxyObj;
		
	}
	
	

}
