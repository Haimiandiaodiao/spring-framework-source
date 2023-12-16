package org.springframework.beans.factory.工厂方法测试;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public  class Keyboard{
		private String name;
	}