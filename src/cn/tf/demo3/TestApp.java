package cn.tf.demo3;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tf.demo2.MyBeanFactory;

public class TestApp {
	
	
	@Test
	public void test2(){
		
		String xmlpath="cn/tf/demo3/beans.xml";
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext(xmlpath);
		PersonService  personService=applicationContext.getBean("personServiceProxyId",PersonService.class);
		personService.addPerson();
		personService.updatePerson();
			
	}
	
	
	
	
	
}
