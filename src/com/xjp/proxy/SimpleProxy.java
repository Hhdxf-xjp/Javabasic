package com.xjp.proxy;

import java.lang.reflect.Proxy;

/**
 * com.xjp.proxy.SimpleProxy
 * <p>
 * 简单的代理实现--通过构造器--属于静态的，接口的一种应用
 *
 * @author xujiangpeng
 * @date 2018/7/6
 */
public class SimpleProxy {

    public static void main(String[] args) {

        ProxyClass proxyClass = new ProxyClass();
        proxyClass.doRun();
        proxyClass.doSleep();

    }
}

/**
 * 抽象功能接口
 */
interface Function {
   void doRun();
   void doSleep();
}



/**
 * 代理类
 */
class ProxyClass implements Function {

    ProxyTarget target;

    /**
     * 构造器负责"代理"目标对象
     */
    public ProxyClass() {
        target = new ProxyTarget();
        System.out.println("代理类创建成功");
    }

    @Override
    public void doRun() {
        System.out.println("ProxyClass---doRun");
        target.doRun();
    }

    @Override
    public void doSleep() {
        System.out.println("ProxyClass---doRun");
        target.doSleep();
    }
}

/**
 * 目标类---被代理类
 */
class ProxyTarget implements Function {

    @Override
    public void doRun() {
        System.out.println("ProxyTarget---doRun");
    }

    @Override
    public void doSleep() {
        System.out.println("ProxyTarget---doSleep");
    }
}



