package com.xjp.aspic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * com.xjp.aspic.testaj
 *
 * @author xujiangpeng
 * @date 2018/7/6
 */
@Aspect
@Component
public class testaj {

    // private static final Logger logger =
    // LoggerFactory.getLogger(SystemLogAspect.class	);

    @Pointcut(value = "within(com.xjp.*)")
    public void controllerAspect() {

    }

    @Around(value = "controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("******************beforeMethod******************");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("requestMapping: " + getRequestMapping(pjp));
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("类名: " + pjp.getTarget().getClass().getName());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("方法名: " + pjp.getSignature().getName());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("参数: " + Arrays.asList(pjp.getArgs()));
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("******************beforeMethod******************");
            System.out.println(new String(stringBuilder.toString().getBytes(), "UTF-8"));
            result = pjp.proceed();
            stringBuilder.setLength(0);
            stringBuilder.append("******************afterMethod******************");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("类名: " + pjp.getTarget().getClass().getName());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("方法名: " + pjp.getSignature().getName());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("返回值: " + result);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("******************afterMethod******************");
            System.out.println(new String(stringBuilder.toString().getBytes(), "utf-8"));
        } catch (Exception e) {
            System.out.println("occurs exception:" + e);
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 获取方法RequestMapping注解的值
     *
     * @param pjp
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    private String getRequestMapping(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();

        if (targetMethod.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = (RequestMapping) targetMethod.getAnnotation(RequestMapping.class);
            return Arrays.asList(requestMapping.value()).toString();
        }
        return "";
    }
}

