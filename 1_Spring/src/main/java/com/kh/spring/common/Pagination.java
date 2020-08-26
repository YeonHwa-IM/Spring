package com.kh.spring.common;

import com.kh.spring.board.model.vo.PageInfo;

public class Pagination {
	
	public static PageInfo getPageInfo(int currentPage, int listCount) {
		//1. pageInfo vo 만들어줌!
		//2. 아래 작성..
		
		PageInfo pi = null;
		
		int pageLimit = 10;
		int maxPage;
		int startPage;
		int endPage;
		int boardLimit =5;
		
		maxPage = (int)((double)listCount / boardLimit +0.9);
		startPage=((int)((double)currentPage / pageLimit + 0.9) -1)	* pageLimit +1;
		endPage = startPage + pageLimit -1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
		
		return pi;
	}

}
