package com.adelsonsljunior.simpleecommerce.infra.configs.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.adelsonsljunior.simpleecommerce.infra.configs.log.LogExecutionTime)")// Intercepta métodos com @Cacheable
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis(); // Captura o tempo inicial

        Object proceed = joinPoint.proceed(); // Executa o método

        long endTime = System.currentTimeMillis(); // Captura o tempo final
        long executionTime = endTime - startTime; // Calcula o tempo de execução

        logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime); // Loga o tempo de execução

        return proceed;
    }
}

