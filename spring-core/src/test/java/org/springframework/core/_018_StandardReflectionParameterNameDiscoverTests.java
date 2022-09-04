/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.core;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import org.springframework.util.ReflectionUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * **source 2022/09/05   它使用 JDK 8 的反射工具来内省参数名称（基于“-parameters”编译器标志）。
 * Tests for StandardReflectionParameterNameDiscoverer
 *
 * @author Rob Winch
 */
public class _018_StandardReflectionParameterNameDiscoverTests {
	private ParameterNameDiscoverer parameterNameDiscoverer;

	@Before
	public void setup() {
		parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
	}

	@Test
	public void getParameterNamesOnInterface() {
		Method method = ReflectionUtils.findMethod(MessageService.class,"sendMessage", String.class);
		String[] actualParams = parameterNameDiscoverer.getParameterNames(method);
		assertThat(actualParams, is(new String[]{"message"}));
	}

	public interface MessageService {
		void sendMessage(String message);
	}
}
