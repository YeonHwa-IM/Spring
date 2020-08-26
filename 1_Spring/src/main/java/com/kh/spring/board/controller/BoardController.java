package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.kh.spring.board.model.exception.BoardException;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.Pagination;
import com.kh.spring.member.model.vo.Member;

@Controller //5 컨트롤러 빈 만들기
public class BoardController {
	
	@Autowired 
	private BoardService bService;//12
	
	//6.보드 리스트 목록을 이동하는 컨트롤러
	@RequestMapping("blist.bo")
	public ModelAndView boardList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv) {
		
		//8.boardListView에서 값을 가져오기 위해서
		// 커런트페이지가져오기
		
		int currentPage = 1; //9 page라는 네임값으로 boardListView에 있는데
		//매개변수로 받오기
		if(page != null) { //10 int를 Integer로 바꿔줌..
			currentPage = page;
		}
		int listCount = bService.getListCount(); 
		//11 메소드 아래 필드에 보드 서비스 정의해주고
		
		//페이지 반환하는 페이지네이션 만들것임 커먼에 만들어줌 그리고 pi에 받아오
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Board> list = bService.selectList(pi); //Board Vo 만들어줌
		
		//보드맵퍼까지 다녀오고
		if(list != null) {
			//list, pi, view => 모델엔드뷰 사용할거고 매개변수에 추가
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("boardListView");
		}else {
			throw new BoardException("게시글 전체 조회에 실패했습니다.");
		}
		
//		return "boardListView"; //7.boardListView파일 생성하기
		return mv; //반환형을 ModelAndView로 바꿔줌
	}
	
	@RequestMapping("binsertView.bo")
	public String boardinsertView() {
		return "boardinsertForm";
	}
	
	//글쓰기 완료하면 리스트로 넘어감
	@RequestMapping("binsert.bo")
	public String boardInsert(@ModelAttribute Board b, @RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request) {//Stirngd이었던 업로드 파일을 멀티파트 파일로바꿔줌
		//제목, 타이틀, 업로드 파일
		
		System.out.println(b);
		System.out.println(uploadFile);
		//org.springframework.web.multipart.commons.CommonsMultipartFile@4b31820d 이런식로 객체로 들어옴.
		System.out.println(uploadFile.getOriginalFilename());
		
		if(uploadFile != null && !uploadFile.isEmpty()) { //파일이 있으면
			//아래 만들어놓은 세이브파일 메소드 불러옴
			String renameFileName = saveFile(uploadFile, request);//리퀘스트 없기때문에 매개변수 HTTP~추가해줌
			
			if(renameFileName != null) {
				b.setOriginalFileName(uploadFile.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		int result = bService.insertBoard(b);
		
		if(result > 0) {
			return "redirect:blist.bo";
		}else {
			throw new BoardException("게시물 등록에 실패하였습니다.");
		}
		
	}
	
	
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		
		String root = request.getSession().getServletContext().getRealPath("resources"); //작은 리소시스의 위치
		String savePath = root + "\\buploadFiles";
		
		File folder = new File(savePath);
		
		if(!folder.exists()) { //폴더가 존재하지 않으면
			folder.mkdirs(); //폴더를 만들어줘라
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new Date(System.currentTimeMillis()))
								+ "."
								+ originFileName.substring(originFileName.lastIndexOf(".")+1);
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return renameFileName;
	}
	
	@RequestMapping("bdetail.bo")
	public ModelAndView boardDetail(@RequestParam("bId") int bId, @RequestParam("page") int page,
							  ModelAndView mv) {
		//page 같은 경우 내가 몇번째 페이지 인지 확인하기 위해 나중에 넘겨줄거고,지금은 id만 넘기기
		
		Board board = bService.selectBoard(bId);
		
		if(board != null) {
			// 모델엔드뷰로 보드를 보낸다.
			mv.addObject("board", board)
			  .addObject("page", page)
			  .setViewName("boardDetailView");//메소드 연결하는거 메소드 체이닝
			
		}else {
			throw new BoardException("게시글 상세보기에 실패하였습니다.");
		}
		return mv;
	}
	
	//수정하기 폼으로 이동
	@RequestMapping("bupView.bo")
	public ModelAndView boardUpdateView(@RequestParam("bId") int bId, @RequestParam("page") int page, ModelAndView mv) {

		Board board = bService.selectUpdateBoard(bId);
		
		if(board != null) {
			mv.addObject("board", board).addObject("page", page).setViewName("boardUpdateForm");
			return mv;
		}else {
			throw new BoardException("게시글 수정 폼 요청에 실패했습니다.");
		}
	}
	
	//게시글 업데이트
	@RequestMapping("bupdate.bo")
	public ModelAndView boardUpdateForm(@ModelAttribute Board b, @RequestParam("reloadFile") MultipartFile reloadFile,
								  @RequestParam("page") int page, HttpServletRequest request, ModelAndView mv) {
		
		if(reloadFile != null && !reloadFile.isEmpty()) { //리네임파일네임이 있을때와 없을때
			if(b.getRenameFileName() != null) { //기존에 파일이 있으면 지워줘야 하기때문에..
				deleteFile(b.getRenameFileName(), request); // 매개변수에 리퀘스트 추가해주기
			}
			
			//내가 받아온 새 파일을 저장하고 이름 바꾸기
			String renameFileName = saveFile(reloadFile, request);
			if(renameFileName != null) {
				b.setOriginalFileName(reloadFile.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		
		int result = bService.updateBoard(b);
		
		if(result > 0) {
			//ModelAndView 추가, 반환값 ModelAndView로 변경
			mv.addObject("page",page)
			  .setViewName("redirect:bdetail.bo?bId="+b.getbId());
			
		}else {
			throw new BoardException("게시글 수정을 실패하였습니다.");
		}
		return mv;
	}
	
	public void deleteFile(String fileName, HttpServletRequest request) { //IO할때 배웠던거라 하심..ㅠㅠ
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		
		File f = new File(savePath + "\\" + fileName);//파일경로와 파일이름을 함께 f변수에 담음
		
		if(f.exists()) {// 이파일이 존재하면
			f.delete(); //지워라
		}
	}
	
	@RequestMapping("bdelete.bo")
	public String deleteBoard(@RequestParam("bId") int bId) {
		int result = bService.deleteBoard(bId);
		
		if(result > 0) {
			return "redirect:blist.bo";
		
		}else {
			throw new BoardException("게시글 삭제에 실패하였습니다.");
		}
	}
	
	@RequestMapping("rList.bo")
	public void replyList(@RequestParam("bId") int bId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		
		ArrayList<Reply> list = bService.replyList(bId);
		
//			JSONArray jArr = new JSONArray();
//			for(Reply r : list) {
//				JSONObject job = new JSONObject();
//				job.put("rId", r.getrId());
//				job.put("rContent", r.getrContent());
//				job.put("refBid", r.getRefBid());
//				job.put("rWriter", r.getrWriter());
//				job.put("rCreateDate", r.getrCreateDate());
//				job.put("rModifyDate", r.getrModifyDate());
//				job.put("rStatus", r.getrStatus());
//				
//				jArr.add(job);
//				
//			}
//		
//			PrintWriter out;
//			try {
//				out = response.getWriter();
//				out.print(jArr);
//				out.flush();
//				out.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		//Gson으로
		
//			try {
//				for(Reply r : list) {
//					r.setrContent(URLEncoder.encode(r.getrContent(), "UTF-8"));
//				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
		
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			try {
				gson.toJson(list, response.getWriter());
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	@RequestMapping("addReply.bo")
	@ResponseBody //뷰 리졸버가  스트링 받으면 뷰 이름으로 받기 때문에, 데이터라는걸 알려주기 위해서 	@ResponseBody를 해줘야함 
	public String addReply(@ModelAttribute Reply r, HttpSession session) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		String rWriter = loginUser.getId();
		
		r.setrWriter(rWriter);
		
		int result = bService.insertReply(r);
		
		if(result > 0) {
			return "success"; // 	@ResponseBody 해줘야함
		}else {
			throw new BoardException("댓글 등록에 실패하였습니다.");
		}
		
	}
	
	@RequestMapping("topList.bo")
	public void boardTopList(HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		
		ArrayList<Board> list = bService.selectTopList();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(list, response.getWriter());
			
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
