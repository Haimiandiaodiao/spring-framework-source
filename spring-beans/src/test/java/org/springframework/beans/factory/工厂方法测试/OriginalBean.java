package org.springframework.beans.factory.工厂方法测试;

public class OriginalBean {
    public Object originalMethod() {
        System.out.println("Original method is called.");
        return null;
    }
}