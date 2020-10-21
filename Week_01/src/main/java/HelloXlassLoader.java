package main.java;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法
 * 
 * @author minpeng
 * 
 */
public class HelloXlassLoader extends ClassLoader {
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			Class<?> helloClass = new HelloXlassLoader().findClass("Hello");
			Object obj = helloClass.newInstance();
			Method method = helloClass.getMethod("hello");
			method.invoke(obj);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String filename = System.getProperty("user.dir") + "/src/main/resources/Hello.xlass";
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(filename));
			for (int i = bytes.length - 1; i >= 0; i--) {
				bytes[i] = (byte) (255 - bytes[i]);
			}

			return defineClass(name, bytes, 0, bytes.length);

		} catch (IOException e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}

}
