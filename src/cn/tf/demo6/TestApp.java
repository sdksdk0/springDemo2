package cn.tf.demo6;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:cn/tf/demo6/beans.xml")
public class TestApp {
	
	@Autowired
	private TeacherService teacherService;

	@Test
	public void test2(){
		
		teacherService.addTeacher();
		teacherService.updateTeacher();
			
	}
	
}
