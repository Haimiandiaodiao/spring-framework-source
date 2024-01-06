package 自测试.注解测试;

import org.aspectj.lang.annotation.RequiredTypes;

import java.lang.annotation.*;

/**
 * @author Dongyangyang
 * @description
 * @date:2024/1/5
 * @mail yangyang.dong@kuwo.cn
 */
public class 基础测试 {


	/**重复注解，指定使用的容器注解*/
	@Repeatable(MyAnnotationContainer.class)
	@Target({ElementType.TYPE.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface  MyAnnotation{

	}

	/**容器注解*/
	@Target({ElementType.TYPE.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface  MyAnnotationContainer{
		MyAnnotation[] value();
	}


	public class ACaless{

	}
}
