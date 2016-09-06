package cn.tf.jdbc.a;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestApp {
	
	@Test
	public void test1(){
		BasicDataSource  dataSource=new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/dbone");
		dataSource.setUsername("zp");
		dataSource.setPassword("a");
		
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		
		jdbcTemplate.update("insert into t_user(username,password) values(?,?)", "Maike","1234");
	}

}
