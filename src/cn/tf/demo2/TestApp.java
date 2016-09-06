package cn.tf.demo2;

import org.junit.Test;

import cn.tf.demo2.MyBeanFactory;

public class TestApp {
	
	
	@Test
	public void test2(){
		BookService userService=new BookService();
		userService.addBook();
		userService.updateBook();	
	}
	
	@Test
	public void test1(){
		BookService userService=(BookService) MyBeanFactory.getBean();
		userService.addBook();
		userService.updateBook();	
	}
	
	
	
}
