package cn.tf.jdbc.c;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cn.tf.jdbc.a.User;

public class UserDao  extends JdbcDaoSupport {

	
	public void save(User user){
		getJdbcTemplate().update("insert into t_user(username,password) values(?,?)", user.getUsername(),user.getPassword());	
	}

}
