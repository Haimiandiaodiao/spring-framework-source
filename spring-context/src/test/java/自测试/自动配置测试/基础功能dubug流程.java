package 自测试.自动配置测试;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

/**
 * @author Dongyangyang
 * @description
 * @date:2023/12/29
 * @mail yangyang.dong@kuwo.cn
 */
public class 基础功能dubug流程 {
	//静态工厂使用
	DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class C{
		private  String name = "C";
		private  D d;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class D{
		private  String name = "D";
	}



	@Configuration
	public static class E{

		@Bean
		public C cPriover(){
			return new C();
		}

		@Bean
		public D dPriover(){
			return new D();
		}

	}

	@Configuration
	public static class F{
		@Bean
		public C cPriover(){
			//查看生成的是一个对象么
			D d = dPriover();
			C c = new C();
			c.setD(d);
			return c;
		}
		@Bean
		public D dPriover(){
			return new D();
		}

	}

	@Configuration
	public static class G{
		@Bean
		public C cPriover(D d){
			//查看生成的是一个对象么
			C c = new C();
			c.setD(d);
			return c;
		}
		@Bean
		public D dPriover(){
			return new D();
		}

	}


	@Test
	public void 配置类的基础使用() {

		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(E.class).getBeanDefinition();

		beanRegistry.registerBeanDefinition("config",beanDefinition);
		//不属于基本的BeanFactory属于应用上线文的内容了
		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanRegistry);

		Object cPriover = beanRegistry.getBean("cPriover");
		System.out.println(JSON.toJSONString(cPriover));

	}


	@Test
	public void Bean引用的方法和直接调用的是一个么对象么() {

		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(F.class).getBeanDefinition();

		beanRegistry.registerBeanDefinition("config",beanDefinition);
		//不属于基本的BeanFactory属于应用上线文的内容了
		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanRegistry);
		//查看并不是使用的一个对象
		Object cPriover = beanRegistry.getBean("cPriover");
		Object dPriover = beanRegistry.getBean("dPriover");
		System.out.println(JSON.toJSONString(cPriover));

	}
	@Test
	public void Bean引用的方法和直接调用的是一个么对象么2() {

		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(G.class).getBeanDefinition();

		beanRegistry.registerBeanDefinition("config",beanDefinition);
		//不属于基本的BeanFactory属于应用上线文的内容了
		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanRegistry);
		//查看并不是使用的一个对象
		Object cPriover = beanRegistry.getBean("cPriover");
		Object dPriover = beanRegistry.getBean("dPriover");
		System.out.println(JSON.toJSONString(cPriover));

	}
}
