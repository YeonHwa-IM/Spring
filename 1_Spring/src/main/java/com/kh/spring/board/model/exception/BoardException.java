package com.kh.spring.board.model.exception;

public class BoardException extends RuntimeException { //런타임으로 바꿔줌! 트라이 캐치 안해줘도 됨
	public BoardException() {}
	public BoardException(String msg) {
		super(msg);
	}
}
