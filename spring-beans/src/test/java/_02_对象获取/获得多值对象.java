package _02_对象获取;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.工厂方法测试.静态参数工厂的使用;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dongyangyang
 * @description
 * @date:2023/12/29
 * @mail yangyang.dong@kuwo.cn
 */
public class 获得多值对象 {
	interface  Animal{

	}
	@Data

	public static class Cat implements Animal{

	}

	@Data

	public static class Dog implements Animal{

	}

	public static class Zoo {
		Collection<Animal> animals;
	}


	DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();

	@Test
	public void 获得一个Map() throws NoSuchFieldException {
		AbstractBeanDefinition catDef = BeanDefinitionBuilder.genericBeanDefinition(Cat.class).getBeanDefinition();
		AbstractBeanDefinition dogDef = BeanDefinitionBuilder.genericBeanDefinition(Dog.class).getBeanDefinition();
		beanRegistry.registerBeanDefinition("cat",catDef);
		beanRegistry.registerBeanDefinition("dog",dogDef);

		DependencyDescriptor animals = new DependencyDescriptor(Zoo.class.getDeclaredField("animals"), true);
		HashSet<String> animalsNames = new HashSet<>();
		Object zoo = beanRegistry.doResolveDependency(animals, "zoo", animalsNames, beanRegistry.getTypeConverter());
		
		System.out.println(zoo);


	}
}
