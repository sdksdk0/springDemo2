package cn.tf.jdbc.c;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tf.jdbc.a.User;

public class TestApp {
	
	
	@Test
	public void test1(){
		
		User user = new User();
		user.setUsername("JieKE");
		user.setPassword("aaa");
		
		
		String xmlpath="cn/tf/jdbc/c/beans.xml";
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext(xmlpath);
		UserDao userDao = (UserDao) applicationContext.getBean("userDaoId");
		userDao.save(user);
	
	
	}

}
