package cn.tf.jdbc.d;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import cn.tf.jdbc.a.User;



public class UserDao extends JdbcDaoSupport {
	
	public void save(User user){
		getJdbcTemplate().update("insert into t_user(username,password) values(?,?)", user.getUsername(),user.getPassword());
	}

	public User findById(int userId) {
		String sql = "select * from t_user where id = ?";
		RowMapper<User> rowMapper =ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		List<User> allUser = getJdbcTemplate().query(sql, rowMapper, userId);
		return allUser.get(0) ;
	}
	public User findById2(int userId) {
		String sql = "select * from t_user where id = ?";
		RowMapper<User> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
	}

}
