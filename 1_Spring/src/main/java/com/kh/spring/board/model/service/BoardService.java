package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;

public interface BoardService{ //13 인터페이스를 먼저 만들고
	
	//13-1 BoardServiceImpl 를 만들어줌
	
	int getListCount(); //14

	ArrayList<Board> selectList(PageInfo pi);

	int insertBoard(Board b);

	Board selectBoard(int bId);

	Board selectUpdateBoard(int bId);

	int updateBoard(Board b);

	int deleteBoard(int bId);

	ArrayList<Reply> replyList(int bId);

	int insertReply(Reply r);

	ArrayList<Board> selectTopList();


}
