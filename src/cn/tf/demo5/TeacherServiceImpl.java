package cn.tf.demo5;

public class TeacherServiceImpl implements TeacherService {

	@Override
	public void addTeacher() {
		System.out.println("add teacher");
		
	}

	@Override
	public String updateTeacher() {
		System.out.println("update teacher");
		return "更新完毕";
	}

}
