# springDemo2
spring基础，包括AOP、aspectJ切入点表达式、jdbc模板等

#AOP
面向切面编程：采用横向抽取机制，取代了传统纵向继承体系重复性代码。

AOP的应用：性能监视、事务管理、安全检查、缓存等。

target:目标类，需要被代理的类，也是需要被增强的类。

JoinPoint:连接点，需要被拦截点，spring中连接点就是方法，及目标类所有方法。
PointCut:切入点，已经被拦截的连接点，有哪些连接点需要被增强。
Advice:通知/增强，增强的内容。
Weaving:织入，用增强Advice应用目标类Target,生成代理对象过程。
proxy:代理。
aspect:切面，通知advice与切入点Point


###spring AOP底层
接口+实现类：使用jdk的动态代理。

实现类：使用的是cglib（字节码增强）


字节码增强框架，不用使用接口，在运行时，动态的创建目标类的子类，目标类不能使用final。


目标类：


    public class BookService {
	public void addBook(){
		System.out.println("cglib addBook");
	}
	
	public void updateBook(){
		System.out.println("cglib updateBook");
	}
    }

切面类：

    public class MyAspect {
	
	public void before(){
		System.out.println("之前执行");
	}
	public void after(){
		System.out.println("之后执行");
	}
    }



工厂：


    public static Object getBean(){
		final BookService  bookService=new BookService();
		//切面类
		final MyAspect myAspect=new MyAspect();
		//生成代理类。代理类是目标类的子类
		//核心类
		Enhancer enhancer=new Enhancer();
		//确定父类
		enhancer.setSuperclass(bookService.getClass());
		//设置处理
		enhancer.setCallback(new MethodInterceptor() {
			
			@Override
			public Object intercept(Object proxy, Method method, Object[] args,
					MethodProxy methodProxy) throws Throwable {
				//执行目标类的方法
				myAspect.before();
				Object obj=method.invoke(bookService, args);
				methodProxy.invokeSuper(proxy, args);
				myAspect.after();
				return obj;
			}
		});	
		//创建代理类
		Object proxyObj=enhancer.create();
		return proxyObj;
		
	}


###aop通知类型

aop是一种思想，有aop联盟提出，spring对aop的规范进行支持。

5种通知：
前置通知：在目标方法执行之前实施增强。
后置通知：在执行目标后实施增强。
环绕通知：在方法执行前后实施增强。
异常抛出通知：在方法抛出异常后实施增强。
引介通知：在目标类中添加一些新的方法和属性。


###spring工厂bean--半自动
切面类：需要实现接口， MethodInterceptor 环绕通知


    public class MyAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		System.out.println("前");
		//执行目标方法
		Object obj = mi.proceed();
		System.out.println("后");
		return obj;
	}
    }

配置文件：

	<bean id="personServiceId" class="cn.tf.demo3.PersonServiceImpl"></bean>
	
	<!-- 切面类，含通知 -->
	<bean id="myAspect" class="cn.tf.demo3.MyAspect" ></bean>
	
	<!-- 
			ProxyFactoryBean 用于生产代理类一个特殊的工厂bean
			proxyInterfaces 用于确定需要实现接口
			interceptorNames 用于确定通知实现类，需要提供的bean名称
			target 用于确定代理类bean名称
	 -->
	
	<!-- 代理类 -->
	<bean id="personServiceProxyId"  class="org.springframework.aop.framework.ProxyFactoryBean">
		<!-- 确定接口 -->
		<property name="proxyInterfaces" value="cn.tf.demo3.PersonService"></property>
		<!-- 确定通知 -->
		<property name="interceptorNames"  value="myAspect"></property>
		<!-- 确定目标类 -->
		<property name="target"  ref="personServiceId"></property>
		<!-- 强制cglib -->
		<property name="optimize" value="true"></property>
		
	</bean>


###AOP全自动
使用AspectJ的表达式


<bean id="studentServiceId" class="cn.tf.demo4.StudentServiceImpl"></bean>
	
	<!-- 切面类，含通知 -->
	<bean id="myAspect" class="cn.tf.demo4.MyAspect" ></bean>
	<!-- aop编程 -->
	<aop:config>
		<!-- 切入点 -->
		<aop:pointcut expression="execution(* cn.tf.demo4.*ServiceImpl.*(..))" id="myPointCut"/>
		<!-- 一个特殊的切面  ,包含一个切入点和一个通知-->
		<aop:advisor advice-ref="myAspect"  pointcut-ref="myPointCut"/>
	</aop:config>




#aspectJ切入点表达式
导入jar包：
aspectJ、springaop、aop联盟规范、

1、execution

execution:匹配方法的执行
格式：execution(修饰符 返回值类型 包.类名.方法名(参数列表)throws 异常)

修饰符：public|private|* 

返回值类型：String|void|*

包：cn.tf.service  --指定包
cn.tf.pm.*.Service  --指定模块
cn.tf.pm.*.service..   --表示当前目录以及子目录

类名：UserService  --指定名称
*Service  --以Service结尾

方法名：save  --执行方法名称
add*  --add开头

参数列表：
()  --无参
（int)  --参数整形
（String,int） --两个参数
(..)   --参数任意
	

execution(* cn.tf.*.service..*.*(..))

2、within
确定包或者子包
within(cn.tf.pm.*.service..)

3、this
匹配实现接口的代理类
this(cn.tf.pm.user.service.UserService)

4、taeget
目标类

5、args
参数列表

6、bean

##通知类型

前置通知[Before advice]：在连接点前面执行，前置通知不会影响连接点的执行，除非此处抛出异常。
 
正常返回通知[After returning advice]：在连接点正常执行完成后执行，如果连接点抛出异常，则不会执行。 

异常返回通知[After throwing advice]：在连接点抛出异常后执行。 

返回通知[After (finally) advice]：在连接点执行完成后执行，不管是正常执行完成，还是抛出异常，都会执行返回通知中的内容。 

环绕通知[Around advice]：环绕通知围绕在连接点前后，比如一个方法调用的前后。这是最强大的通知类型，能在方法调用前后自定义一些操作。环绕通知还需要负责决定是继续处理join point(调用ProceedingJoinPoint的proceed方法)还是中断执行。



 声明通知类型 

				1 前置通知 , 目标方法之前执行。
					* 第一个参数为JoinPoint，可以获得目标方法名等。
				<aop:before method="myBefore" pointcut-ref="myPonitCut"/>
				2 后置通知，目标方法之后执行，可以获得返回值。 通过“returning”属性配置第二个参数的名称，获得返回值的，类型必须Object
					* 第一个参数为：JoinPoint
					* 第二个参数为：Object xxx
				<aop:after-returning method="myAfterReturning" pointcut-ref="myPonitCut" returning="xxx"/>
				3 环绕通知， 目标方法前后
					方法要求：public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
					执行目标方法：joinPoint.proceed();
				<aop:around method="myAround" pointcut-ref="myPonitCut"/>
				4 抛出异常通知，目标方法出现异常时才执行。通过“throwing”属性配置第二个参数的名称,获得具体的异常信息，类型必须是Throwable
					* 第一个参数为：JoinPoint
					* 第二个参数为：Throwable e
				<aop:after-throwing method="myAfterThrowing" pointcut-ref="myPonitCut" throwing="e"/>



切面类：

    public void myBefore(JoinPoint  joinPoint){
		System.out.println("前置通知"+joinPoint.getSignature().getName());
	}
	
	public void myAfterReturning(JoinPoint joinPoint,Object xxx){
		System.out.println("后置通知, 返回值：" + xxx);
	}
	
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		
		System.out.println("前");
		//必须执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知, " + e.getMessage());
	}
	
	public void myAfter(){
		System.out.println("最终");
	}


配置文件：

	<aop:config>
		<aop:aspect  ref="myAspect">
			<aop:pointcut expression="execution(* cn.tf.demo5.*.*(..))" id="myPointCut"/>
			<!-- 声明通知类型 -->
			<aop:before method="myBefore"  pointcut-ref="myPointCut"/> 
			<aop:after-returning method="myAfterReturning" pointcut-ref="myPointCut" returning="xxx"/>
			<aop:around method="myAround"  pointcut-ref="myPointCut" />
			<aop:after-throwing method="myAfterThrowing"  pointcut-ref="myPointCut"  throwing="e"/>
			<aop:after method="myAfter"  pointcut-ref="myPointCut"/>
			
			
		</aop:aspect>
	</aop:config>


##基于注解

如果使用注解进行aop开发，必须进行aspectj自动代理。

	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>				   	


通知注解：

@Before
@AfterReturning
@Around
@AfterThrowing
@After



    @Component
    @Aspect
    public class MyAspect {
	
	@Before("myPointCut()")
	public void myBefore(JoinPoint  joinPoint){
		System.out.println("前置通知"+joinPoint.getSignature().getName());
	}
	
	@AfterReturning(value="execution(* cn.tf.demo6.*.*(..))",returning="xxx")
	public void myAfterReturning(JoinPoint joinPoint,Object xxx){
		System.out.println("后置通知, 返回值：" + xxx);
	}
	
	@Around("myPointCut()")
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		
		System.out.println("前");
		//必须执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	@AfterThrowing(value="myPointCut()",throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知, " + e.getMessage());
	}
	@After("myPointCut()")
	public void myAfter(){
		System.out.println("最终");
	}
	//用来声明切入点表达式，在通知中通过方法名来获得，相当于调用方法
	@Pointcut("execution(* cn.tf.demo6.*.*(..))")
	private void myPointCut(){
		
	}
    }

配置文件：
    
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:context="http://www.springframework.org/schema/context"
    xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
       	http://www.springframework.org/schema/context/spring-context.xsd
        "> <!-- bean definitions here -->

    	
    	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>				   	
		<!-- 扫描注解 -->
		<context:component-scan base-package="cn.tf.demo6"></context:component-scan>
    </beans>


#JDBC模板

导入spring中的jdbc,tx,c3p0,dbcp


属性文件：jdbcInfo.properties
    
    jdbc.driverClass=com.mysql.jdbc.Driver
    jdbc.jdbcUrl=jdbc:mysql://localhost:3306/dbone
    jdbc.user=zp
    jdbc.password=a


在spring的配置文件中配置：

	<!-- 加载properties文件 -->
	<context:property-placeholder location="classpath:cn/tf/jdbc/d/jdbcInfo.properties"/>
	
	<!-- 配置数据源 
		如果properties文件已经被加载，可以通过 ${key}获得配置文件中内容
	-->
	<bean id="dataSourceId" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
	</bean>
	
	<!-- 配置dao -->
	<bean id="userDaoId" class="cn.tf.jdbc.d.UserDao">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>


dao的配置可以依据实际情况写。

