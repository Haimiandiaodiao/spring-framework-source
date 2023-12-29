package org.springframework.beans.factory.工厂方法测试;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;

import java.lang.reflect.Method;
import java.security.Key;

/**
 * @author Dongyangyang
 * @description
 * @date:2023/10/9
 * @mail yangyang.dong@kuwo.cn
 */
public class 静态参数工厂的使用 {

	public static class ComputerFactory{
		public static Computer  producComputer(Keyboard keyboard,Display display){
			Computer computer = new Computer(keyboard, display);
			return computer;
		}
	}

	//静态工厂使用
	DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();

	@Test
	public void singleObjectRejes() {
		DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
		String  objects = "11111";
		beanRegistry.registerSingleton("single",objects);

		Object single = beanRegistry.getBean("single");

		System.out.println(single);

	}
	@Test
	public void staticFactoryUse() {

		RootBeanDefinition key = new RootBeanDefinition(Keyboard.class);
		key.setInstanceSupplier(()->Keyboard.builder().name("联想键盘").build());

		RootBeanDefinition dis = new RootBeanDefinition(Display.class);
		dis.setInstanceSupplier(()->Display.builder().name("联想键盘").build());


		beanRegistry.registerBeanDefinition("key",key);
		beanRegistry.registerBeanDefinition("dis",dis);


		AbstractBeanDefinition computer = BeanDefinitionBuilder.rootBeanDefinition(ComputerFactory.class, "producComputer")
				.addConstructorArgReference("key")
				.addConstructorArgReference("dis")
				.getBeanDefinition();


		beanRegistry.registerBeanDefinition("computer",computer);

		Object computer1 = beanRegistry.getBean("computer");
		System.out.println(JSON.toJSONString(computer1));
	}

	@Test
	public void 手动注册单例() {
		Keyboard keyboard = Keyboard.builder().name("联想键盘").build();
		beanRegistry.registerSingleton("key",keyboard);

		Object key = beanRegistry.getBean("key");
		System.out.println(JSON.toJSONString(key));

	}

	@Test
	public void 手动注册FactoryBean() {
		FactoryBean factory = new FactoryBean() {
			@Override
			public Object getObject() throws Exception {
				return Keyboard.builder().name(String.valueOf(System.currentTimeMillis())).build();

			}
			@Override
			public Class<?> getObjectType() {
				return Keyboard.class;
			}
		};
		//会被加入到缓存中不会重复的创建单例
		beanRegistry.registerSingleton("key",factory);
		for (int i = 0; i < 3; i++) {
			Object key = beanRegistry.getBean("key");
			System.out.println(JSON.toJSONString(key));
		}
		//&是返回对象的工厂
		Object bean = beanRegistry.getBean("&key");
		System.out.println(bean);

	}

	@Test
	public void NullBean的使用() {
		//注册一个空对象到 工厂中
		// NullBean 外部不可以对其进行引用
	}

	@Test
	public void overMethod的使用_replaceOverride() {
		AbstractBeanDefinition originalBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(OriginalBean.class).getBeanDefinition();

		//方法替换器，对指定的方法进行替换执行
		MethodReplacer methodReplace = new MethodReplacer() {
			@Override
			public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
				System.out.println("我实现了方法替换");
				return null;
			}
		};
		beanRegistry.registerSingleton("replacement",methodReplace);
		//标明 originalMethod方法使用的是replacement这个方法替换器来进行执行的
		ReplaceOverride replaceOverride = new ReplaceOverride("originalMethod", "replacement");
		originalBeanDefinition.getMethodOverrides().addOverride(replaceOverride);

		beanRegistry.registerBeanDefinition("original",originalBeanDefinition);
		OriginalBean original = (OriginalBean)beanRegistry.getBean("original");


		original.originalMethod();


	}

	@Test
	public void overMethod的使用_lookup() {
		AbstractBeanDefinition original = BeanDefinitionBuilder.genericBeanDefinition(OriginalBean.class).getBeanDefinition();
		AbstractBeanDefinition replacement = BeanDefinitionBuilder.genericBeanDefinition(ReplacementBean.class).getBeanDefinition();

		beanRegistry.registerBeanDefinition("original",original);
		beanRegistry.registerBeanDefinition("replacement",replacement);

		//!!标识 originalMethod方法返回的是 工厂中的replacement对象
		LookupOverride lookupOverride = new LookupOverride("originalMethod", "replacement");
		original.getMethodOverrides().addOverride(lookupOverride);


		OriginalBean original1 = (OriginalBean)beanRegistry.getBean("original");
		//返回的是对象  是一种spring提供的方法注入的一种
		ReplacementBean o = (ReplacementBean)original1.originalMethod();
		System.out.println(o);

		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class A{
		@Autowired
		private B b;


	}



	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class B{
		@Autowired
		private A a;


	}
	@Data
	@AllArgsConstructor
	public static class C{

	}
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class D{
		@Autowired
		private C c;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class E{

		@Autowired
		private C c;
	}


	/***
	 *
	 * !!!!不能直接调用A的toString 方法否则会有方法溢出
	 *
	 */
	@Test
	public void 自动注入解决循环依赖() {

		AbstractBeanDefinition a = BeanDefinitionBuilder.genericBeanDefinition(A.class).addPropertyReference("b","b").getBeanDefinition();
		AbstractBeanDefinition b = BeanDefinitionBuilder.genericBeanDefinition(B.class).addPropertyReference("a","a").getBeanDefinition();

		beanRegistry.registerBeanDefinition("a",a);
		beanRegistry.registerBeanDefinition("b",b);

		Object A = beanRegistry.getBean("a");

		System.out.println(JSON.toJSONString(A));
	}	


	@Test
	public void 自动注入的方法AutowiredAnnotationBeanPostProcessor测试() {
		AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
		autowiredAnnotationBeanPostProcessor.setBeanFactory(beanRegistry);
		beanRegistry.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);
		AbstractBeanDefinition a = BeanDefinitionBuilder.genericBeanDefinition(C.class).getBeanDefinition();
		AbstractBeanDefinition b = BeanDefinitionBuilder.genericBeanDefinition(D.class).getBeanDefinition();

		beanRegistry.registerBeanDefinition("c",a);
		beanRegistry.registerBeanDefinition("d",b);

		Object A = beanRegistry.getBean("d");
		System.out.println(JSON.toJSONString(A));
	}
}
