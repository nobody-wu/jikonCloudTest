package cn.jikon.common.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by zl on 2017/5/2.
 */
@Component
@Aspect
@Order(-5)
public class WebLogAspect {

    private Logger logger = LogManager.getLogger(WebLogAspect.class);
    // 保证每个线程都有一个单独的实例
    private ThreadLocal<Long> time = new ThreadLocal<>();
    @Pointcut("execution(* cn.jikon.api..*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        time.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求的内容
        logger.info("Request URL: " + request.getRequestURL().toString());
        logger.info("Request Method: " + request.getMethod());
        logger.info("Class Method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Params: " + Arrays.toString(joinPoint.getArgs()));
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paraName = enums.nextElement();
            logger.info(paraName + ":" + request.getParameter(paraName));
        }
    }

    @AfterReturning(returning = "object",pointcut ="pointcut()")
    public void doAfterReturning(Object object) {
        logger.info("respone : "+ object);
        logger.info("times : " + ((System.currentTimeMillis() - time.get())) + "ms");
    }
}
