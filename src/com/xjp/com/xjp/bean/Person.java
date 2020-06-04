package com.xjp.com.xjp.bean;

/**
 * com.xjp.com.xjp.bean.Person
 *
 * @author xujiangpeng
 * @date 2018/7/19
 */
public class Person {

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

