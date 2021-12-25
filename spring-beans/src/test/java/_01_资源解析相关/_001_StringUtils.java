package _01_资源解析相关;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class _001_StringUtils {
	/**
	 *  1.可以完成对win的文件分隔符的转换
	 *  2.可以完成../和./的转换
	 */
	@Test
	public void 基础使用测试() {
		String pathToUse = StringUtils.cleanPath("C:\\Windows\\..\\.\\System32\\drivers\\etc");
		System.out.println(pathToUse);

	}
}
