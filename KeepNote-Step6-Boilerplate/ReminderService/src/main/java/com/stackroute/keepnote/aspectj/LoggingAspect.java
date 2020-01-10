package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/* Annotate this class with @Aspect and @Component */
@Aspect
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Reminder controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	
private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("execution(* com.stackroute.keepnote.controller.*.*(..)")
	public void pointName() {
		
	}
	
	@Before("pointName()")
	public void beforeMethods(JoinPoint jp) {
	  //  String logMsg = jp.getSignature().getName();
		logger.info(jp.getSignature().getName() + " is going to execute");
	}
	
	@After("pointName()")
	public void afterMethods(JoinPoint jp) {
		logger.info(jp.getSignature().getName() + " execution completed");
	}
	
	@AfterReturning("pointName()")
	public void afterReturn(JoinPoint jp){
		logger.info(jp.getSignature().getName() + " execution completed after return");
	}
	
	@AfterThrowing("pointName()")
	public void afterThrow(JoinPoint jp) {
		
		logger.info(jp.getSignature().getName() + " execution completed after throwing some exception");
	}
}
