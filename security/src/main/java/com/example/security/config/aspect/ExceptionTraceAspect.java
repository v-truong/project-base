package com.example.security.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.example.security.config.SecurityConstants;

@Configuration
@Aspect
@ConditionalOnProperty(prefix = "app.debug", name = "trace-exception", havingValue = "true")
public class ExceptionTraceAspect {
    @AfterThrowing(pointcut = "execution(* com.example.service.*.*(..))", throwing = "ex")
    public void handleException(JoinPoint joinPoint, RuntimeException ex) {
        // Xử lý ngoại lệ ở đây, ví dụ ghi nhật ký, thông báo, v.v.
        System.out.println("Exception occurred: " + ex.getMessage());
    }
}
