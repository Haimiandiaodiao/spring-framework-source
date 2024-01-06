package 自测试.销毁方法测试;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author Dongyangyang
 * @description
 * @date:2024/1/5
 * @mail yangyang.dong@kuwo.cn
 */
public class 注册Bean销毁方法 {
	//静态工厂使用
	DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class A{
		private String name = "A";

		public void  close(){
			System.out.println("对象的销毁方法");
		}

	}


	@Test
	public void name() {


		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(A.class).setDestroyMethodName("close").getBeanDefinition();

		beanRegistry.registerBeanDefinition("A",beanDefinition);
		//不属于基本的BeanFactory属于应用上线文的内容了
//		ConfigurationClassPostProcessor configurationClassPostProcessor = new ConfigurationClassPostProcessor();
//		configurationClassPostProcessor.postProcessBeanDefinitionRegistry(beanRegistry);
		//查看并不是使用的一个对象
		Object cPriover = beanRegistry.getBean("A");
		System.out.println(JSON.toJSONString(cPriover));

	}
}
