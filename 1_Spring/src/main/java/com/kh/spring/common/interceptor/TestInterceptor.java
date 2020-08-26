package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TestInterceptor extends HandlerInterceptorAdapter{ //1. HandlerInterceptorAdapter 상속
	//사용할수 있는 메소드3개
	
	
	private Logger logger = LoggerFactory.getLogger(TestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//DispatcherSerclet이 Controller를 호출하기 전 수행
		//유일하게 블린을 반환
		
		logger.debug("========start=========");
		logger.debug(request.getRequestURI());
		
		return super.preHandle(request, response, handler); //트루밖에 반환하지 않음
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//Controller에서 DispatcherServlet으로 리턴되는 순간 수행
		
		logger.debug("========view=========");
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//모든 뷰에서 최종결과를 생성하는 일을 포함한 모든 작업이 완료된 후
		
		logger.debug("========END=========");
			
	super.afterCompletion(request, response, handler, ex);
	}

}
