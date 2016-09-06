package cn.tf.jdbc.d;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tf.jdbc.a.User;

public class TestApp {
	
	//@Test
	public void test1(){
		User user = new User();
		user.setUsername("张三");
		user.setPassword("aaa");
		
		String xmlPath = "cn/tf/jdbc/d/beans.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserDao userDao = (UserDao) applicationContext.getBean("userDaoId");
		userDao.save(user);
		
	}
	
	@Test
	public void test2(){	
		String xmlPath = "cn/tf/jdbc/d/beans.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserDao userDao = (UserDao) applicationContext.getBean("userDaoId");
		User user = userDao.findById2(1);
		System.out.println(user);
	}

}
