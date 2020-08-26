package com.kh.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller //어노테이션이 하는 역할은 빈 등록
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	//url Mapping
	// 기본, 루트페이지 => home 메소드 호출
	@RequestMapping(value = "home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale); // 실행하면 콘솔에서 맨처음 보여지는 부분.
		//필드에서 logger선언해두어서 가져다 쓰고 있음. 어디에서 로거가 만들어졌는지 클래스를 적어줘야함
		
//		로거에 있는 네임의 위치를 보고서 
//		그 위치들을 뽑아내고, 
//		그 뽑은 로그들에 대한 레벨이 인포면
//		루트로 보내서 어펜어한테 어디레 출력할지 지정해준다.
		
		//로거를 만들때 로거팩토리를 통해서 만드는데, 가져오는 정보는 
		//이게 가능한 이유가 홈컨트롤러 클래스에 있는
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
		//모델(서블릿의 request 객체를 대체한 것)
//		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
