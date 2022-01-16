package org.springframework.core.convert;

import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;

public class _002_PropertyEditor {
	/**PropertyEditor 用来进行映射的属性编辑器*/
	class Person {
		private String name;
		private Integer age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person{" +
					"name='" + name + '\'' +
					", age=" + age +
					'}';
		}
	}
	/**
	 *  自定义的属性编辑器
	 */
	class CustomePropertyEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			String[] fieldSpilter = text.split(":");
			if(fieldSpilter.length != 2){return;}
			Person person = new Person();
			person.setName(fieldSpilter[0]);
			person.setAge(Integer.valueOf(fieldSpilter[1]));
			setValue(person);
		}

		@Override
		public String getAsText() {
			Object value = getValue();
			if(value instanceof  Person){
				Person value1 = (Person) value;
				return  value1.getName()+":"+value1.getAge();
			}
			return  "";
		}

		@Override
		public void setValue(Object value) {
			super.setValue(value);
		}

		@Override
		public Object getValue() {
			return super.getValue();
		}


	}


	@Test
	public void 基础的属性编辑器使用() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Object newValue = evt.getNewValue();
				Object oldValue = evt.getOldValue();
				Object propagationId = evt.getPropagationId();
				String propertyName = evt.getPropertyName();
				System.out.println("触发了监听器"+newValue+"--"+oldValue+"--"+propagationId+"--"+propertyName);
			}
		};

		CustomePropertyEditor customePropertyEditor = new CustomePropertyEditor();
		customePropertyEditor.addPropertyChangeListener(listener);

		customePropertyEditor.setAsText("dyy:11");
		System.out.println(customePropertyEditor.getValue());
		customePropertyEditor.setAsText("zzq:12");
		System.out.println(customePropertyEditor.getValue());

	}
}
