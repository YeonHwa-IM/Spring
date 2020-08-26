package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component//1. 빈등록
@Aspect//2.
public class LoggerAspect2 {
	
	private Logger logger = LoggerFactory.getLogger(LoggerAspect2.class);
	
//	//3. 포인트컷 + 이름부를 용도로 사용할  빈 메소드작성
//	@Pointcut("execution(* com.kh.spring.board..*(..))")
//	public void myPointcut() {}
//	
//	//4.
//	@Around("myPointcut()")
	
	//5.  //6. aspect-context.xml에 작성
	@Around("execution(* com.kh.spring.board..*(..))")
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
