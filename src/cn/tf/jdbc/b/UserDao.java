package cn.tf.jdbc.b;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.tf.jdbc.a.User;

public class UserDao {
	
	private JdbcTemplate  jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(User user){
		jdbcTemplate.update("insert into t_user(username,password) values(?,?)", user.getUsername(),user.getPassword());
		
	}

}
