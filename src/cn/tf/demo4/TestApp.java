package cn.tf.demo4;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TestApp {
	
	
	@Test
	public void test2(){
		
		String xmlpath="cn/tf/demo4/beans.xml";
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext(xmlpath);
		StudentService studentService = (StudentService) applicationContext.getBean("studentServiceId");
		studentService.addStudent();
		studentService.updateStudent();
			
	}
	
}
