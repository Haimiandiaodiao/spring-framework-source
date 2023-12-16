package org.springframework.beans.factory.工厂方法测试;

public class ReplacementBean {
    public void replacementMethod() {
        System.out.println("Replacement method is called instead of the original method.");
    }

	public void originalMethod() {
		System.out.println("Original111111 method is called.");
	}
}