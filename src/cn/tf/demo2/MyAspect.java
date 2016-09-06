package cn.tf.demo2;

//切面类中包含多个通知Advice
public class MyAspect {
	
	public void before(){
		System.out.println("之前执行");
	}
	public void after(){
		System.out.println("之后执行");
	}
}
