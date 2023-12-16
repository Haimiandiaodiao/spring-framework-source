package org.springframework.beans.factory.工厂方法测试;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public  class Computer{
		private  Keyboard keyboard;
		private  Display display;

	}