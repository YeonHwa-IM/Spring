package com.kh.spring.member.model.exception;

public class MemberException extends RuntimeException { //RuntimeException으로 변경!!!
	public MemberException() {}
	public MemberException(String msg) {
		super(msg);
	}
}
