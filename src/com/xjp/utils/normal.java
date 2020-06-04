package com.xjp.utils;

/**
 * com.xjp.util.normal
 * <p>
 * 常用工具类
 *
 * @author xujiangpeng
 * @date 2018/7/25
 */
public class normal {

    public static void main(String[] args) {

        String str = "xjp";
        str = upperCaseFirstLatter(str);

        System.out.println(str);

    }

    /**
     * 这里先将字符串转为字符数组，然后将数组的第一个元素，即字符串首字母，
     * 进行ASCII 码前移，ASCII 中大写字母从65开始，小写字母从97开始，所以这里减去32。
     * @param value
     * @return
     */
    public static String upperCaseFirstLatter(String value) {
        char[] ch = value.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}

