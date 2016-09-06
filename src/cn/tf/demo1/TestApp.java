package cn.tf.demo1;

import org.junit.Test;

public class TestApp {
	
	@Test
	public void test1(){
		UserService userService=new UserServiceImpl();
		userService.addUser();
		userService.updateUser();	
	}

	
	@Test
	public void test2(){
		UserService userService=(UserService) MyBeanFactory.getBean();
		userService.addUser();
		userService.updateUser();	
	}
}
