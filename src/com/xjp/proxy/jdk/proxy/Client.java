package com.xjp.proxy.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author xujiangpeng
 * @ClassName:
 * @Description: (这里用一句话描述这个类的作用)
 * @date
 */
public class Client {


    /**
     * 1//我们要代理的真实对象
     * 2//我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
     * 3、因为代理类和目标类都是实现了同一组接口，所以可以进行强制转换
     * 第一个参数：
     */

    /*
     *
     * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
     * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
     * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
     */
    public static void main(String[] args) {

        //目标对象
        Subject realSubject = new RealSubject();

        //生成目标对象的处理器handler
        InvocationHandler handler = new DynamicProxy(realSubject);

        //代理类的 class loader，，用来加载代理对象？？
        ClassLoader classLoader = handler.getClass().getClassLoader();

        //class对象集合，，，可以表示目标类所实现的所有接口
        Class<?>[] classes = realSubject.getClass().getInterfaces();

        //通过proxy创建代理对象
        Subject subject = (Subject) Proxy.newProxyInstance(classLoader, classes, handler);

        System.out.println(subject.getClass().getName());
        subject.rent();
        subject.hello("world");
    }
}

