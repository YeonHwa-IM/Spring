package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect1 {
	
	private Logger logger = LoggerFactory.getLogger(LoggerAspect1.class);
	
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		//시그니처 인터페이스: 현재 AOP가 적용되는 메소드의 정보를 가져옴
		Signature signature = joinPoint.getSignature(); //시그니처 바로 객체 생성하지않고 매개변수로 조인포인트 가져와서 객체 가져옴
		logger.debug("signature : " + signature);
		
		String type = signature.getDeclaringTypeName();
		String methodName = signature.getName();
		logger.debug("type : "+ type);
		logger.debug("methodName : "+ methodName);
		
		String componentName = "";
		if(type.indexOf("Controller") > -1) {
			componentName = "Controller \t: ";
		}else if(type.indexOf("service") > -1){
			componentName = "ServiceImpl \t: ";
		}else if(type.indexOf("DAO") > -1) {
			componentName = "DAO \t\t: ";
		}
		
		logger.debug("[Before]" + componentName + type + "." + methodName + "()");
		//다음 advice나 target으로 넘어갈 수 있게 진행시킴

		//2. 어라운드 
		Object obj = joinPoint.proceed();
		logger.debug("[After]" + componentName + type + "." + methodName + "()");
		//throws 해줌
		//ProceedingJoinPoint로 매개변수 수정
		
		return obj;
		
		//선언적 AOP한거고 이제 어노테이션 해볼것임
	}
}
