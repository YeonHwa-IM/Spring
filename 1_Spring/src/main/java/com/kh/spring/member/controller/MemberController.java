package com.kh.spring.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.spring.HomeController;
import com.kh.spring.member.model.exception.MemberException;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes("loginUser")//2 loginuser라는 이름을 가진 값을 보낼때 세션에 남는다. 
@Controller //MemberController 라는걸 컨트롤러 객체로 등록하는것.
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
//	콘솔에 찍어보기 ->DEBUG: MemberController.enrollView{180} - 회원등록페이지
	
	@Autowired //의존성 주입이 일어나는 부분
	private MemberService mService; //맴버서비스 임플이 맴버 서비스라는 인터페이스를 구현하고 있기때문에
	
	@Autowired // 빈등록하고, web.xml에 읽으라고 적어둔 후에
	private BCryptPasswordEncoder bcryptPasswordEncoder; 
	/****************파라미터 전송 받는 방법  *************************/
	//1. HttpServletRequest를 통해 전송 받기 (JSP/Servlet 방식)
	
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public void memberLogin(HttpServletRequest request) {
//		System.out.println("로그인 처리 메소드");
//		String id = request.getParameter("id");
//		String pwd = request.getParameter("pwd");
//		
//		System.out.println("memberconrtroller id : " + id + " " + "pwd : " + pwd);
//	}
	
	//2. @RequestParam 어노테이션을 방식
//	@RequestMapping(value="login.me", method=RequestMethod.GET)
////	public void memberLigin(@RequestParam("id") String userId, @RequestParam("pwd") String userPwd) {
//	public void memberLigin(@RequestParam(value="idpp", required=false, defaultValue="idpp") String userId, @RequestParam(value="pwdpp") String userPwd) {
//		//원래는 속성 value를 명시해줘야하는데 하나라서 생략이 가능한거고, 하나이상일때는 명시해 줘야함
//		System.out.println("memberconrtroller id : " + userId + " / " + "pwd : " + userPwd);
//		// id에 required=false 속성을 주면, pwd를 안넣었을 경우에만 Required String parameter 'pwdpp' is not present 라고 나옴
//		// 페이징 처리 할때 처럼 currentpage 
//	}
	
	//3. @RequestParam 생략
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public void memberLogin(String id, String pwd) {//view에서 넘겨주는 파라미터 이름과 컨트롤러에서 받는 매개변수 이름이 일치하면 생략가능 
//		System.out.println("memberconrtroller id : " + id + " / " + "pwd : " + pwd);
//	}
	
//	//4. @ModelAttribute 어노테이션 방식
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public void memberLogin(@ModelAttribute Member m) {//멤버(객체에서 보내는 필드명과)의 파라미터 이름과 컨트롤러에서 객체에 담아주는 방법
//		System.out.println("memberconrtroller" + m); // 
//	}
	
	//5. @ModelAttribute 어노테이션 생략 방식
//		@RequestMapping(value="login.me", method=RequestMethod.POST)
//		public String memberLogin(Member m, HttpSession session) { //반환값 기본적으로 String이어야함
//			System.out.println("memberconrtroller" + m);
//			
			//인터페이스를 만든이유 공동모듈을 사용하기 위해서...
////			MemberService mService = new MemberServiceImpl(); //다형성 적용 가능
//			System.out.println(mService.hashCode()); // 해시코드 값의 주소값이 계속 바뀌는데 이건 좋지않은 방식이라고 함
//			//주소값이 바뀌면 객체가 계속 바뀌는것..new MemberServiceImpl();얘를 계속 만들어주고 있는건데
//			//이런걸 결합도가 높다고 함. 이런걸 프로그램의 결합도가 높으면 좋지 않은 프로그램이라고 함.
//			//결합도가 높으면 끊어주어야함.
//			//또, 파일이름을 바꾸었을 때 경로가 끊기는점.
//			
//			//1. 결함성을 낮춰주었음 => 좋은 프로그램
//			//2. 이름을 바꿔도, 직접 객체생성하는게 아니기때문에 서비스에 영향안줌
//			Member loginUser = mService.memberLogin(m);
//			
//			if(loginUser != null) {
//				session.setAttribute("loginUser", loginUser);
//			}
//			
//			return "redirect:home.do"; //redirect:이게 붙으면 앞에 있는 주소는 무시하고 home.do 로만 이동한다.
//		}
		
	/********요청 후 전달하고자하는 데이터가 있을 경우 ******************/
	//1. Model 객체를 사용하는 방법(맵형태로 키값이 들어감)
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public String memberLogin(Member m, HttpSession session, Model model) { //반환값 기본적으로 String이어야함
//		System.out.println("memberconrtroller" + m);
//		
////		MemberService mService = new MemberServiceImpl(); //다형성 적용 가능
//		System.out.println(mService.hashCode());
//
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser", loginUser);
//			return "redirect:home.do"; //redirect:이게 붙으면 앞에 있는 주소는 무시하고 home.do 로만 이동한다.
//		}else {
//			//모델객체를 이용해서 
//			model.addAttribute("message", "로그인에 실패하였습니다.");//리퀘스트랑 비슷
//			return "../common/errorPage";//상대경로 이용해서 에러페이지로 이동
//		}
//		
//	}
	
//	2. MedelAndView객체 사용하는 방법 : Model + View
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public ModelAndView memberLogin(Member m, HttpSession session, ModelAndView mv) {
//		System.out.println("memberconrtroller" + m);
//		
////		MemberService mService = new MemberServiceImpl(); //다형성 적용 가능
//		System.out.println(mService.hashCode());
//
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser", loginUser);
//			mv.setViewName("redirect:home.do");//뷰만 사용
//		}else { //값과 뷰를 다사용
//			mv.addObject("message", "로그인에 실패하였습니다.");
//			mv.setViewName("../common/errorPage");
//		
//		}
//		return mv; //리턴을 mv해야겠으니, 반환값이 Sting이 아니라 ModelAndView이다!!
//	}
//	//3. session에 저장할 때 @SessionAttributes 사용하기 : Model과 같이 사용되어야 함
////	우리가 Model을 사용할때 , Model에 attribute가 추가될 때 자동으로 키 값을 차장 세션에 등록하는 어노테이션이다.
//	@RequestMapping(value="login.me", method=RequestMethod.POST)
//	public String memberLogin(Member m, Model model) {
//		System.out.println("memberconrtroller" + m);
//		
////		MemberService mService = new MemberServiceImpl(); //다형성 적용 가능
//		System.out.println(mService.hashCode());
//
//		Member loginUser = mService.memberLogin(m);
//		
//		if(loginUser != null) {
//			model.addAttribute("loginUser", loginUser); //1.모델에 loginuser를 넣었고
//			return "redirect:home.do";
//		}else { //값과 뷰를 다사용
////			model.addAttribute("message", "로그인에 실패하였습니다.");
////			return "../common/errorPage";
//			throw new MemberException("로그인에 실패하였습니다.");
//		}
//	}
	
	//로그아웃용 컨트롤러1
//	@RequestMapping("logout.me")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		
//		return "redirect:home.do";
//	}
	
	//로그아웃용 컨트롤러2 (@SessionAttributes용)
	@RequestMapping("logout.me")
	public String logout(SessionStatus status) {
		status.setComplete();
		
		return "redirect:home.do";
	}
	
	//회원가입 페이지 이동
	@RequestMapping("enrollView.me")//밸류 생략가능
	public String enrollView() {
		logger.debug("회원등록페이지");
		
		return "memberJoin"; // 한글이 깨짐.. web.xml에 인코딩 필터 작성해줌
	}
	//회원가입
	@RequestMapping("minsert.me")
	public String memberInsert(@ModelAttribute Member m, @RequestParam("post") String post,
														 @RequestParam("address1") String address1,
														 @RequestParam("address2") String address2) {
		m.setAddress(post + "/" + address1 + "/" + address2);
		System.out.println(m);
		
		//bcrypt 암호화 방식
		String encPwd =bcryptPasswordEncoder.encode(m.getPwd());
		System.out.println(encPwd);
		
		m.setPwd(encPwd);//암호화된 비번 멤버객체에 추가해주고
		
		int result = mService.insertMember(m);
		
		if(result > 0) {
			return "redirect:home.do";
		}else {
			throw new MemberException("회원가입에 실패하였습니다.");
		}
		
	}
	//암호화 후 로그인
	@RequestMapping(value="login.me", method=RequestMethod.POST)
	public String memberLogin(Member m, Model model) {
		System.out.println("memberconrtroller" + m);
		
//		MemberService mService = new MemberServiceImpl(); //다형성 적용 가능
//		System.out.println("해쉬코드 주소값" + mService.hashCode());

		Member loginUser = mService.memberLogin(m); // 맵퍼에서 아이디만 확인해서 값을 가져옴
		
		//로그인 아이디만 유저에 담은 상태에서, 비번과 암호화된 비번이 같은지 확인
		//rawPassword는 m안에 있음, encodedPassword는 디비에 있음.
		if(bcryptPasswordEncoder.matches(m.getPwd(), loginUser.getPwd())) {
			model.addAttribute("loginUser", loginUser); //1.모델에 loginuser를 넣었고
			logger.info(loginUser.getId());
			
			return "redirect:home.do";
		}else {
			throw new MemberException("로그인에 실패하였습니다.");
		}
		//기존 디비의 정보는 비번 부분을 바꾸어 주어야함.
	}
	
	//내정보보기 페이지 이동
	@RequestMapping("myinfo.me")
	public String myInfoView() {
		return "mypage";
	}
	
	//회원정보 수정 뷰 페이지 이동
	@RequestMapping("mupdateView.me")
	public String updateFormView() {
		return "updateFormView";
	}
	
	//회원정보 수정 입력페이지로 이동
	@RequestMapping("mupdate.me")
	public String memberUpdate(@ModelAttribute Member m, @RequestParam("post") String post,
														 @RequestParam("address1") String address1,
														 @RequestParam("address2") String address2,
														 Model model) {
		m.setAddress(post + "/" + address1 + "/" + address2);
		
		int result = mService.updateMember(m);
		if(result > 0) {
			model.addAttribute("loginUser", m);
			return "mypage";//수정이 끝나면 마이페이지로 이동
			//수정해서 DB는 바뀌는데 회원정보 뷰는 안바뀐다. 이떄, 모델객체를 사용해서 로그인 유저에 새 정보를 넣어준다.
		}else {
			throw new MemberException("회원정보 수정에 실패했습니다.");
		}
	} 
	
	//비번 변경 입력페이지로 이동
	@RequestMapping("mpwdUpdateView.me")
	public String memberPwdUpdateForm() {
		return "memberPwdUpdateForm";
	}
	
	//비번 변경하기
	@RequestMapping(value="mPwdUpdate.me", method=RequestMethod.POST)
	public String memberPwdUpdate(HttpSession session, @RequestParam("pwd") String pwd, @RequestParam("newPwd1") String newPwd) {
		//기존 비번, 새비번 받아오고, 
		//세션에서 아이디 꺼내고
		int result=0;
		Member member = (Member)session.getAttribute("loginUser");
		String loginUserId = member.getId();
		String userpwd = member.getPwd();
		
		String newEncPwd = bcryptPasswordEncoder.encode(newPwd); 
		
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("loginUserId", loginUserId);
		hm.put("newPwd", newEncPwd);
		//서비스가 들어가기 전에 디비에 있는 비번
		if(bcryptPasswordEncoder.matches(pwd,userpwd)){
			result = mService.updatePwdMember(hm);
		}else {
			throw new MemberException("비번을 잘못입력하셨습니다.");
		}
		
		if(result > 0) {
			return "mypage";//수정이 끝나면 마이페이지로 이동
		}else {
			throw new MemberException("비밀번호 변경에 실패했습니다.");
		}
	}

	//회원 탈퇴
	@RequestMapping("mdelete.me")
//	public String deleteMember(HttpSession session, SessionStatus status) {
	public String deleteMember(@RequestParam("id") String id, SessionStatus status) {
		
//		Member member = (Member)session.getAttribute("loginUser");
//		String id = member.getId();
		
		int result = mService.deleteMember(id);
		System.out.println(result);
		if(result > 0) {
			status.setComplete();
			return "redirect:home.do";
		}else {
			throw new MemberException("회원탈퇴에 실패하였습니다.");
		}
	}
	
	@RequestMapping("dupid.me")
	public void idDuplicateCheck(@RequestParam("id") String id, HttpServletResponse response) {
		
		int result = mService.idCheck(id);
		System.out.println(result);
		
		boolean isUsable = result == 0? true : false;
		
		try {
			response.getWriter().print(isUsable);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			PrintWriter out = response.getWriter();
//			if(result > 0) {
//				out.append("ok");
//				out.flush();
//			}else {
//				out.append("fail");
//				out.flush();
//			}
//			out.close();
//		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
