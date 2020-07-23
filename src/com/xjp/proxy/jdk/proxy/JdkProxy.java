package com.xjp.proxy.jdk.proxy;

/**
 * @author xujiangpeng
 * @ClassName:
 * @Description: (这里用一句话描述这个类的作用)
 * @date
 */
public class JdkProxy {


}


/**
 * 接口
 */
interface Subject {
    public void rent();

    public void hello(String str);
}


/**
 * 实现一个接口的目标类，普通类
 */
class RealSubject implements Subject {
    @Override
    public void rent() {
        System.out.println("I want to rent my house");
    }

    @Override
    public void hello(String str) {
        System.out.println("hello: " + str);
    }
}



