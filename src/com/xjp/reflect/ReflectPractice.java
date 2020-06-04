package com.xjp.reflect;

import com.xjp.com.xjp.bean.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * com.xjp.reflect.ReflectPractice
 * <p>
 * 反射的应用和练习
 *
 * @author xujiangpeng
 * @date 2018/7/19
 */
public class ReflectPractice {

    private static Logger logger = LoggerFactory.getLogger(ReflectPractice.class);

    public static void main(String[] args) {

        Person person = new Person();

        System.out.println(person.getName());

        setFieldValueByName("name","xjp",person);

        System.out.println(person.getName());

        System.out.println(getFieldValueByName("name",person));

    }


    /**
     * 通过属性名调用对应的setter方法进行赋值
     *
     * <p>基本类型不能getClass,所以需要使用Field
     *
     * @param fieldName 属性名称
     * @param filedVal  属性值
     * @param targetObj 目标对象
     */
    @NotNull
    public static void setFieldValueByName(String fieldName, Object filedVal, Object targetObj) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Field field = targetObj.getClass().getDeclaredField(fieldName);
            Method method = targetObj.getClass().getMethod(setter, field.getType());
            method.invoke(targetObj, filedVal);
        } catch (Exception e) {
            logger.error("常量类--setFieldValueByName 异常！", e);
        }
    }

    public static Object getFieldValueByName(String fieldName, Object targetObj) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = targetObj.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(targetObj, new Object[] {});
            return value;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}

