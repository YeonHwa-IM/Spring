package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.spring.member.model.vo.Member;

public class BoardInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			request.setAttribute("msg", "로그인후 이용하세요.");
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
			
			return false;
		}
		
		//보드컨텍스트에 인터셉터 등록 필요
	
		return super.preHandle(request, response, handler);
	}
}
