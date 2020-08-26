package com.kh.ajax.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.ajax.model.vo.Sample;
import com.kh.ajax.model.vo.User;

@Controller //1 빈등록
public class TestController {
	
	@Autowired //의존성 주입 이미 셈플에 대한 빈등록이 들어갔다. 1. 컴포넌트 2. 다른거
	private Sample sam;
	
	@RequestMapping("testtest.do")
	public void test() {
		System.out.println(sam);
	}
	
	//1. ServletOutputStream을 이용한 출력용 메소드
	@RequestMapping("test1.do")
	public void test1Method(@RequestParam("name") String name, @RequestParam("age") int age, 
						      HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			
			if(name.equals("임연화") && age == 29) {
				out.append("ok");
				out.flush();
			}else {
				out.append("fail");
				out.flush();
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//String에서 void로 바꿔줌
	
	@RequestMapping("test2.do")
	@ResponseBody
	public String test2Method() {
		//json을 통해서 값을 넣을것임
		//메이블을 이용해서 라이브러리
		
		JSONObject job = new JSONObject();
		
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", "박신우");
		job.put("content", "JSON객체를 리턴해보겠습니다.");
		
		return job.toJSONString(); // 투스트링을 했을때 앞뒤로 주소가 붙어서 잘못된 경로가 나옴.
		//주소가 아닌 보낼 데이터라는걸 알려줘야함 -> 어노테이션 추가
		// 뷰리졸버가 인식하는 주소가 아닌 데이터라라고 알려주는 	@ResponseBody
		//**정말 많이하는 실수이다.@ResponseBody잘 해주기!
	}
	
	@RequestMapping("test3.do")
	public void test3Method(HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		
		JSONObject job = new JSONObject();
		
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", "박신우");
		job.put("content", "JSON객체를 리턴해보겠습니다.");
		
		try {
			PrintWriter out = response.getWriter();
			out.println(job);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("test4.do")
	public void test4Method(HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		ArrayList<User> list = new ArrayList<User>();
		//원래 디비 갔다오는 건데 귀찮아서 이걸루..
		
		list.add(new User("u111", "p111", 25, "m111@kh.com", "01025358387"));
		list.add(new User("u222", "p222", 25, "m222@kh.com", "01025358388"));
		list.add(new User("u333", "p333", 25, "m333@kh.com", "01025358389"));
		list.add(new User("u444", "p444", 25, "m444@kh.com", "01025358310"));
		list.add(new User("u555", "p555", 25, "m555@kh.com", "01025358311"));
		
		JSONArray jArr = new JSONArray();
			for(User user : list) {
				JSONObject jUser = new JSONObject();
				jUser.put("userId", user.getUserId());
				jUser.put("userpwd", user.getUserPwd());
				jUser.put("age", user.getAge());
				jUser.put("email", user.getEamil());
				jUser.put("phone", user.getPhone());
				
				jArr.add(jUser);
			}
		
		JSONObject sendJson = new JSONObject();
		sendJson.put("list", jArr);
		
		try {
			PrintWriter out = response.getWriter();
			
			out.println(sendJson);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
}
