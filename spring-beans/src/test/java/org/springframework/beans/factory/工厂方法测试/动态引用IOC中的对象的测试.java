package org.springframework.beans.factory.工厂方法测试;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.*;

/**
 * @author Dongyangyang
 * @description
 * @date:2023/10/9
 * @mail yangyang.dong@kuwo.cn
 */
public class 动态引用IOC中的对象的测试 {

	@Test
	public void BeanDefintion注入运行时对象() {
		DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
		RootBeanDefinition display = new RootBeanDefinition(Display.class);
		
		RootBeanDefinition displayName = new RootBeanDefinition(String.class);
		displayName.setInstanceSupplier(()->"TCL显示器");
		beanRegistry.registerBeanDefinition("displayName",displayName);

		//动态注入这个对象
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("name",new RuntimeBeanReference("displayName"));
		display.setPropertyValues(propertyValues);
		beanRegistry.registerBeanDefinition("TclDisplay",display);


		Object tclDisplay = beanRegistry.getBean("TclDisplay");
		System.out.println(JSON.toJSONString(tclDisplay));
	}


	@Test
	public void 使用BeanDefintionBuilder来注入运行时对象和上面完成同样的功能() {
		DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();

		RootBeanDefinition displayName = new RootBeanDefinition(String.class);
		displayName.setInstanceSupplier(()->"TCL显示器");
		beanRegistry.registerBeanDefinition("displayName",displayName);

		AbstractBeanDefinition display = BeanDefinitionBuilder.rootBeanDefinition(Display.class).addPropertyReference("name", "displayName")
				.getBeanDefinition();
		//动态注入这个对象
		beanRegistry.registerBeanDefinition("TclDisplay",display);



		Object tclDisplay = beanRegistry.getBean("TclDisplay");
		System.out.println(JSON.toJSONString(tclDisplay));
	}


	@Test
	public void name() {
		GenericBeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClass(Keyboard.class);
		definition.setSource("dddddddddd");
		Object source = definition.getSource();
		System.out.println(source);
	}
}
