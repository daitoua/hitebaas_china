package com.hitebaas.util.comom;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class CommomUtil {

	
	public static String  readerFile(File file) {
		try {
			return  FileUtils.readFileToString(file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("解压缩文件解析失败");
		}
		return null;
	}
	
	  /**
     * map 转实体类
     * @param clazz
     * @param map
     * @param <T>
     * @return
     */
    public static <T> T convertMapToBean(Class<T> clazz, Map<String,Object> map) {
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // 创建 JavaBean 对象


            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);
                    if ("".equals(value)) {
                        value = null;
                    }
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);


                }
            }
        } catch (Exception e) {
        	
        }
        return (T) obj;
    }
}
