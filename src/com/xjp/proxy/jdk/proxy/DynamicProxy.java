package com.xjp.proxy.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xujiangpeng
 * @ClassName:
 * 定义一个动态代理类了，前面说个，每一个动态代理类都必须要实现 InvocationHandler 这个接口，
 *
 * 因此我们这个动态代理类也不例外：
 * @date
 */
public class DynamicProxy implements InvocationHandler {

    /**
     * 要代理的真实对象
     */
    private Object subject;

    /**
     * 将要代理的对象，通过构造函数放入代理类属性中。
     */
    public DynamicProxy(Object subject) {
        this.subject = subject;
    }


    /**
     *
     * invoke前后可以添加自己的方法
     *
     * @param object
     * @param method
     * @param args
     * @return
     * @throws Throwable
     * //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
     */
    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {

        //在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before real method");
        System.out.println("Method:" + method);

        //调用实际方法
        Object ret = method.invoke(subject, args);

        //在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after real method");

        return ret;
    }

}
