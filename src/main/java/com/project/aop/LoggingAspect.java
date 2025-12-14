package com.project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
	
	/**
	 * To convert java object (request args) into JSON format
	 */
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Around("execution(* com.project.controller..*(..))")         //Pointcut expression
	public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable{
		
		long startTime = System.currentTimeMillis();
		
		/**
		 * 'MethodSignature' - To access Method meta data like  it name , return type, parameter name , declaring class , etc
		 */
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String methodName = methodSignature.getName();
        String className = methodSignature.getDeclaringType().getSimpleName();
        
        Object[] args = joinPoint.getArgs();
        
        /**
         * Creates a Empty container (mutable string) in which we add all request argument details 
         */
        StringBuilder requestDetails = new StringBuilder();
        
        for(Object arg: args) {
        	
        	/**
        	 * Ignore or skip HttpServletRequest type argument 
        	 * because it consist of heavy data so that it destroy its readability,
        	 * also its jackson mapper not make it serialize
        	 */
        	
        	if(arg instanceof HttpServletRequest) continue;
        	
        	try {
        		
        		/**
        		 * Jackson Object Mapper is used to convert java object into JSON string format 
        		 * append(" ")- > add a space to move on next argument 
        		 */
        		requestDetails.append(mapper.writeValueAsString(arg)).append(" ");
        		
        	}catch(Exception e){
        		
        		/**
        		 * Here we are using toString() method ,
        		 * so that atleast we can long object name
        		 * In case of any failure (Like : Complex object -  MultipartFile, InputStream , ..)
        		 */
        		
        		requestDetails.append(arg.toString()).append(" ");
        	}
        }
		
		log.info("API Request : {}.{}() | Args: {}", className, methodName, requestDetails);
		
		/**
		 * Calling or executing actual method call
		 */
		Object response = joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		
		log.info("API Response : {}.{}() executed in {}ms", className, methodName, timeTaken);
		
		return response;
		
	}

}
