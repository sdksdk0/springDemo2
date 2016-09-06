package cn.tf.demo5;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TestApp {
	
	
	@Test
	public void test2(){
		
		String xmlpath="cn/tf/demo5/beans.xml";
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext(xmlpath);
		TeacherService teacherService = (TeacherService) applicationContext.getBean("teacherServiceId");
		teacherService.addTeacher();
		teacherService.updateTeacher();
			
	}
	
}
