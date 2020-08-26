package com.kh.spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
	
	private Logger logger = LoggerFactory.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {
		new Log4jTest().test();
	}
	
	public void test() {
		logger.trace("trace 로그");
		logger.debug("debug 로그");
		logger.info("info 로그");
		logger.warn("warn 로그");
		logger.error("error 로그");
//		logger.fatal("fatal로그");

		//콜솔에
//		INFO : com.kh.spring.log.Log4jTest - info 로그 
//		WARN : com.kh.spring.log.Log4jTest - warn 로그
//		ERROR: com.kh.spring.log.Log4jTest - error 로그


		//레벨을 디버그로 바꾸면 디버그부터 볼수있음
//		DEBUG: com.kh.spring.log.Log4jTest - debug 로그
//		INFO : com.kh.spring.log.Log4jTest - info 로그
//		WARN : com.kh.spring.log.Log4jTest - warn 로그
//		ERROR: com.kh.spring.log.Log4jTest - error 로그

	}
}
