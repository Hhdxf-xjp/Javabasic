package com.xjp;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.xjp.Test --- 测试类
 *
 * @author xujiangpeng
 * @date 2018/7/27
 */
public class Test {

    public static void main(String[] args) {

        List<String> list = null;

        //list = Collections.EMPTY_LIST;

        for(String str : emptyIfNull(list)){

            System.out.println(str.toUpperCase());

        }
    }

    /**
     * 确保集合遍历不会空指针
     * @author xjp
     */
    public static <T> T emptyIfNull(T other) {
        try {
            return other == null ? (T) new Object() : other;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 确保集合遍历不会空指针
     * @author xjp
     */
    public static <T> T emptyIfNullll(T other) {


        //空指针。
        try {
            return other == null ? (T) other.getClass().newInstance() : other;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

