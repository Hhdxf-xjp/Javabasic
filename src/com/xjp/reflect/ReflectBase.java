package com.xjp.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * com.xjp.reflect.ReflectBase
 * <p>
 * 反射的基础
 *
 * @author xujiangpeng
 * @date 2018/7/6
 */
public class ReflectBase {

    /*
     * java.lang.Class:是反射的源头。
     *
     * 我们创建了一个类，通过编译（javac.exe）,生成对应的.class文件。
     * 使用java.exe加载（JVM的类加载器完成的） 此.class文件，
     * 此.class文件加载到内存以后，就是一个运行时类，存在在缓存区。
     *
     * 那么这个运行时类本身就是一个Class的实例！
     * 1.每一个运行时类只加载一次！
     * 2.有了Class的实例以后，我们才可以进行如下的操作：
     *     1）*创建对应的运行时类的对象
     *     2）获取对应的运行时类的完整结构（属性、方法、构造器、内部类、父类、所在的包、异常、注解、...）
     *     3）*调用对应的运行时类的指定的结构(属性、方法、构造器)
     *     4）反射的应用：动态代理
     */

    /**
     * 在有反射以前，如何创建一个类的对象，并调用其中的方法、属性
     */
    @Test
    public void test1() {
        Person person01 = new Person();

        person01.setAge(24);
        person01.setName("xjp");
        person01.info("xjp info!");

        //com.xjp.reflect.Person@3ac42916
        System.out.println(person01);
    }

    /**
     * 使用反射。通过反射创建一个类的对象，并调用其中的结构
     */
    @Test
    public void test2() throws Exception {

        //类名的话可以直接调用属性class，而对象名使用getClass()方法
        Class clazz = Person.class;
        //class com.xjp.reflect.Person
        System.out.println(clazz);

        //1.创建clazz对应的运行时类Person类的对象
        //com.xjp.reflect.Person@3ac42916
        Person person = (Person) clazz.newInstance();
        System.out.println(person);

        //2.通过反射调用运行时类的指定的属性--公有
        //返回一个 Field 对象，它反映此 Class 对象所表示的类或接口的指定公共成员字段。
        Field field = clazz.getField("name");

        //public java.lang.String com.xjp.reflect.Person.name
        System.out.println(field);

        //方法:get(Object obj) 返回指定对象obj上此 Field 表示的字段的值,参数是该对象
        String name = (String) field.get(person);
        field.set(person, "xjp");

        //私有
        //返回一个 Field 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明字段（包括私有成员）。
        Field field2 = clazz.getDeclaredField("age");

        field2.get("age");

        /*
         *can not access a member of class com.xjp.reflect.Person with modifiers "private"
         *如果不设置成true,那么就会出现可以看到属性， 但是不能访问值。
         */

        //获取属性的值用get(Object obj)的方法，但是获取私有属性的时候必须先设置Accessible为true，然后才能获取。
        field2.setAccessible(true);
        field2.set(person, 22);
        //22
        System.out.println(person.getAge());

        //3.通过反射调用运行时类的指定的方法---方法名+方法参数类型
        //无参的话，就不用参数
        Method method = clazz.getMethod("info", String.class);
        //xjp info!
        method.invoke(person, "xjp info!");

        //4.通过运行时类对象，获取运行时类
        clazz = person.getClass();
        //class com.xjp.reflect.Person
        System.out.println(clazz);
    }
}

class Person {
    public String name;
    private int age;

    public void info(String info) {
        System.out.println(info);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

