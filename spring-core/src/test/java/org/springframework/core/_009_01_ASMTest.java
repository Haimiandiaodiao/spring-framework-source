package org.springframework.core;

import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

public class _009_01_ASMTest {

	public static class A{
		private String name;
		private Integer age;
		public void show(String defaultName, Integer defaultAge){

		}

	}


	@Test
	public void baseTestAsm() throws IOException {
		ClassReader a = new ClassReader("_009_01_ASMTest$A");


	}
}
