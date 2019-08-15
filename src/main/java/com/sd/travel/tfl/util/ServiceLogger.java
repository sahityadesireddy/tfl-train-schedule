package com.sd.travel.tfl.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ServiceLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogger.class);

    @Before("execution(* com.sd.travel.tfl.service.TflApiService.searchTrains(..)) && args(platformId)")
    public void beforeSearch(String platformId) {
        LOGGER.info("Received a request for searching:" + platformId);
    }

    @AfterReturning(pointcut = "execution(* com.sd.travel.tfl.service.TflApiService.searchTrains(..))", returning = "result")
    public void afterSearch(JoinPoint joinPoint, Object result) {
        LOGGER.info("The response for the request was:" + result.toString());
    }
}
