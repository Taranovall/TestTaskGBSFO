package com.gbsfo.test.task.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class EventsAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    public static final String CRUD_TOPIC = "CRUD-operation";

    @AfterReturning(pointcut = "execution(public * com.gbsfo.test.task.controller.*.*(..))", returning = "result")
    public void sendToKafkaAfterCRUD(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String annotationName = method.getDeclaredAnnotations()[0].annotationType().getSimpleName();
        String affectedObject = ((ResponseEntity) result).getBody().getClass().getSimpleName();
        String event = String.format("[%s] %s", annotationName, affectedObject);
        kafkaTemplate.send(CRUD_TOPIC, event);
    }
}
